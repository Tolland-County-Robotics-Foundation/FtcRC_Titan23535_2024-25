package org.firstinspires.ftc.teamcode.TestCodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Drive_Mechanisms.Drive_v1;
import org.firstinspires.ftc.teamcode.Sensor_Mechanisms.Color_Sensor_v1;

import java.util.Objects;

@TeleOp(name = "Full Game Code 6", group = "test")
public class FullGameCode_V6 extends OpMode {
    // Creating an object from Drive_V1 class
    Drive_v1 drive = new Drive_v1();
    Color_Sensor_v1 colors = new Color_Sensor_v1();

    // Creating an object from ElapsedTime class to have run time information
    private final ElapsedTime runtime = new ElapsedTime();

    //Creating two variables for capping the speed
    String speedcap = "Normal";

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

        telemetry.addData("Are you on the Red Alliance or the Blue Alliance?", "Left trigger for Red, right trigger for Blue");
        while (true) {
            if (gamepad1.left_trigger > 0.1) {
                redSpy = true;
                break;
            }
            if (gamepad1.right_trigger > 0.1) {
                redSpy = false;
                break;
            }
        }
    }
    @Override
    public void loop()
    {
        // Set the speed cap for driver 1
        if (gamepad1.y) {
            speedcap = "Max";
            speed_percentage = 90.0;
        } else if (gamepad1.b) {
            speedcap = "Fast";
            speed_percentage = 65.0;
        } else if (gamepad1.x) {
            speedcap = "Normal";
            speed_percentage = 40.0;
        } else if (gamepad1.a) {
            speedcap = "Slow";
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
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        //Displaying Axial,Lateral,Yaw AKA horizontal, vertical, and spin
        telemetry.addData("Axial:", axial);
        telemetry.addData("Lateral:",lateral);
        telemetry.addData("Yaw:", yaw);
        telemetry.addData("Current Speed", speedcap);
        telemetry.addData("Speed percentage: ",speed_percentage);


        /// intake mechanism ----------------------------------------------------------------------

        double intakeArmPower = gamepad2.left_stick_y;
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

        double armPower = 0.0;


        if (gamepad2.left_trigger > 0) {
            armPower = -gamepad2.left_trigger;
        } else if (gamepad2.right_trigger > 0) {
            armPower = gamepad2.right_trigger;
        } else {
            armPower = 0;
        }

        leftArmLift.setPower(armPower);
        rightArmLift.setPower(armPower);

        /// Basket -----------------------------------------------------------

        if (gamepad2.dpad_down) {
            basket.setPosition(0.8);
        } else if (gamepad2.dpad_up) {
            basket.setPosition(0.4);
        } else if (gamepad2.dpad_left) {
            basket.setPosition(1);
        } else if (gamepad2.dpad_right) {
            basket.setPosition(0);
        }

        telemetry.addData("intake arm power: ", armPower);

        telemetry.addData("basket position: ", basket.getPosition());

        ///Sensors ----------------------------------------------------------

        String colorOfBlock = colors.blockColor();
        if (colors.getDistance(DistanceUnit.INCH) < 0.5) {
            telemetry.addData("Block Color", colors.blockColor());
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