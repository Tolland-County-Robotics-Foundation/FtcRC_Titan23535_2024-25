package org.firstinspires.ftc.teamcode.TestCodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import static android.os.SystemClock.sleep;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp
public class TestAll extends OpMode {


    private DcMotor leftFrontDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightBackDrive = null;
    private DcMotor intake = null;
    private DcMotor IntakeLift = null; // Intake
    double cap = 0.5;

    //Linear Slide
    DcMotor leftArm;
    DcMotor rightArm;
    Servo leftS;
    Servo rightS;


    @Override
    public void init() {
        leftFrontDrive = hardwareMap.get(DcMotor.class, "leftFront");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rightFront");
        leftBackDrive = hardwareMap.get(DcMotor.class, "leftBack");
        rightBackDrive = hardwareMap.get(DcMotor.class, "rightBack");
        leftFrontDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFrontDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotorSimple.Direction.REVERSE);

        //Linear Slide
        leftArm = hardwareMap.get(DcMotor.class, "leftArm");
        leftArm.setDirection(DcMotorSimple.Direction.REVERSE);
        rightArm = hardwareMap.get(DcMotor.class, "rightArm");
        rightArm.setDirection(DcMotorSimple.Direction.FORWARD);
        leftS = hardwareMap.get(Servo.class, "leftS");
        leftS.setDirection(Servo.Direction.FORWARD);
        rightS = hardwareMap.get(Servo.class, "rightS");
        rightS.setDirection(Servo.Direction.REVERSE);

        //Intake
        IntakeLift = hardwareMap.get(DcMotor.class, "intakeWheel");
        IntakeLift.setDirection(DcMotorSimple.Direction.FORWARD);
        intake = hardwareMap.get(DcMotor.class, "intake");
        intake.setDirection(DcMotorSimple.Direction.FORWARD);

    }

    @Override
    public void loop() {
// Drivetrain

        double leftFrontPower = 0.0;
        double leftBackPower = 0.0;
        double rightFrontPower = 0.0;
        double rightBackPower = 0.0;

        double forward = -gamepad1.left_stick_y;
        double right = gamepad1.left_stick_x;
        double rotate = gamepad1.right_stick_x;

        leftFrontPower = forward + right + rotate;
        leftBackPower = forward - right + rotate;
        rightFrontPower = forward - right - rotate;
        rightBackPower = forward + right - rotate;

        double maxPower = 0.0;
        maxPower = Math.max(Math.abs(leftFrontPower), Math.abs(leftBackPower));
        maxPower = Math.max(maxPower, Math.abs(rightFrontPower));
        maxPower = Math.max(maxPower, Math.abs(rightBackPower));

        telemetry.addData("Max", maxPower);

        if (maxPower > 1) {
            leftFrontPower = leftFrontPower / maxPower;
            leftBackPower = leftBackPower / maxPower;
            rightFrontPower = rightFrontPower / maxPower;
            rightBackPower = rightBackPower / maxPower;
        }

        // Speed Cap
        if (gamepad1.a) {
            cap = 0.5;
        }
        if (gamepad1.b) {
            cap = 0.45;
        }
        if (gamepad1.y) {
            cap = 0.35;
        }
        if (gamepad1.x) {
            cap = 0.25;
        }

        leftFrontPower = leftFrontPower * cap;
        leftBackPower = leftBackPower * cap;
        rightFrontPower = rightFrontPower * cap;
        rightBackPower = rightBackPower * cap;

        leftFrontDrive.setPower(leftFrontPower);
        rightFrontDrive.setPower(rightFrontPower);
        leftBackDrive.setPower(leftBackPower);
        rightBackDrive.setPower(rightBackPower);

        // Intake

        float leftTrig = gamepad2.left_trigger;
        float rightTrig = gamepad2.right_trigger;
        if (leftTrig > 0 && rightTrig <= 0) {
            IntakeLift.setPower(leftTrig);
        } else if (rightTrig > 0 && leftTrig <= 0) {
            IntakeLift.setPower(rightTrig);
        } else {
            IntakeLift.setPower(0);
        }

        if (gamepad2.left_bumper) { // Out
            intake.setPower(-0.7);
        } else if (gamepad2.right_bumper) { // In
            intake.setPower(0.7);
        } else {
            intake.setPower(0);
        }

        //Linear Slide
        double liftPower = gamepad2.left_stick_y;
        double turn = gamepad2.right_stick_x;
        double turnPower = 0.0;

        if (turn > 0) {
            turnPower = turnPower + 0.2;
        }
        if (turn < 0) {
            turnPower = turnPower - 0.2;
        }

        leftArm.setPower(liftPower);
        rightArm.setPower(liftPower);

        leftS.setPosition(turnPower);
        rightS.setPosition(turnPower);
    }
}





