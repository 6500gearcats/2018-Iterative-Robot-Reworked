package org.usfirst.frc.team6500.robot;


import org.usfirst.frc.team6500.robot.Constants;
import org.usfirst.frc.team6500.robot.auto.AutoMapper;
import org.usfirst.frc.team6500.trc.auto.TRCDirectionalSystemAction;
import org.usfirst.frc.team6500.trc.auto.TRCDrivePID;
import org.usfirst.frc.team6500.trc.sensors.TRCCamera;
import org.usfirst.frc.team6500.trc.sensors.TRCNetworkVision;
import org.usfirst.frc.team6500.trc.systems.TRCDirectionalSystem;
import org.usfirst.frc.team6500.trc.systems.TRCDriveInput;
import org.usfirst.frc.team6500.trc.util.TRCNetworkData;
import org.usfirst.frc.team6500.trc.util.TRCTypes;
import org.usfirst.frc.team6500.trc.wrappers.sensors.TRCEncoderSet;
import org.usfirst.frc.team6500.trc.wrappers.sensors.TRCGyroBase;
import org.usfirst.frc.team6500.trc.wrappers.systems.drives.TRCMecanumDrive;

import edu.wpi.first.wpilibj.TimedRobot;


public class Robot extends TimedRobot
{
    // Robot member definitions
    TRCGyroBase gyro;
    TRCEncoderSet encoders;
    TRCMecanumDrive drive;
    TRCDirectionalSystem lift, grabber;
    int positionOptionID, targetOptionID;


    /**
     * Code here will run once as soon as the robot starts
     */
    @Override
    public void robotInit()
    {
        // Setup: Communications
        TRCNetworkData.initializeNetworkData(TRCTypes.DataInterfaceType.Board);
        TRCNetworkVision.initializeVision();
        TRCCamera.initializeCamera();


        // Setup: Systems: Drivetrain
        drive = new TRCMecanumDrive(Constants.DRIVE_WHEEL_PORTS, Constants.DRIVE_WHEEL_TYPES);

        // Setup: Systems: Directional
        lift = new TRCDirectionalSystem(Constants.LIFT_MOTORS, Constants.LIFT_MOTOR_TYPES, true, 1.0, -0.6);
        grabber = new TRCDirectionalSystem(Constants.GRABBER_MOTORS, Constants.GRABBER_MOTOR_TYPES, true, 1.0, -1.0);
        TRCDirectionalSystemAction.registerSystem("Lift", lift);
        TRCDirectionalSystemAction.registerSystem("Grabber", grabber);

        // Setup: Systems: Sensors
        gyro = new TRCGyroBase(TRCTypes.GyroType.NavX);
        encoders = new TRCEncoderSet(Constants.ENCODER_INPUTS, Constants.ENCODER_DISTANCE_PER_PULSE, false, 4);


        // Setup: Autonomous
        TRCDrivePID.initializeTRCDrivePID(encoders, gyro, drive, TRCTypes.DriveType.Mecanum, Constants.SPEED_AUTO);

        // Setup: Autonomous: Options
        positionOptionID = TRCNetworkData.putOptions(Constants.OPTIONS_POSITIONS);
        targetOptionID = TRCNetworkData.putOptions(Constants.OPTIONS_TARGETS);


        // Setup: Input
        TRCDriveInput.initializeDriveInput(Constants.INPUT_PORTS, Constants.SPEED_BASE, Constants.SPEED_BOOST);

        // Setup: Input: Button Bindings: Grabber
        TRCDriveInput.bindButton(Constants.INPUT_GUNNER_PORT, Constants.INPUT_GRAB_PULL_BUTTON, grabber::driveForward);
        TRCDriveInput.bindButton(Constants.INPUT_GUNNER_PORT, Constants.INPUT_GRAB_RELEASE_BUTTON, grabber::driveReverse);
        TRCDriveInput.bindButtonAbsence(Constants.INPUT_GUNNER_PORT, Constants.INPUT_GRAB_BUTTONS, grabber::fullStop);

        // Setup: Input: Button Bindings: Lift
        TRCDriveInput.bindButton(Constants.INPUT_GUNNER_PORT, Constants.INPUT_LIFT_ELEVATE_BUTTON, lift::driveForward);
        TRCDriveInput.bindButton(Constants.INPUT_GUNNER_PORT, Constants.INPUT_LIFT_DESCEND_BUTTON, lift::driveReverse);
        TRCDriveInput.bindButtonAbsence(Constants.INPUT_GUNNER_PORT, Constants.INPUT_LIFT_BUTTONS, lift::fullStop);
    }

    /**
     * Code here will run once at the start of autonomous
     */
    @Override
    public void autonomousInit()
    {
        encoders.resetAllEncoders();
        gyro.reset();

        AutoMapper.determineAndRunRoute(TRCNetworkData.getSelection(positionOptionID), TRCNetworkData.getSelection(targetOptionID));
    }

    /**
     * Code here will run continously during autonomous
     */
    @Override
    public void autonomousPeriodic()
    {
        // Nothing to do here ¯\_(ツ)_/¯
    }

    /**
     * Code here will run once at the start of teleop
     */
    @Override
    public void teleopInit()
    {
        // Nothing to do here ¯\_(ツ)_/¯
    }

    /**
     * Code here will run continously during teleop
     */
    @Override
    public void teleopPeriodic()
    {
        // Check all inputs
        TRCDriveInput.updateDriveInput();
        // And drive the robot
        drive.driveCartesian(TRCDriveInput.getStickDriveParams(Constants.INPUT_DRIVER_PORT));
    }
}