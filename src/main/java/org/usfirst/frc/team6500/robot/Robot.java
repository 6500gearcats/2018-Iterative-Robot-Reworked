package org.usfirst.frc.team6500.robot;

import java.io.*;
import java.net.*;

import org.usfirst.frc.team6500.trc.util.*;
/*
import org.usfirst.frc.team6500.robot.Constants;
import org.usfirst.frc.team6500.trc.auto.TRCDirectionalSystemAction;
import org.usfirst.frc.team6500.trc.auto.TRCDrivePID;
import org.usfirst.frc.team6500.trc.systems.TRCDirectionalSystem;
import org.usfirst.frc.team6500.trc.systems.TRCDriveInput;
import org.usfirst.frc.team6500.trc.util.TRCController;
import org.usfirst.frc.team6500.trc.util.TRCDriveParams;
import org.usfirst.frc.team6500.trc.util.TRCNetworkData;
import org.usfirst.frc.team6500.trc.util.TRCTypes.*;
import org.usfirst.frc.team6500.trc.wrappers.sensors.TRCEncoderSet;
import org.usfirst.frc.team6500.trc.wrappers.sensors.TRCGyroBase;
import org.usfirst.frc.team6500.trc.wrappers.systems.drives.TRCMecanumDrive;
*/

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.RobotBase;

import org.usfirst.frc.team6500.robot.auto.AutoAlign;


public class Robot extends TimedRobot
{
    // Robot member definitions
    TRCGyroBase gyro;
    TRCEncoderSet encoders;
    TRCMecanumDrive drive;
    TRCDirectionalSystem lift, grabber;
    AnalogInput leftProx, rightProx;
    DigitalInput hE;
    int positionOptionID = 1;
    int targetOptionID = 2;


