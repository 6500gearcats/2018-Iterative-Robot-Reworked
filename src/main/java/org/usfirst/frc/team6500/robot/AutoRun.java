package org.usfirst.frc.team6500.robot;

import org.usfirst.frc.team6500.trc.auto.TRCAutoRoute;
import org.usfirst.frc.team6500.trc.auto.TRCDrivePID;
import org.usfirst.frc.team6500.trc.util.TRCTypes.DriveActionType;

public class AutoRun implements TRCAutoRoute
{
    private double inches;

    public AutoRun(double length)
    {
        this.inches = length;
    }

    @Override
    public void run() 
    {
        TRCDrivePID.run(DriveActionType.Rotate, this.inches);
    }
}