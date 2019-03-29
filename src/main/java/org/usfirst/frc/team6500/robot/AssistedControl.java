package org.usfirst.frc.team6500.robot;

import org.usfirst.frc.team6500.trc.auto.TRCVector;
import org.usfirst.frc.team6500.trc.auto.TRCArtificialDriver;
import org.usfirst.frc.team6500.trc.auto.TRCDrivePID;
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
        readThread.setName("Assisted Control Thread");
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
        while (this.isReading)
        {
            int action = requestAction();
            TRCVector movement = AssistedControl.interperateCommand(action);
            System.out.print("Action is " + movement.getDirection().name());
            System.out.println("; number " + action);
            robby.setAction(movement);
            try {
                Thread.sleep(20);
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
        switch (action)
        {
            case Constants.REMOTECONTROL_ACTION_FORWARD:
            {
                TRCVector movement = new TRCVector(DriveAction.ForwardBack, Direction.Forward);
                return movement;
            }
            case Constants.REMOTECONTROL_ACTION_FORWARDRIGHT:
            {
                TRCVector movement = new TRCVector(DriveAction.Diagonal, Direction.ForwardRight);
                return movement;
            }
            case Constants.REMOTECONTROL_ACTION_RIGHT:
            {
                TRCVector movement = new TRCVector(DriveAction.LeftRight, Direction.Right);
                return movement;
            }
            case Constants.REMOTECONTROL_ACTION_BACKWARDRIGHT:
            {
                TRCVector movement = new TRCVector(DriveAction.Diagonal, Direction.BackwardRight);
                return movement;
            }
            case Constants.REMOTECONTROL_ACTION_BACKWARD:
            {
                TRCVector movement = new TRCVector(DriveAction.ForwardBack, Direction.Backward);
                return movement;
            }
            case Constants.REMOTECONTROL_ACTION_BACKWARDLEFT:
            {
                TRCVector movement = new TRCVector(DriveAction.Diagonal, Direction.BackwardLeft);
                return movement;
            }
            case Constants.REMOTECONTROL_ACTION_LEFT:
            {
                TRCVector movement = new TRCVector(DriveAction.LeftRight, Direction.Left);
                return movement;
            }
            case Constants.REMOTECONTROL_ACTION_FORWARDLEFT:
            {
                TRCVector movement = new TRCVector(DriveAction.Diagonal, Direction.ForwardLeft);
                return movement;
            }
            case Constants.REMOTECONTROL_ACTION_ROTATELEFT:
            {
                TRCVector movement = new TRCVector(DriveAction.Rotation, Direction.RotateLeft);
                return movement;
            }
            case Constants.REMOTECONTROL_ACTION_ROTATERIGHT:
            {
                TRCVector movement = new TRCVector(DriveAction.Rotation, Direction.RotateRight);
                return movement;
            }
            case Constants.REMOTECONTROL_ACTION_CURVEFORWARDLEFT:
            {
                TRCVector movement = new TRCVector(DriveAction.Curve, Direction.CurveForwardLeft);
                return movement;
            }
            case Constants.REMOTECONTROL_ACTION_CURVEFORWARDRIGHT:
            {
                TRCVector movement = new TRCVector(DriveAction.Curve, Direction.CurveForwardRight);
                return movement;
            }
            default:
            {
                TRCVector movement = new TRCVector();
                return movement;
            }
        }
    }
}