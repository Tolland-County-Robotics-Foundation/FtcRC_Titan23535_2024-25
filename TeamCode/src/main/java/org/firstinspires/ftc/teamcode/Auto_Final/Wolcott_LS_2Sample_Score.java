package org.firstinspires.ftc.teamcode.Auto_Final;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Wolcott-LS_2Sample_Score", group="Autonomous")

public class Wolcott_LS_2Sample_Score extends LinearOpMode {

    /* Declare OpMode members. */
    private DcMotor leftFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightBackDrive = null;

    private CRServo claw        = null;
    private DcMotor intakeArm   = null;

    //Creating objects for long arm
    private Servo basket          = null;
    private DcMotor leftArmLift     = null;
    private DcMotor rightArmLift    = null;


    private ElapsedTime runtime = new ElapsedTime();

    // Calculate the COUNTS_PER_INCH for your specific drive train.
    // Go to your motor vendor website to determine your motor's COUNTS_PER_MOTOR_REV
    // For external drive gearing, set DRIVE_GEAR_REDUCTION as needed.
    // For example, use a value of 2.0 for a 12-tooth spur gear driving a 24-tooth spur gear.
    // This is gearing DOWN for less speed and more torque.
    // For gearing UP, use a gear ratio less than 1.0. Note this will affect the direction of wheel rotation.
    static final double COUNTS_PER_MOTOR_REV = 28;    //
    static final double DRIVE_GEAR_REDUCTION = 12.0;     // 4:1 External Gearing.
    static final double WHEEL_DIAMETER_INCHES = 2.95276;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double DRIVE_SPEED = 0.5;


    @Override
    public void runOpMode() {

        // Initialize the drive system variables.
        leftFrontDrive = hardwareMap.get(DcMotor.class, "leftFront");
        leftBackDrive = hardwareMap.get(DcMotor.class, "leftBack");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rightFront");
        rightBackDrive = hardwareMap.get(DcMotor.class, "rightBack");


        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // When run, this OpMode should start both motors driving forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
        leftFrontDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotorSimple.Direction.REVERSE);

        // Enable brake

        leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        /// Initialization of intake mechanism ----------------------------------------------

        claw        = hardwareMap.get(CRServo.class, "claw");
        intakeArm   = hardwareMap.get(DcMotor.class, "intakeArm");

        claw.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeArm.setDirection(DcMotorSimple.Direction.FORWARD);

        intakeArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        /// Initialization of long arm mechanism ---------------------------------------------

        basket          = hardwareMap.get(Servo.class, "BasketArm");
        leftArmLift     = hardwareMap.get(DcMotor.class, "LeftArmLift");
        rightArmLift    = hardwareMap.get(DcMotor.class, "RightArmLift");

        basket.setDirection(Servo.Direction.FORWARD);
        leftArmLift.setDirection(DcMotorSimple.Direction.FORWARD);
        rightArmLift.setDirection(DcMotorSimple.Direction.REVERSE);

        leftArmLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightArmLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Starting at", "%7d :%7d",
                leftFrontDrive.getCurrentPosition(),
                leftBackDrive.getCurrentPosition(),
                rightFrontDrive.getCurrentPosition(),
                rightBackDrive.getCurrentPosition());
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();


        /*
            The drive modes are: forward, backward, left, right, turnLeft, turnRight
         */

        /// 3 sample score auto from left side ------------------------------------------------------------------------------------------------------------

        //Moves robot to scoring position from start point
        basket.setPosition(0.45);
        drive("left", 20, DRIVE_SPEED, 10.0);
        drive("forward", 5, DRIVE_SPEED, 10.0);
        drive("turnRight", 8, DRIVE_SPEED, 10.0);
        drive("backward", 2, DRIVE_SPEED, 10.0);
        //Puts intake down
        intakeArm.setPower(-0.5);
        sleep(750);
        intakeArm.setPower(0);
        //Scores 1st sample and reset
        basket.setPosition(0.45);
        leftArmLift.setPower(1);
        rightArmLift.setPower(1);
        sleep(2500);
        basket.setPosition(1);
        sleep(1500);
        basket.setPosition(0.45);
        sleep(500);
        leftArmLift.setPower(-1);
        rightArmLift.setPower(-1);
        sleep(2500);
        leftArmLift.setPower(0);
        rightArmLift.setPower(0);
        //Moves robot to right most sample
        drive("forward", 2, DRIVE_SPEED, 10.0);
        drive("turnLeft", 8, DRIVE_SPEED, 10.0);
        drive("right", 6, DRIVE_SPEED, 10.0);
        drive("forward", 6, DRIVE_SPEED, 10.0);
        //Grabs 2nd sample
        claw.setPower(1);
        //Moves back to scoring position
        drive("backward", 6, DRIVE_SPEED, 10.0);
        drive("left", 6, DRIVE_SPEED, 10.0);
        drive("turnRight", 8, DRIVE_SPEED, 10.0);
        drive("backward", 2, DRIVE_SPEED, 10.0);
        //Transfers sample to basket
        basket.setPosition(0.55);
        intakeArm.setPower(0.50);
        sleep(1500);
        intakeArm.setPower(0);
        claw.setPower(-1);
        sleep(250);
        //Places intake down
        intakeArm.setPower(-0.5);
        sleep(750);
        intakeArm.setPower(0);
        //Scores 2nd sample and reset
        basket.setPosition(0.45);
        leftArmLift.setPower(1);
        rightArmLift.setPower(1);
        sleep(2500);
        basket.setPosition(1);
        sleep(1500);
        basket.setPosition(0.45);
        sleep(500);
        leftArmLift.setPower(-1);
        rightArmLift.setPower(-1);
        sleep(2500);
        leftArmLift.setPower(0);
        rightArmLift.setPower(0);

