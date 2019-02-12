package org.usfirst.frc.team6500.robot;


import org.usfirst.frc.team6500.robot.Constants;
import org.usfirst.frc.team6500.trc.auto.TRCDirectionalSystemAction;
import org.usfirst.frc.team6500.trc.auto.TRCDrivePID;
import org.usfirst.frc.team6500.trc.systems.TRCDirectionalSystem;
import org.usfirst.frc.team6500.trc.systems.TRCDriveInput;
import org.usfirst.frc.team6500.trc.util.TRCNetworkData;
import org.usfirst.frc.team6500.trc.util.TRCTypes;
import org.usfirst.frc.team6500.trc.wrappers.sensors.TRCEncoderSet;
import org.usfirst.frc.team6500.trc.wrappers.sensors.TRCGyroBase;
import org.usfirst.frc.team6500.trc.wrappers.systems.drives.TRCMecanumDrive;
import org.usfirst.frc.team6500.trc.util.TRCController;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.RobotBase;


public class Robot extends TimedRobot
{
    // Robot member definitions
    TRCGyroBase gyro;
    TRCEncoderSet encoders;
    TRCMecanumDrive drive;
    TRCDirectionalSystem lift, grabber;
    int positionOptionID = 1;
    int targetOptionID = 2;

    AutoRun richardRunner;
    boolean hasCompleted;

    /**
     * Code here will run once as soon as the robot starts
     */
    @Override
    public void robotInit()
    {
        // Setup: Communications
        TRCNetworkData.initializeNetworkData(TRCTypes.DataInterfaceType.Board);
        TRCNetworkData.createDataPoint("Encoder Output");
        TRCNetworkData.createDataPoint("Encoder 0");
        TRCNetworkData.createDataPoint("Encoder 1");
        TRCNetworkData.createDataPoint("Encoder 2");
        TRCNetworkData.createDataPoint("Encoder 3");
        // TRCNetworkVision.initializeVision();
        //TRCCamera.initializeCamera();

        // Setup: Systems: Drivetrain
        boolean[] inversion = {true, true, true, true};
        drive = new TRCMecanumDrive(Constants.DRIVE_WHEEL_PORTS, Constants.DRIVE_WHEEL_TYPES, inversion, true);

        // Setup: Systems: Directional
        lift = new TRCDirectionalSystem(Constants.LIFT_MOTORS, Constants.LIFT_MOTOR_TYPES, true, 1.0, -0.6);
        //grabber = new TRCDirectionalSystem(Constants.GRABBER_MOTORS, Constants.GRABBER_MOTOR_TYPES, true, 1.0, -1.0);
        TRCDirectionalSystemAction.registerSystem("Lift", lift);
        //TRCDirectionalSystemAction.registerSystem("Grabber", grabber);

        // Setup: Systems: Sensors
        gyro = new TRCGyroBase(TRCTypes.GyroType.NavX);
        encoders = new TRCEncoderSet(Constants.ENCODER_INPUTS, Constants.ENCODER_DISTANCES_PER_PULSE, true, 4, Constants.ENCODER_TYPES);
        encoders.resetAllEncoders();

        // Setup: Input: Button Bindings: Grabber
        //TRCDriveInput.bindButton(Constants.INPUT_GUNNER_PORT, Constants.INPUT_GRAB_PULL_BUTTON, grabber::driveForward);
        //TRCDriveInput.bindButton(Constants.INPUT_GUNNER_PORT, Constants.INPUT_GRAB_RELEASE_BUTTON, grabber::driveReverse);
        //TRCDriveInput.bindButtonAbsence(Constants.INPUT_GUNNER_PORT, Constants.INPUT_GRAB_BUTTONS, grabber::fullStop);
    }

    /**
     * Code here will run once at the start of autonomous
     */
    @Override
    public void autonomousInit()
    {
        encoders.resetAllEncoders();
        gyro.reset();

        TRCDrivePID.initializeTRCDrivePID(encoders, gyro, drive, TRCTypes.DriveType.Mecanum, Constants.SPEED_AUTO);
        // TRCNetworkData.putOptions(Constants.OPTIONS_POSITIONS, positionOptionID);
        // TRCNetworkData.putOptions(Constants.OPTIONS_TARGETS, targetOptionID);

        richardRunner = new AutoRun(90);
        hasCompleted = false;
    }

    /**
     * Code here will run continously during autonomous
     */
    @Override
    public void autonomousPeriodic()
    {
        if (!hasCompleted)
        {
            richardRunner.run();
            hasCompleted = true;
        } else {
            drive.driveCartesian(0, 0, 0);
        }
    }

    /**
     * Code here will run once at the start of teleop
     */
    @Override
    public void teleopInit()
    {
        TRCDriveInput.initializeDriveInput(Constants.INPUT_PORT, Constants.SPEED_BASE, Constants.SPEED_BOOST);

        Runnable[] upActions = {lift::driveForward, lift::fullStop};
        Runnable[] downActions = {lift::driveReverse, lift::fullStop};

        TRCDriveInput.bindButton(TRCController.BUMPER_LEFT, upActions);
        TRCDriveInput.bindButton(TRCController.BUMPER_RIGHT, downActions);
    }

    /**
     * Code here will run continously during teleop
     */
    @Override
    public void teleopPeriodic()
    {
        // Check all inputs
        TRCDriveInput.updateBindings();
        // And drive the robot
        drive.driveCartesian(TRCDriveInput.getStickDriveParams());

        TRCNetworkData.updateDataPoint("Encoder Output", encoders.getAverageDistanceTraveled());
        TRCNetworkData.updateDataPoint("Encoder 0", encoders.getIndividualDistanceTraveled(0));
        TRCNetworkData.updateDataPoint("Encoder 1", encoders.getIndividualDistanceTraveled(1));
        TRCNetworkData.updateDataPoint("Encoder 2", encoders.getIndividualDistanceTraveled(2));
        TRCNetworkData.updateDataPoint("Encoder 3", encoders.getIndividualDistanceTraveled(3));
    }
    public static void main(String... args)
    {
        RobotBase.startRobot(Robot::new);
    }
}