package org.usfirst.frc.team6500.robot;

import org.usfirst.frc.team6500.trc.auto.TRCVector;
import org.usfirst.frc.team6500.trc.auto.TRCArtificialDriver;
import org.usfirst.frc.team6500.trc.auto.TRCDrivePID;
import org.usfirst.frc.team6500.trc.util.TRCNetworkData;
import org.usfirst.frc.team6500.trc.util.TRCTypes.*;

import edu.wpi.first.wpilibj.I2C;

public class AssistedControl extends I2C 
{
    private TRCArtificialDriver robby = new TRCArtificialDriver();
    private Thread readThread;
    private boolean isReading = false;

    AssistedControl(int address) 
    {
        super(I2C.Port.kOnboard, address);
    }

    public void startCommunications() 
    {
        readThread = new Thread(this::read);
        readThread.start();
        isReading = true;
    }

    public void stopCommunications() 
    {
        this.isReading = false;
    }

    private int requestAction() 
    {
        byte[] input = new byte[1];
        boolean noConnection = readOnly(input, 1);
        if (noConnection)
            return -2;
        return (int) input[0];
    }

    private void read() 
    {
        TRCDrivePID.grantSubautonomousAction();
        robby.startDriving();
        while (isReading) 
        {
            int action = requestAction();
            System.out.println("Action is " + action);
            TRCVector movement = AssistedControl.interperateCommand(action);
            robby.setAction(movement);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        robby.stopDriving();
        TRCDrivePID.denySubautonomousAction();
    }

    /**
     * Interperates a command sent to the control server
     * 
     * @param command the raw String data recieved from the controller
     */
    private static TRCVector interperateCommand(int action)
    {
        TRCVector movement = null;
        switch (action)
        {
            case Constants.REMOTECONTROL_ACTION_FORWARD:
            {
                movement = new TRCVector(DriveAction.ForwardBack, Direction.Forward);
                break;
            }
            case Constants.REMOTECONTROL_ACTION_FORWARDRIGHT:
            {
                movement = new TRCVector(DriveAction.Diagonal, Direction.ForwardRight);
                break;
            }
            case Constants.REMOTECONTROL_ACTION_RIGHT:
            {
                movement = new TRCVector(DriveAction.LeftRight, Direction.Right);
                break;
            }
            case Constants.REMOTECONTROL_ACTION_BACKWARDRIGHT:
            {
                movement = new TRCVector(DriveAction.Diagonal, Direction.BackwardRight);
                break;
            }
            case Constants.REMOTECONTROL_ACTION_BACKWARD:
            {
                movement = new TRCVector(DriveAction.ForwardBack, Direction.Backward);
                break;
            }
            case Constants.REMOTECONTROL_ACTION_BACKWARDLEFT:
            {
                movement = new TRCVector(DriveAction.Diagonal, Direction.BackwardLeft);
                break;
            }
            case Constants.REMOTECONTROL_ACTION_LEFT:
            {
                movement = new TRCVector(DriveAction.LeftRight, Direction.Left);
                break;
            }
            case Constants.REMOTECONTROL_ACTION_FORWARDLEFT:
            {
                movement = new TRCVector(DriveAction.LeftRight, Direction.ForwardLeft);
                break;
            }
            case Constants.REMOTECONTROL_ACTION_ROTATELEFT:
            {
                movement = new TRCVector(DriveAction.Rotation, Direction.RotateLeft);
                break;
            }
            case Constants.REMOTECONTROL_ACTION_ROTATERIGHT:
            {
                movement = new TRCVector(DriveAction.Rotation, Direction.RotateRight);
                break;
            }
            default:
            {
                movement = new TRCVector();
                break;
            }
        }
        return movement;
    }
}