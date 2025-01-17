package org.firstinspires.ftc.teamcode.TestCodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Drive_Mechanisms.Drive_v1;
import org.firstinspires.ftc.teamcode.Sensor_Mechanisms.Blinkin_v1;
import org.firstinspires.ftc.teamcode.Sensor_Mechanisms.Color_Sensor_v1;

import java.util.Objects;

@TeleOp(name = "Full Game Code 9", group = "test")
public class FullGameCode_V9 extends OpMode {
    // Creating an object from Drive_V1 class
    Drive_v1 drive = new Drive_v1();
    Color_Sensor_v1 color = new Color_Sensor_v1();
    Blinkin_v1 light = new Blinkin_v1();
    //Update
    // Creating an object from ElapsedTime class to have run time information
    private final ElapsedTime runtime = new ElapsedTime();

    //Creating variables
    String speedCap = "Normal";
    Boolean rightColor;
    Boolean redSpy;
    double speed_percentage = 40.0;

    //Creating objects for intake
    private CRServo claw        = null;
    private DcMotor intakeArm   = null;

    //Creating objects for long arm
    private Servo basket          = null;
    private DcMotor leftArmLift     = null;
    private DcMotor rightArmLift    = null;


    @Override
    public void init()
    {
        // Wait for the game to start (driver presses PLAY)
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Resetting runtime
        runtime.reset();

        telemetry.addData(">", "Press Start To Run The Robot" );
        // Use init method to do the initialization of drive object
        drive.init(hardwareMap);

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

    }
    @Override
    public void loop()
    {
        // Set the speed cap for driver 1
        if (gamepad1.y) {
            speedCap = "Max";
            speed_percentage = 90.0;
        } else if (gamepad1.b) {
            speedCap = "Fast";
            speed_percentage = 65.0;
        } else if (gamepad1.x) {
            speedCap = "Normal";
            speed_percentage = 40.0;
        } else if (gamepad1.a) {
            speedCap = "Slow";
            speed_percentage = 25.0;
        }

        /* Uses left joystick to go forward, backward, left, and right, and right joystick to rotate.

           Left joystick up --> forward

           Left joystick down --> Backward

           Left joystick right --> Right

           Left joystick left --> Left

           Right joystick left --> rotate left

           Right joystick right --> rotate right

        */

        double axial   = -gamepad1.left_stick_y;  // Note: pushing stick forward gives negative value
        double lateral =  gamepad1.left_stick_x;
        double yaw     =  gamepad1.right_stick_x;
        /*
        boolean goForward  = gamepad1.dpad_up;
        boolean goBackward = gamepad1.dpad_down;
        boolean goLeft  = gamepad1.dpad_left;
        boolean goRight  = gamepad1.dpad_right;
        */



        drive.setDriveMotorPower(axial, lateral, yaw, speed_percentage);


        //Display Runtime
        telemetry.addData("Status", "Run Time: " + runtime);
        //Displaying Axial,Lateral,Yaw AKA horizontal, vertical, and spin
        telemetry.addData("Axial:", axial);
        telemetry.addData("Lateral:",lateral);
        telemetry.addData("Yaw:", yaw);
        telemetry.addData("Current Speed", speedCap);
        telemetry.addData("Speed percentage: ",speed_percentage);


        /// intake mechanism ----------------------------------------------------------------------

        double intakeArmPower = gamepad2.left_stick_y * 0.5;
        intakeArm.setPower(intakeArmPower);

        if (gamepad2.right_stick_x > 0) {
            claw.setPower(1);
        } else if ((gamepad2.right_stick_x < 0) || (!rightColor)) {
            claw.setPower(-1);
        } else {
            claw.setPower(0.0);
        }

        telemetry.addData("intake arm power: ", intakeArmPower);

        telemetry.addData("claw power: ", claw.getPower());

        /// Long arm mechanism ------------------------------------------------------------------

        /// Long arm ---------------------------------------------------------

        double armPower;
        boolean noMove;

        if (gamepad2.left_trigger > 0) {
            armPower = -gamepad2.left_trigger;
            noMove = false;
        } else if (gamepad2.right_trigger > 0) {
            armPower = gamepad2.right_trigger;
            noMove = false;
        } else {
            armPower = 0.065;
            noMove = true;
        }

        leftArmLift.setPower(armPower);
        rightArmLift.setPower(armPower);

        /// Basket -----------------------------------------------------------

        if (gamepad2.dpad_down) {
            basket.setPosition(1); // Drop
        } else if (gamepad2.dpad_up) {
            basket.setPosition(0.55); // Collect
        } else if (gamepad2.dpad_left) {
            basket.setPosition(1);
        } else if (gamepad2.dpad_right) {
            basket.setPosition(0.45); // Move
        }

        if (armPower > 0.065 || armPower < 0.065) {
            basket.setPosition(0.45);
        }
        if (intakeArmPower < 0 && gamepad2.left_trigger == 0 && gamepad2.right_trigger == 0) {
            basket.setPosition(0.55);
        }



        telemetry.addData("intake arm power: ", armPower);

        telemetry.addData("basket position: ", basket.getPosition());

        /// Long Arm Slight Raise -----------------------------------------------



        /// Long Arm Slight Raise -----------------------------------------------

        ///Sensors --------------------------------------------------------------
        String colorOfBlock = color.blockColor();
        if (color.getDistance(DistanceUnit.INCH) < 0.5) {
            telemetry.addData("Block Color", color.blockColor());
        }

        if (redSpy) {
            if (Objects.equals(colorOfBlock, "blue")) {
                rightColor = false;
            }
        } else if (redSpy = false) {
            if (Objects.equals(colorOfBlock, "red")) {
                rightColor = false;
            }
        } else {
            rightColor = true;
        }



    }
}






