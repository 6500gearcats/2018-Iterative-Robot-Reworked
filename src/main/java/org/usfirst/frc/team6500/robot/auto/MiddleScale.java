package org.usfirst.frc.team6500.robot.auto;

import org.usfirst.frc.team6500.trc.auto.TRCAutoRoute;
import org.usfirst.frc.team6500.trc.auto.TRCDrivePID;
import org.usfirst.frc.team6500.trc.util.TRCTypes.DriveActionType;
import org.usfirst.frc.team6500.robot.auto.ForwardScale;

public class MiddleScale implements TRCAutoRoute {
	private static double inches0 = 100.0;
	
    private boolean left;
    
	
	public MiddleScale(boolean left) {
		if(left) { inches0 *= -1; };
		
		this.left = left;
	}
	
	@Override
    public void run()
    {
        TRCDrivePID.run(DriveActionType.Right, inches0);
		
		ForwardScale thing = new ForwardScale(left);
		thing.run();
	}
}
