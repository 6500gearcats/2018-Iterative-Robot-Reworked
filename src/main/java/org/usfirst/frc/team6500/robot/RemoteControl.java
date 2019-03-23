package org.usfirst.frc.team6500.robot;

import java.util.ArrayList;
import java.io.*;
import java.net.*;

import org.usfirst.frc.team6500.robot.Constants;
import org.usfirst.frc.team6500.trc.auto.TRCDrivePID;
import org.usfirst.frc.team6500.trc.auto.TRCVector;
import org.usfirst.frc.team6500.trc.util.TRCNetworkData;
import org.usfirst.frc.team6500.trc.util.TRCTypes;
import org.usfirst.frc.team6500.trc.util.TRCTypes.Direction;
import org.usfirst.frc.team6500.trc.util.TRCTypes.DriveAction;
import org.usfirst.frc.team6500.trc.util.TRCTypes.UnitType;
import org.usfirst.frc.team6500.trc.util.TRCTypes.VerbosityType;

public class RemoteControl
{
    static private ArrayList<RemoteControl> openConnections = new ArrayList<RemoteControl>();
    private Integer port = Constants.REMOTECONTROL_DEFAULT_PORT;
    private Thread runnable = new Thread(this::control);
    private boolean isValid = false;
    

    /**
     * Creates a {@link RemoteControl} object opening to the specified port
     */
    RemoteControl(Integer port)
    {
        this.port = port;
        if (!RemoteControl.validatePort(port))
        {
            TRCNetworkData.logString(VerbosityType.Log_Error, "RemoteControl:RemoteControl(Integer):Unable to bind to port '" + port + "' because it is already bound!");
        }
        else isValid = true;
        openConnections.add(this);
    }

    /**
     * Creates a {@link RemoteControl} object opening to port 7272
     */
    RemoteControl()
    {
        if (!RemoteControl.validatePort(port))
        {
            TRCNetworkData.logString(VerbosityType.Log_Error, "RemoteControl:RemoteControl():Unable to bind to port '" + port + "' because it is already bound!");
        }
        else isValid = true;
        openConnections.add(this);
    }

    /**
     * Starts the control server and listens
     */
    public void startRemoteConnection()
    {
        if (!isValid) return;
        runnable.start();
    }

    /**
     * Stops the control server
     */
    public void stopRemoteConnection()
    {
        isValid = false;
        openConnections.remove(this);
    }

    /**
     * The control function that works network stuff. NEVER CALL THIS FUNCTION
     * EXCEPT THROUGH A DIFFERENT THREAD AS IT BLOCKS!!!
     */
    private void control()
    {
        try (
			 ServerSocket serverSocket = new ServerSocket(port);
			 Socket clientSocket = serverSocket.accept();
			 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			 ) {
			String inputLine;
			while ((inputLine = in.readLine()) != null)
			{
                if (!isValid)
                {
                    serverSocket.close();
                    clientSocket.close();
                    return;
                }
                out.println(inputLine);
                this.interperateCommand(inputLine);
            }
            
            serverSocket.close();
            clientSocket.close();
		} catch (IOException e) {
			System.out.println("Exception caught when trying to listen on port " + port + " or listening for a connection");
			System.out.println(e.getMessage());
        }
    }

    public Integer port() {return port;}
    public boolean isValid() {return isValid;}

    public static ArrayList<RemoteControl> openConnections() {return openConnections;}

    /**
     * Interperates a command sent to the control server
     * 
     * @param command the raw String data recieved from the controller
     */
    private void interperateCommand(String command)
    {
        boolean preAuthorized = TRCDrivePID.isSubautonomousAuthorized();
        if (!preAuthorized) TRCDrivePID.grantSubautonomousAction();

        TRCVector movement = null;
        switch (command.charAt(0))
        {
            case Constants.REMOTECONTROL_ACTION_FORWARD:
            {
                movement = new TRCVector(DriveAction.ForwardBack, 1, UnitType.General);
                break;
            }
            case Constants.REMOTECONTROL_ACTION_FORWARDRIGHT:
            {
                movement = new TRCVector(Direction.ForwardRight, 1, UnitType.General);
                break;
            }
            case Constants.REMOTECONTROL_ACTION_RIGHT:
            {
                movement = new TRCVector(DriveAction.LeftRight, 1, UnitType.General);
                break;
            }
            case Constants.REMOTECONTROL_ACTION_BACKWARDRIGHT:
            {
                movement = new TRCVector(Direction.BackwardRight, 1, UnitType.General);
                break;
            }
            case Constants.REMOTECONTROL_ACTION_BACKWARD:
            {
                movement = new TRCVector(DriveAction.ForwardBack, -1, UnitType.General);
                break;
            }
            case Constants.REMOTECONTROL_ACTION_BACKWARDLEFT:
            {
                movement = new TRCVector(Direction.BackwardLeft, 1, UnitType.General);
                break;
            }
            case Constants.REMOTECONTROL_ACTION_LEFT:
            {
                movement = new TRCVector(DriveAction.LeftRight, -1, UnitType.General);
                break;
            }
            case Constants.REMOTECONTROL_ACTION_FORWARDLEFT:
            {
                movement = new TRCVector(Direction.ForwardLeft, 1, UnitType.General);
                break;
            }
            case Constants.REMOTECONTROL_ACTION_ROTATELEFT:
            {
                movement = new TRCVector(DriveAction.Rotation, -90, UnitType.Degrees);
                break;
            }
            case Constants.REMOTECONTROL_ACTION_ROTATERIGHT:
            {
                movement = new TRCVector(DriveAction.Rotation, 90, UnitType.Degrees);
                break;
            }
            case Constants.REMOTECONTROL_ACTION_STOP:
            {
                
            }
            default:
            {
                
                break;
            }
        }
        
        if (!preAuthorized) TRCDrivePID.denySubautonomousAction();
    }

    /**
     * Checks to make sure a port isn't being used
     * 
     * @param port the port number to check
     */
    private static boolean validatePort(int port)
    {
        // sorry for the formatting, its just compact!
        for (RemoteControl rc : openConnections) if (rc.port == port) return false;
        return true;
    }
}
