package org.usfirst.frc.team6500.robot;

import java.util.ArrayList;
import java.io.*;
import java.net.*;

import org.usfirst.frc.team6500.robot.Constants;
import org.usfirst.frc.team6500.trc.auto.TRCDrivePID;
import org.usfirst.frc.team6500.trc.util.TRCNetworkData;
import org.usfirst.frc.team6500.trc.util.TRCTypes;
import org.usfirst.frc.team6500.trc.util.TRCVector;

public class RemoteControl
{
    static private ArrayList<RemoteControl> openConnections;
    private Integer port = 7272; // default port will always be (from now on 'cause I say) 7272
    private Thread runnable = new Thread(this::control);
    private boolean isValid = false;

    /**
     * Creates a {@link RemoteControl} object opening to the specified port
     */
    RemoteControl(Integer port)
    {
        this.port = port;
        isValid = true;
        openConnections.add(this);
    }

    /**
     * Creates a {@link RemoteControl} object opening to port 7272
     */
    RemoteControl()
    {
        isValid = true;
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
    }

    private void control()
    {
        try (
			 ServerSocket serverSocket = new ServerSocket(port);
			 Socket clientSocket = serverSocket.accept();
			 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			 ) {
			String inputLine;
			while ((inputLine = in.readLine()) != null && isValid)
			{
                out.println(inputLine);
                this.interperateCommand(inputLine);
			}
		} catch (IOException e) {
			System.out.println("Exception caught when trying to listen on port " + port + " or listening for a connection");
			System.out.println(e.getMessage());
        }
    }

    public Integer port() {return port;}
    public boolean isValid() {return isValid;}

    public static ArrayList<RemoteControl> openConnections() {return openConnections;}

    private void interperateCommand(String command)
    {
        TRCDrivePID.grantSubautonomousAction();
        
        switch (command.charAt(0))
        {
            case Constants.REMOTECONTROL_ACTION_FORWARD:
            {
                break;
            }
            case Constants.REMOTECONTROL_ACTION_FORWARDRIGHT:
            {
                break;
            }
            case Constants.REMOTECONTROL_ACTION_RIGHT:
            {
                break;
            }
            case Constants.REMOTECONTROL_ACTION_BACKWARDRIGHT:
            {
                break;
            }
            case Constants.REMOTECONTROL_ACTION_BACKWARD:
            {
                break;
            }
            case Constants.REMOTECONTROL_ACTION_BACKWARDLEFT:
            {
                break;
            }
            case Constants.REMOTECONTROL_ACTION_LEFT:
            {
                break;
            }
            case Constants.REMOTECONTROL_ACTION_FORWARDLEFT:
            {
                break;
            }
            case Constants.REMOTECONTROL_ACTION_ROTATELEFT:
            {
                break;
            }
            case Constants.REMOTECONTROL_ACTION_ROTATERIGHT:
            {
                break;
            }
            default: TRCNetworkData.logString(TRCTypes.VerbosityType.Log_Error, "Recieved invalid remote control command"); break;
        }
        TRCDrivePID.denySubautonomousAction();
    }
}