    /**
     * Code here will run once as soon as the robot starts
     */
    @Override
    public void robotInit() 
    {
		int portNumber = 7272;
		
		try (
			 ServerSocket serverSocket = new ServerSocket(portNumber);
			 Socket clientSocket = serverSocket.accept();
			 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			 ) {
			String inputLine;
			while ((inputLine = in.readLine()) != null)
			{
				out.println(inputLine);
			}
		} catch (IOException e) {
			System.out.println("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
			System.out.println(e.getMessage());
        }
        // Setup: Communications
        TRCNetworkData.initializeNetworkData(DataInterfaceType.Board);
        TRCNetworkData.createDataPoint("Encoder Output");
        TRCNetworkData.createDataPoint("Encoder 0");
        TRCNetworkData.createDataPoint("Encoder 1");
        TRCNetworkData.createDataPoint("Encoder 2");
        TRCNetworkData.createDataPoint("Encoder 3");
        TRCNetworkData.createDataPoint("Gyro");
        TRCNetworkData.createDataPoint("Left Proximity");
        TRCNetworkData.createDataPoint("Right Proximity");
        TRCNetworkData.createDataPoint("Hall Effect");
        TRCNetworkVision.initializeVision();
        //TRCCamera.initializeCamera();


        // Setup: Systems: Drivetrain
        drive = new TRCMecanumDrive(Constants.DRIVE_WHEEL_PORTS, Constants.DRIVE_WHEEL_TYPES, Constants.DRIVE_WHEEL_INVERTS, true);

        // Setup: Systems: Directional
        lift = new TRCDirectionalSystem(Constants.LIFT_MOTORS, Constants.LIFT_MOTOR_TYPES, true, 1.0, -0.6);
        //grabber = new TRCDirectionalSystem(Constants.GRABBER_MOTORS, Constants.GRABBER_MOTOR_TYPES, true, 1.0, -1.0);
        TRCDirectionalSystemAction.registerSystem("Lift", lift);
        //TRCDirectionalSystemAction.registerSystem("Grabber", grabber);

        // Setup: Systems: Sensors
        gyro = new TRCGyroBase(GyroType.NavX);
        encoders = new TRCEncoderSet(Constants.ENCODER_INPUTS, Constants.ENCODER_DISTANCES_PER_PULSE, true, 4, Constants.ENCODER_TYPES);
        encoders.resetAllEncoders();
        leftProx  = new AnalogInput(Constants.PROXIMITY_LEFT);
        rightProx = new AnalogInput(Constants.PROXIMITY_RIGHT);
        hE = new DigitalInput(0);
        AutoAlign.setupAlignment(drive, leftProx, rightProx);


        // Setup: Autonomous
        TRCDrivePID.initializeTRCDrivePID(encoders, gyro, drive, DriveType.Mecanum, Constants.SPEED_AUTO);
        AutoAlign.setupAlignment(drive, leftProx, rightProx);

        // Setup: Autonomous: Options
        TRCNetworkData.putOptions(Constants.OPTIONS_POSITIONS, positionOptionID);
        TRCNetworkData.putOptions(Constants.OPTIONS_TARGETS, targetOptionID);


        // Setup: Input
        
        TRCDriveInput.initializeDriveInput(Constants.INPUT_PORTS, Constants.INPUT_TYPES, Constants.SPEED_BASE, Constants.SPEED_BOOST);

        // Setup: Input: Button Bindings: Grabber
        //TRCDriveInput.bindButton(Constants.INPUT_GUNNER_PORT, Constants.INPUT_GRAB_PULL_BUTTON, grabber::driveForward);
        //TRCDriveInput.bindButton(Constants.INPUT_GUNNER_PORT, Constants.INPUT_GRAB_RELEASE_BUTTON, grabber::driveReverse);
        //TRCDriveInput.bindButtonAbsence(Constants.INPUT_GUNNER_PORT, Constants.INPUT_GRAB_BUTTONS, grabber::fullStop);

        // Setup: Input: Button Bindings: Lift
        // TRCDriveInput.bindButtonPress(Constants.INPUT_DRIVER_PORT, Constants.INPUT_LIFT_ELEVATE_BUTTON, lift::driveForward);
        // TRCDriveInput.bindButtonPress(Constants.INPUT_DRIVER_PORT, Constants.INPUT_LIFT_DESCEND_BUTTON, lift::driveReverse);
        // TRCDriveInput.bindButtonAbsence(Constants.INPUT_DRIVER_PORT, Constants.INPUT_LIFT_BUTTONS, lift::fullStop);

        TRCDriveInput.bindButtonPress(Constants.INPUT_DRIVER_PORT, 4, AutoAlign::alignWithFloorTape);
    }

    /**
     * Code here will run once at the start of autonomous
     */
    /*
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
    */

    /**
     * Code here will run continously during autonomous
     */
    /*
    @Override
    public void autonomousPeriodic()
    {
        if (!hasCompleted)
        {
            TRCDrivePID.driveAround(12, 90);
            hasCompleted = true;
        }
    }
    */

    /**
     * Code here will run once at the start of teleop
     */
    /*
    @Override
    public void teleopInit()
    {
        TRCDriveInput.initializeDriveInput(Constants.INPUT_PORT, Constants.SPEED_BASE, Constants.SPEED_BOOST);

        Runnable[] upActions = {lift::driveForward, lift::fullStop};
        Runnable[] downActions = {lift::driveReverse, lift::fullStop};

        TRCDriveInput.bindButton(TRCController.BUMPER_LEFT, upActions);
        TRCDriveInput.bindButton(TRCController.BUMPER_RIGHT, downActions);
    }
    */

    /**
     * Code here will run continously during teleop
     */
    /*
    @Override
    public void teleopPeriodic()
    {
        // Check all inputs
        TRCDriveInput.checkButtonBindings();
        // And drive the robot
        TRCDriveParams params = TRCDriveInput.getStickDriveParams(Constants.INPUT_DRIVER_PORT);
        TRCController controller = TRCDriveInput.getController(Constants.INPUT_DRIVER_PORT);
        params.setRawX(controller.getAxis(XboxAxisType.LeftX));
        params.setRawY(controller.getAxis(XboxAxisType.LeftY));
        params.setRawZ(controller.getAxis(XboxAxisType.RightX));
        drive.driveCartesian(params);

        TRCNetworkData.updateDataPoint("Encoder Output", encoders.getAverageDistanceTraveled(DirectionType.ForwardBackward));
        TRCNetworkData.updateDataPoint("Encoder 0", encoders.getIndividualDistanceTraveled(0));
        TRCNetworkData.updateDataPoint("Encoder 1", encoders.getIndividualDistanceTraveled(1));
        TRCNetworkData.updateDataPoint("Encoder 2", encoders.getIndividualDistanceTraveled(2));
        TRCNetworkData.updateDataPoint("Encoder 3", encoders.getIndividualDistanceTraveled(3));
        TRCNetworkData.updateDataPoint("Gyro", gyro.getAngle());
        TRCNetworkData.updateDataPoint("Left Proximity", AutoAlign.calculateUltrasonicDistance(leftProx.getVoltage()));
        TRCNetworkData.updateDataPoint("Right Proximity", AutoAlign.calculateUltrasonicDistance(rightProx.getVoltage()));
        TRCNetworkData.updateDataPoint("Hall Effect", hE.get());
    }
    */
    public static void main(String... args)
    {
        RobotBase.startRobot(Robot::new);
    }
}