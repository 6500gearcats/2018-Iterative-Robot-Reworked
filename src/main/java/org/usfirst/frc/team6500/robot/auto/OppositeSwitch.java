package org.usfirst.frc.team6500.robot.auto;

import org.usfirst.frc.team6500.robot.Constants;
import org.usfirst.frc.team6500.trc.auto.TRCAutoRoute;
import org.usfirst.frc.team6500.trc.auto.TRCDrivePID;
import org.usfirst.frc.team6500.trc.util.TRCTypes.DirectionalSystemActionType;
import org.usfirst.frc.team6500.trc.util.TRCTypes.DriveActionType;
import org.usfirst.frc.team6500.trc.auto.TRCDirectionalSystemAction;


public class OppositeSwitch implements TRCAutoRoute
{
	private static double inches0 = 200.0;
	private static double inches1 = 165.0;
	private static double inches2 = 10.0;
	
	private static double degrees0 = 90.0;


	public OppositeSwitch(boolean left)
	{
		if (left) { inches0 *= -1; degrees0 *= -1;}
	}
	
	@Override
    public void run()
    {
		(new TRCDirectionalSystemAction("Lift", DirectionalSystemActionType.Forward, Constants.LIFT_TIME_SWITCH_UP, true)).start();
		
		TRCDrivePID.run(DriveActionType.Right, inches0);
		TRCDrivePID.run(DriveActionType.Forward, inches1);
		TRCDrivePID.run(DriveActionType.Rotate, degrees0);
		TRCDrivePID.run(DriveActionType.Forward, inches2);
		TRCDrivePID.run(DriveActionType.Rotate, degrees0);
		
		(new TRCDirectionalSystemAction("Grabber", DirectionalSystemActionType.Reverse, Constants.GRABBER_TIME_EJECT, false)).start();
	}
}