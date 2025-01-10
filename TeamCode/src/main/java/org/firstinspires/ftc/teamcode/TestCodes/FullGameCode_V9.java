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
    /// Creating objects for the drivetrain, color sensor, REV Blinkin, and runtime.
    Drive_v1 drive = new Drive_v1();
    Color_Sensor_v1 color = new Color_Sensor_v1();
    Blinkin_v1 light = new Blinkin_v1();
    private final ElapsedTime runtime = new ElapsedTime();

    /// Initializing variables to use across the entire program.
    String speedCap = "Normal";
    Boolean start = true;
    Boolean rightColor;
    String redSpy = "none";
    double speed_percentage = 40.0;

    ///Creating objects for the intake mechanisms.
    private CRServo claw        = null;
    private DcMotor intakeArm   = null;

    /// Creating objects for the linear slide and basket.
    private Servo basket          = null;
    private DcMotor leftArmLift     = null;
    private DcMotor rightArmLift    = null;


    @Override
    public void init()
    {
        // Resetting runtime
        runtime.reset();

        // Use init method to do the initialization of drive object
        drive.init(hardwareMap);

        /// Initialization of intake mechanisms.
        claw        = hardwareMap.get(CRServo.class, "claw");
        claw.setDirection(DcMotorSimple.Direction.FORWARD);

        intakeArm   = hardwareMap.get(DcMotor.class, "intakeArm");
        intakeArm.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        /// Initialization of linear slide and basket.
        basket          = hardwareMap.get(Servo.class, "BasketArm");
        basket.setDirection(Servo.Direction.FORWARD);

        leftArmLift     = hardwareMap.get(DcMotor.class, "LeftArmLift");
        leftArmLift.setDirection(DcMotorSimple.Direction.FORWARD);
        leftArmLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rightArmLift    = hardwareMap.get(DcMotor.class, "RightArmLift");
        rightArmLift.setDirection(DcMotorSimple.Direction.REVERSE);
        rightArmLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    @Override
    public void loop()
    {
        /// Alliance data.
        //Sets the telemetry reply for alliance color.
        String allianceText;
        if (Objects.equals(redSpy, "red")) {
            allianceText = "Red";
        } else if (Objects.equals(redSpy, "blue")) {
            allianceText = "Blue";
        } else {
            allianceText = "Left Trigger for Red, Right Trigger for Blue";
        }

        telemetry.addData("Alliance Selected", allianceText);

        //Chooses the alliance.
        if (start) {
            if (gamepad1.left_trigger > 0.1) {
                redSpy = "red";
                start = false;
            }
            if (gamepad1.right_trigger > 0.1) {
                redSpy = "blue";
                start = false;
            }
        }

        /// Sets the speed cap for driver 1.
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

        /// Drivetrain controls.
        double axial   = -gamepad1.left_stick_y;  // Note: pushing stick forward gives a negative value.
        double lateral =  gamepad1.left_stick_x;
        double yaw     =  gamepad1.right_stick_x;

        drive.setDriveMotorPower(axial, lateral, yaw, speed_percentage);

        ///Miscellaneous telemetries.
        //Displaying runtime.
        telemetry.addData("Status", "Run Time: " + runtime.seconds());
        //Displaying current speed.
        telemetry.addData("Current Speed", speedCap);

        /// Intake mechanisms.
        double intakeArmPower = gamepad2.left_stick_y * 0.5;

        intakeArm.setPower(intakeArmPower);

        if (gamepad2.right_stick_x > 0) {
            claw.setPower(1);
        } else if ((gamepad2.right_stick_x < 0) || (!rightColor)) {
            claw.setPower(-1);
        } else {
            claw.setPower(0.0);
        }

        /// Linear slide.
        double armPower;

        if (gamepad2.left_trigger > 0) {
            armPower = -gamepad2.left_trigger;
        } else if (gamepad2.right_trigger > 0) {
            armPower = gamepad2.right_trigger;
        } else {
            armPower = 0.065;
        }

        leftArmLift.setPower(armPower);
        rightArmLift.setPower(armPower);

        /// Basket.
        //Manual controls.
        if (gamepad2.dpad_down) {
            basket.setPosition(1); // Drop
        } else if (gamepad2.dpad_up) {
            basket.setPosition(0.55); // Collect
        } else if (gamepad2.dpad_right) {
            basket.setPosition(0.45); // Move
        }

        //Automatic controls.
        if (armPower > 0.065 || armPower < 0.065) {
            basket.setPosition(0.45);
        } else if (intakeArmPower < 0 && gamepad2.left_trigger == 0 && gamepad2.right_trigger == 0) {
            basket.setPosition(0.55);
        }

        /// Color Sensor.
        String colorOfBlock = color.blockColor();

        //Telemetry
        if (color.getDistance(DistanceUnit.INCH) < 0.5) {
            telemetry.addData("Sample Color", color.blockColor());
        }

        //Automatic sample rejection system.
        if (Objects.equals(redSpy, "red")) {
            if (Objects.equals(colorOfBlock, "blue")) {
                rightColor = false;
            }
        } else if (Objects.equals(redSpy, "blue")) {
            if (Objects.equals(colorOfBlock, "red")) {
                rightColor = false;
            }
        } else {
            rightColor = true;
        }

        ///REV Blinkin.
        light.sample(redSpy);
    }
}