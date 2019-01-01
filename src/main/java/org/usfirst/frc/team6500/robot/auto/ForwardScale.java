package org.usfirst.frc.team6500.robot.auto;

import org.usfirst.frc.team6500.robot.Constants;
import org.usfirst.frc.team6500.trc.auto.TRCAutoRoute;
import org.usfirst.frc.team6500.trc.auto.TRCDrivePID;
import org.usfirst.frc.team6500.trc.util.TRCTypes.DirectionalSystemActionType;
import org.usfirst.frc.team6500.trc.util.TRCTypes.DriveActionType;
import org.usfirst.frc.team6500.trc.auto.TRCDirectionalSystemAction;


public class ForwardScale implements TRCAutoRoute
{
	private static double inches0 = 15.0;
	private static double inches1 = 285.0;
	private static double inches2 = -15.0;
	
	private static double degrees0 = -90.0;
	

	public ForwardScale(boolean left)
	{
		if (left) { degrees0 *= -1; }
	}
	
	@Override
    public void run()
    {
		(new TRCDirectionalSystemAction("Lift", DirectionalSystemActionType.Forward, Constants.LIFT_TIME_SCALE_UP, true)).start();
		TRCDrivePID.run(DriveActionType.Forward, inches1);
		
		TRCDrivePID.run(DriveActionType.Rotate, degrees0);
		
		TRCDrivePID.run(DriveActionType.Forward, inches0);

		(new TRCDirectionalSystemAction("Grabber", DirectionalSystemActionType.Reverse, Constants.GRABBER_TIME_EJECT, false)).start();
		
		TRCDrivePID.run(DriveActionType.Forward, inches2);
		
		(new TRCDirectionalSystemAction("Lift", DirectionalSystemActionType.Reverse, Constants.LIFT_TIME_SCALE_DOWN, false)).start();
	}
}