/// 3 sample score auto from left side ------------------------------------------------------------------------------------------------------------

        /*

        KEY
        intakeArm.setPower(0.25); // Up
        claw.setPower(1); // Close
        basket.setPosition(1); // Drop
        basket.setPosition(0.55); // Collect
        basket.setPosition(0.45); // Move

        leftArmLift.setPower(1);
        rightArmLift.setPower(1);
        */

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);  // pause to display final telemetry message.
    }

    /*
     *  Method to perform a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
     *  Move will stop if any of three conditions occur:
     *  1) Move gets to the desired position
     *  2) Move runs out of time
     *  3) Driver stops the OpMode running.
     */
    public void drive(String driveMode, double distance, double speed, double timeoutS) {

        int newLeftFrontTarget = 0;
        int newLeftBackTarget = 0;
        int newRightFrontTarget = 0;
        int newRightBackTarget = 0;

        // Ensure that the OpMode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller

            if (driveMode == "forward") {
                newLeftFrontTarget = leftFrontDrive.getCurrentPosition() + (int) (distance * COUNTS_PER_INCH);
                newLeftBackTarget = leftBackDrive.getCurrentPosition() + (int) (distance * COUNTS_PER_INCH);
                newRightFrontTarget = rightFrontDrive.getCurrentPosition() + (int) (distance * COUNTS_PER_INCH);
                newRightBackTarget = rightBackDrive.getCurrentPosition() + (int) (distance * COUNTS_PER_INCH);
            } else if (driveMode == "backward") {
                newLeftFrontTarget = leftFrontDrive.getCurrentPosition() + (int) (-distance * COUNTS_PER_INCH);
                newLeftBackTarget = leftBackDrive.getCurrentPosition() + (int) (-distance * COUNTS_PER_INCH);
                newRightFrontTarget = rightFrontDrive.getCurrentPosition() + (int) (-distance * COUNTS_PER_INCH);
                newRightBackTarget = rightBackDrive.getCurrentPosition() + (int) (-distance * COUNTS_PER_INCH);
            } else if (driveMode == "right") {
                newLeftFrontTarget = leftFrontDrive.getCurrentPosition() + (int) (distance * COUNTS_PER_INCH);
                newLeftBackTarget = leftBackDrive.getCurrentPosition() + (int) (-distance * COUNTS_PER_INCH);
                newRightFrontTarget = rightFrontDrive.getCurrentPosition() + (int) (-distance * COUNTS_PER_INCH);
                newRightBackTarget = rightBackDrive.getCurrentPosition() + (int) (distance * COUNTS_PER_INCH);

            } else if (driveMode == "left") {
                newLeftFrontTarget = leftFrontDrive.getCurrentPosition() + (int) (-distance * COUNTS_PER_INCH);
                newLeftBackTarget = leftBackDrive.getCurrentPosition() + (int) (distance * COUNTS_PER_INCH);
                newRightFrontTarget = rightFrontDrive.getCurrentPosition() + (int) (distance * COUNTS_PER_INCH);
                newRightBackTarget = rightBackDrive.getCurrentPosition() + (int) (-distance * COUNTS_PER_INCH);

            } else if (driveMode == "turnLeft") {
                newLeftFrontTarget = leftFrontDrive.getCurrentPosition() + (int) (-distance * COUNTS_PER_INCH);
                newLeftBackTarget = leftBackDrive.getCurrentPosition() + (int) (-distance * COUNTS_PER_INCH);
                newRightFrontTarget = rightFrontDrive.getCurrentPosition() + (int) (distance * COUNTS_PER_INCH);
                newRightBackTarget = rightBackDrive.getCurrentPosition() + (int) (distance * COUNTS_PER_INCH);

            } else if (driveMode == "turnRight") {
                newLeftFrontTarget = leftFrontDrive.getCurrentPosition() + (int) (distance * COUNTS_PER_INCH);
                newLeftBackTarget = leftBackDrive.getCurrentPosition() + (int) (distance * COUNTS_PER_INCH);
                newRightFrontTarget = rightFrontDrive.getCurrentPosition() + (int) (-distance * COUNTS_PER_INCH);
                newRightBackTarget = rightBackDrive.getCurrentPosition() + (int) (-distance * COUNTS_PER_INCH);
            }

            }

            leftFrontDrive.setTargetPosition(newLeftFrontTarget);
            leftBackDrive.setTargetPosition(newLeftBackTarget);
            rightFrontDrive.setTargetPosition(newRightFrontTarget);
            rightBackDrive.setTargetPosition(newRightBackTarget);


            // Turn On RUN_TO_POSITION

            leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();

            leftFrontDrive.setPower(Math.abs(speed));
            leftBackDrive.setPower(Math.abs(speed));
            rightFrontDrive.setPower(Math.abs(speed));
            rightBackDrive.setPower(Math.abs(speed));


            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (leftFrontDrive.isBusy() && leftBackDrive.isBusy()
                            && rightFrontDrive.isBusy() && rightBackDrive.isBusy())) {


                // Display it for the driver.
                telemetry.addData("Running to", " %4d :%4d :%4d :%4d", newLeftFrontTarget, newLeftBackTarget, newRightFrontTarget, newRightBackTarget);
                telemetry.addData("Currently at", " at %4d :%4d :%4d :%4d",
                        leftFrontDrive.getCurrentPosition(), leftBackDrive.getCurrentPosition(), rightFrontDrive.getCurrentPosition(), rightBackDrive.getCurrentPosition());
                // telemetry.addData("Speed","%4d :%4d :%4d :%4d", leftFrontDrive.getPower(), leftBackDrive.getPower(), rightFrontDrive.getPower(), rightBackDrive.getPower());
                telemetry.update();
            }

            // Stop all motion;
            leftFrontDrive.setPower(0);
            leftBackDrive.setPower(0);
            rightFrontDrive.setPower(0);
            rightBackDrive.setPower(0);

            // Turn off RUN_TO_POSITION
            leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(250);   // optional pause after each move.
        }
    }
