package org.usfirst.frc.team6500.robot.auto;

import org.usfirst.frc.team6500.robot.Constants;
import org.usfirst.frc.team6500.trc.auto.TRCAutoRoute;

import edu.wpi.first.wpilibj.DriverStation;

public class AutoMapper
{
    private static boolean isLeft(int position) { return (position == Constants.OPTIONS_POSITION_LEFT);}

    public static TRCAutoRoute determineRoute(int position, int target)
    {
        // Analyze match-specific configuration
        String gameData = DriverStation.getInstance().getGameSpecificMessage();

        if(!(gameData.length() > 0))
        {
        	System.err.println("No game data found! Exiting Auto.");
        	return null;
        }

        char switchData = gameData.charAt(0);
        int switchPos;
        if (switchData == 'L') { switchPos = Constants.OPTIONS_POSITION_LEFT; } else { switchPos = Constants.OPTIONS_POSITION_RIGHT; }
        
        char scaleData = gameData.charAt(1);
        int scalePos;
        if (scaleData == 'L') { scalePos = Constants.OPTIONS_POSITION_LEFT; } else { scalePos = Constants.OPTIONS_POSITION_RIGHT; }


        // Determine best route
        TRCAutoRoute bestRoute = null;

        if(target == Constants.OPTIONS_TARGET_BASELINE) { bestRoute = new AutoLine(); }
		
		//If we're in the middle, check to see if we want the scale or the switch, and do that
		if(position == Constants.OPTIONS_POSITION_MIDDLE) {
			if(target == Constants.OPTIONS_TARGET_SWITCH) {
				return new MiddleSwitch(isLeft(switchPos));
			} else {
				return new MiddleScale(isLeft(scalePos));
			}
		}
		
		//If we're not on the middle, check the priority, then if that's on the far side, do the other thing
		if(target == Constants.OPTIONS_POSITION_MIDDLE) {
			if(position == switchPos) {			bestRoute = new ForwardSwitch(isLeft(position));
			} else if(position == scalePos) {	bestRoute = new ForwardScale(isLeft(position));
			} else {							bestRoute = new OppositeScale(isLeft(position));
		}}
		else
		{
			if(position == scalePos) { 			bestRoute = new ForwardScale(isLeft(position));
			} else if(position == switchPos) { 	bestRoute = new ForwardSwitch(isLeft(position));
			} else { 							bestRoute = new OppositeScale(isLeft(position));
        }}
        

        return bestRoute;
    }

    public static void determineAndRunRoute(int postion, int target)
    {
        try
        {
            TRCAutoRoute route = determineRoute(postion, target);
            route.run();
        }
        catch (Exception e)
        {
            return;
        }
    }
}