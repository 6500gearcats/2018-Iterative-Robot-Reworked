package org.usfirst.frc.team6500.robot;

import org.usfirst.frc.team6500.trc.util.TRCTypes.*;

public class Constants
{
    // Input Constants, Stick config
    // public static final int INPUT_DRIVER_PORT = 0;
    // public static final int INPUT_GUNNER_PORT = 1;
    // public static final int INPUT_PORTS[] = {INPUT_DRIVER_PORT, INPUT_GUNNER_PORT};
    // public static final ControllerType INPUT_TYPES[] = {ControllerType.Extreme3D, ControllerType.Extreme3D};

    // public static final int INPUT_GRAB_PULL_BUTTON = 3;
    // public static final int INPUT_GRAB_RELEASE_BUTTON = 5;
    // public static final Object INPUT_GRAB_BUTTONS[] = {INPUT_GRAB_PULL_BUTTON, INPUT_GRAB_RELEASE_BUTTON};

    // public static final int INPUT_LIFT_ELEVATE_BUTTON = 6;
    // public static final int INPUT_LIFT_DESCEND_BUTTON = 4;
    // public static final Object INPUT_LIFT_BUTTONS[] = {INPUT_LIFT_ELEVATE_BUTTON, INPUT_LIFT_DESCEND_BUTTON};

    // Input Constants, Xbox config
    public static final int INPUT_DRIVER_PORT = 0;
    public static final int INPUT_PORTS[] = {INPUT_DRIVER_PORT};
    public static final ControllerType INPUT_TYPES[] = {ControllerType.Xbox360};

    // public static final int INPUT_GRAB_PULL_BUTTON = 3;
    // public static final int INPUT_GRAB_RELEASE_BUTTON = 5;
    // public static final Object INPUT_GRAB_BUTTONS[] = {INPUT_GRAB_PULL_BUTTON, INPUT_GRAB_RELEASE_BUTTON};

    // public static final int INPUT_LIFT_ELEVATE_BUTTON = 6;
    // public static final int INPUT_LIFT_DESCEND_BUTTON = 4;
    // public static final Object INPUT_LIFT_BUTTONS[] = {INPUT_LIFT_ELEVATE_BUTTON, INPUT_LIFT_DESCEND_BUTTON};


    // Speed Constants
    public static final double SPEED_BASE = 0.85;
    public static final double SPEED_BOOST = 0.95;
    public static final double SPEED_AUTO = 0.45;


    // Options Constants
    public static final int OPTIONS_POSITION_LEFT = 0;
    public static final int OPTIONS_POSITION_MIDDLE = 1;
    public static final int OPTIONS_POSITION_RIGHT = 2;
    public static final String OPTIONS_POSITIONS[] = {"Left", "Middle", "Right"};
    public static final int OPTIONS_TARGET_SWITCH = 0;
    public static final int OPTIONS_TARGET_SCALE = 1;
    public static final int OPTIONS_TARGET_BASELINE = 2;
    public static final String OPTIONS_TARGETS[] = {"Switch", "Scale", "Baseline"};


    // Drive Constants
    public final static int DRIVE_WHEEL_FRONTLEFT = 3;
    public final static int DRIVE_WHEEL_REARLEFT = 1;
	public final static int DRIVE_WHEEL_FRONTRIGHT = 4;
    public final static int DRIVE_WHEEL_REARRIGHT = 2;
    public final static int DRIVE_WHEEL_PORTS[] = {DRIVE_WHEEL_FRONTLEFT, DRIVE_WHEEL_REARLEFT, DRIVE_WHEEL_FRONTRIGHT, DRIVE_WHEEL_REARRIGHT};
    public final static SpeedControllerType DRIVE_WHEEL_TYPES[] = {SpeedControllerType.CANTalonSRX, SpeedControllerType.CANTalonSRX, SpeedControllerType.CANTalonSRX, SpeedControllerType.CANTalonSRX};
    public final static boolean DRIVE_WHEEL_INVERTS[]           = {false, false, false, false};


