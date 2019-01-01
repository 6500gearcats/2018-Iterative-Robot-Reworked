package org.usfirst.frc.team6500.robot.auto;

import org.usfirst.frc.team6500.robot.Constants;
import org.usfirst.frc.team6500.trc.auto.TRCAutoRoute;
import org.usfirst.frc.team6500.trc.auto.TRCDrivePID;
import org.usfirst.frc.team6500.trc.util.TRCTypes.DirectionalSystemActionType;
import org.usfirst.frc.team6500.trc.util.TRCTypes.DriveActionType;
import org.usfirst.frc.team6500.trc.auto.TRCDirectionalSystemAction;

public class ForwardSwitch implements TRCAutoRoute
{
	private double inches0, inches1, degrees0;
	private boolean left;
	
	public ForwardSwitch(boolean left)
	{
		this.inches0 = 15.0;
		this.inches1 = 135.0;
		this.degrees0 = -90.0;
		this.left = left;
	}
	
	@Override
    public void run()
    {
        if (left)
        {
			TRCDrivePID.run(DriveActionType.Right, -this.inches0);
        }
        else
        {
			TRCDrivePID.run(DriveActionType.Right, this.inches0);
		}
        
        
		TRCDrivePID.run(DriveActionType.Forward, this.inches1);
        
        
        if (left)
        {
			TRCDrivePID.run(DriveActionType.Rotate, -this.degrees0);
		}
		else
		{
			TRCDrivePID.run(DriveActionType.Rotate, this.inches0);
		}
        
        
		TRCDrivePID.run(DriveActionType.Right, this.inches0);
		
		(new TRCDirectionalSystemAction("Grabber", DirectionalSystemActionType.Reverse, Constants.GRABBER_TIME_EJECT, false)).start();
	}
}