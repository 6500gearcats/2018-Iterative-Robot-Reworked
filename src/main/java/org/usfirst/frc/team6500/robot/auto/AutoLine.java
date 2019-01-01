package org.usfirst.frc.team6500.robot.auto;

import org.usfirst.frc.team6500.trc.auto.TRCAutoRoute;
import org.usfirst.frc.team6500.trc.auto.TRCDrivePID;
import org.usfirst.frc.team6500.trc.util.TRCTypes.DriveActionType;

/**
 * Default route used for crossing the auto line
 */
public class AutoLine implements TRCAutoRoute
{
	private double inches;
	
	public AutoLine()
	{
		this.inches = 130.0;
	}
	
	@Override
    public void run()
    {
		TRCDrivePID.run(DriveActionType.Forward, this.inches);
	}
}