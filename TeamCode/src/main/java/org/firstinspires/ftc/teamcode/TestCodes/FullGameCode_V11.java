package org.firstinspires.ftc.teamcode.TestCodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Drive_Mechanisms.Drive_v1;
import org.firstinspires.ftc.teamcode.Mechanisms_Final.Climb;
import org.firstinspires.ftc.teamcode.Sensor_Mechanisms.Blinkin_v2;
import org.firstinspires.ftc.teamcode.Sensor_Mechanisms.Color_Sensor_v2;

import java.util.Objects;

@TeleOp(name = "Full Game Code 11", group = "test")
public class FullGameCode_V11 extends OpMode {
    /// Creating objects for the drivetrain, color sensor, REV Blinkin, and runtime.
    Drive_v1 drive = new Drive_v1();
    Color_Sensor_v2 colorB = new Color_Sensor_v2();
    Blinkin_v2 lightB = new Blinkin_v2();
    Climb hook      = new Climb();
    private final ElapsedTime runtime = new ElapsedTime();
    private ElapsedTime hookTimer = new ElapsedTime();

    /// Initializing variables to use across the entire program.
    String speedCap = "Normal";
    Boolean reject;
    Boolean accept;
    String redSpy;
    double clawPower;
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
        hookTimer.reset();

        // Use init method to do the initialization of drive object
        drive.init(hardwareMap);
        colorB.init(hardwareMap);
        lightB.init(hardwareMap);
        hook.init(hardwareMap);

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

    public void samplePick(Boolean accept, Boolean reject) {
        if (accept) {
            clawPower = 1.0;
        } else if (reject) {
            clawPower = -1.0;
        } else {
            clawPower = 0.0;
        }
    }

    @Override
    public void loop()
    {
        /// Alliance data.
        telemetry.addData("Alliance", "Left Trigger for Red, Right Trigger for Blue");
        if (gamepad1.left_trigger > 0) {
            redSpy = "red";
            telemetry.addData("Alliance", "Red");
        }
        if (gamepad1.right_trigger > 0) {
            redSpy = "blue";
            telemetry.addData("Alliance", "Blue");
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

        /// Color Sensor.
        String sample = colorB.sampleColor();

        //Automatic sample rejection system.
        reject = false;
        accept = false;

        if (Objects.equals(redSpy, "red")) {
            if (Objects.equals(sample, "blue")) {
                reject = true;
            }
        }
        if (Objects.equals(redSpy, "blue")) {
            if (Objects.equals(sample, "red")) {
                reject = true;
            }
        }

        //Automatic sample acceptation system.
        if (Objects.equals(redSpy, "red")) {
            if (Objects.equals(sample, "red")) {
                accept = true;
            }
        }
        if (Objects.equals(redSpy, "blue")) {
            if (Objects.equals(sample, "blue")) {
                accept = true;
            }
        }
        if (Objects.equals(sample, "yellow")) {
            accept = true;
        }

        //Telemetry
        telemetry.addData("Sample Color", colorB.sampleColor());
        telemetry.addData("Reject?", reject);
        telemetry.addData("Accept?", accept);

        /// Intake mechanisms.
        double intakeArmPower = gamepad2.left_stick_y * 0.5;
        intakeArm.setPower(intakeArmPower);
        int intakeArmTargetDown = 50;
        int intakeArmTargetUp = 1300;
        double intakeSpeed = 0.5;

        if (intakeArmPower < 0) {
            intakeArm.setTargetPosition(intakeArmTargetDown);
            intakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        } else if (intakeArmPower > 0) {
            intakeArm.setTargetPosition(intakeArmTargetUp);
            intakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        } else {
            intakeArm.setTargetPosition(intakeArm.getCurrentPosition() + 1);
            intakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        intakeArm.setPower(intakeArmPower);

        if (gamepad2.right_stick_x > 0) {
            clawPower = 1.0;
        } else if ((gamepad2.right_stick_x < 0)) {
            clawPower = -1.0;
        } else {
            samplePick(accept, reject);
        }

        claw.setPower(clawPower);

        /// Linear slide.
        double armPower;

        //Controls
        if (gamepad2.left_trigger > 0) {
            armPower = -gamepad2.left_trigger;
        } else if (gamepad2.right_trigger > 0) {
            armPower = gamepad2.right_trigger;
        } else {
            armPower = 0;
        }

        int slideArmTargetDown = 0;
        int slideArmTargetUp = -6050;

        if (armPower > 0) {
            rightArmLift.setTargetPosition(slideArmTargetDown);
            leftArmLift.setTargetPosition(slideArmTargetDown);
            rightArmLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftArmLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        } else if (armPower < 0) {
            rightArmLift.setTargetPosition(slideArmTargetUp);
            leftArmLift.setTargetPosition(slideArmTargetUp);
            rightArmLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftArmLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        } else {
            rightArmLift.setTargetPosition(rightArmLift.getCurrentPosition() + 1);
            leftArmLift.setTargetPosition(leftArmLift.getCurrentPosition() + 1);
            rightArmLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftArmLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        rightArmLift.setPower(armPower);
        leftArmLift.setPower(armPower);

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
        if (armPower > 0 || armPower < 0) {
            basket.setPosition(0.45);
        } else if (intakeArmPower > 0 && gamepad2.left_trigger == 0 && gamepad2.right_trigger == 0) {
            basket.setPosition(0.55);
        }

        // Hook

        boolean hookResetButton = gamepad2.y;
        boolean hookGrabRungButton = gamepad2.a;

        /// Hook Mechanism ---------------------------------------------------------------------

        if (hookGrabRungButton) {
            hookTimer.reset();
            while (hookTimer.seconds() < 2.5) { hook.grabRung(); }
        } else hook.stop();

        if (hookResetButton) {
            hookTimer.reset();
            while (hookTimer.seconds() < 2.5) { hook.reset(); }
        } else hook.stop();

        /// Rev BLINKIN
        lightB.light(redSpy);
    }
}