    // Directional System (Grabber/Lift) Constants
    public final static int LIFT_MOTOR_LEFT = 0;
    public final static int LIFT_MOTOR_RIGHT = 1;
    public final static int LIFT_MOTORS[] = {LIFT_MOTOR_LEFT, LIFT_MOTOR_RIGHT};
    public final static SpeedControllerType LIFT_MOTOR_TYPES[] = {SpeedControllerType.Spark, SpeedControllerType.Spark};
    public final static double LIFT_TIME_SCALE_UP = 2.75;
    public final static double LIFT_TIME_SCALE_DOWN = 3.0;
    public final static double LIFT_TIME_SWITCH_UP = 0.78;
    
    public final static int GRABBER_MOTOR_LEFT = 4;
    public final static int GRABBER_MOTOR_RIGHT = 5;
    public final static int GRABBER_MOTORS[] = {GRABBER_MOTOR_LEFT, GRABBER_MOTOR_RIGHT};
    public final static SpeedControllerType GRABBER_MOTOR_TYPES[] = {SpeedControllerType.Spark, SpeedControllerType.Spark};
    public final static double GRABBER_TIME_EJECT = 1.0;


    // Encoder Constants
    // public final static int ENCODER_INPUT_FL_A = 4;
	// public final static int ENCODER_INPUT_FL_B = 5;
	// public final static int ENCODER_INPUT_FR_A = 6;
	// public final static int ENCODER_INPUT_FR_B = 7;
	// public final static int ENCODER_INPUT_RL_A = 2;
	// public final static int ENCODER_INPUT_RL_B = 3; 
	// public final static int ENCODER_INPUT_RR_A = 0;
    // public final static int ENCODER_INPUT_RR_B = 1;
    public final static int ENCODER_INPUTS[] = {DRIVE_WHEEL_REARRIGHT, 0, DRIVE_WHEEL_FRONTRIGHT, 0, DRIVE_WHEEL_REARLEFT, 0, DRIVE_WHEEL_FRONTLEFT, 0};
    public final static EncoderType ENCODER_TYPES[] = {EncoderType.Talon, EncoderType.Talon, EncoderType.Talon, EncoderType.Talon};

    public final static double[] ENCODER_DISTANCES_PER_PULSE = {0.53, 0.0240, 0.02832, 0.02542};

    public final static int REMOTECONTROL_ACTION_STOP = 0;
    public final static int REMOTECONTROL_ACTION_FORWARD = 12;
    public final static int REMOTECONTROL_ACTION_FORWARDRIGHT = 1;
    public final static int REMOTECONTROL_ACTION_RIGHT = 2;
    public final static int REMOTECONTROL_ACTION_BACKWARDRIGHT = 3;
    public final static int REMOTECONTROL_ACTION_BACKWARD = 4;
    public final static int REMOTECONTROL_ACTION_BACKWARDLEFT = 5;
    public final static int REMOTECONTROL_ACTION_LEFT = 6;
    public final static int REMOTECONTROL_ACTION_FORWARDLEFT = 7;
    public final static int REMOTECONTROL_ACTION_ROTATERIGHT = 8;
    public final static int REMOTECONTROL_ACTION_ROTATELEFT = 9;
    public final static int REMOTECONTROL_ACTION_CURVEFORWARDLEFT = 11;
    public final static int REMOTECONTROL_ACTION_CURVEFORWARDRIGHT = 10;

    public final static int REMOTECONTROL_DEFAULT_PORT = 7272; // default port will always be (from now on 'cause I say) 7272

    // Proximity Sensor Constants
    public final static int PROXIMITY_LEFT            = 0;
    public final static int PROXIMITY_RIGHT           = 1;
    public final static double PROXIMITY_THRESHOLD_MM = 500.0;
}