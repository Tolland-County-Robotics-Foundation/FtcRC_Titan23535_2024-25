package org.firstinspires.ftc.teamcode.TestCodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class TestMotors extends OpMode {

    // Creating objects from respective motor classes

    // Drivetrain: 4 Dc Motors

    private DcMotor leftFrontDrive  = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor leftBackDrive   = null;
    private DcMotor rightBackDrive  = null;

    // Linear Slide: 2 Dc Motors & 2 Servo Motors

    private DcMotor leftArm  = null;
    private DcMotor rightArm = null;
    private Servo leftS      = null;
    private Servo rightS     = null;

    // Intake: 2 Dc Motors:

    private DcMotor intake      = null;
    private DcMotor intakeWheel = null;

    // Create a variable to store the position of a servo motor

    double servoPosition = 0.0;



    @Override
    public void init()
    {
        leftFrontDrive  = hardwareMap.get(DcMotor.class,"leftFront");
        rightFrontDrive = hardwareMap.get(DcMotor.class,"rightFront");
        leftBackDrive   = hardwareMap.get(DcMotor.class,"leftBack");
        rightBackDrive  = hardwareMap.get(DcMotor.class,"rightBack");


        leftArm  = hardwareMap.get(DcMotor.class,"leftArm");
        rightArm = hardwareMap.get(DcMotor.class,"rightArm");
        leftS    = hardwareMap.get(Servo.class,"leftS");
        rightS   = hardwareMap.get(Servo.class,"rightS");

        intake      = hardwareMap.get(DcMotor.class,"intake");
        intakeWheel = hardwareMap.get(DcMotor.class,"intakeWheel");


        leftArm.setDirection(DcMotorSimple.Direction.FORWARD);
        rightArm.setDirection(DcMotorSimple.Direction.REVERSE);

        leftS.setDirection(Servo.Direction.FORWARD);
        rightS.setDirection(Servo.Direction.REVERSE);


    }
    @Override
    public void loop()
    {
        double leftFrontPower   = 0.0;
        double rightFrontPower  = 0.0;
        double leftBackPower    = 0.0;
        double rightBackPower   = 0.0;
        double leftArmPower     = 0.0;
        double rightArmPower    = 0.0;
        double intakePower      = 0.0;
        double intakeWheelPower = 0.0;


        // Testing if all 4 drivetrain motors drive at a same speed for same power

        if (gamepad1.left_bumper)
        {
            leftFrontPower  = leftFrontPower + 0.1;
            rightFrontPower = rightFrontPower + 0.1;
            leftBackPower   = leftBackPower + 0.1;
            rightBackPower  = rightBackPower + 0.1;
        }

        // Test Motor Connections

        // Drivetrain

        if (gamepad1.x)
        {
            leftFrontPower = 0.5;
        }

        if (gamepad1.a)
        {
            leftBackPower = 0.5;
        }

        if (gamepad1.y)
        {
            rightFrontPower = 0.5;
        }

        if (gamepad1.b)
        {
            rightBackPower = 0.5;
        }

        // Arm

        if (gamepad2.a)
        {
            leftArmPower = 0.75;
            rightArmPower = 0.75;
        }

        if (gamepad2.left_bumper)
        {
            if (servoPosition > 1.0)
            {
                servoPosition = servoPosition - 0.2;
            }

            else
            {
                servoPosition = servoPosition + 0.2;
            }
        }



        if (gamepad2.b)
        {
            rightArmPower = 0.3;
        }
        else
        {
            rightArmPower = 0.0;
        }



        if (gamepad2.x)
        {
            leftS.setPosition(0.8);
        }
        else
        {
            leftS.setPosition(0.2);
        }

        if (gamepad2.y)
        {
            rightS.setPosition(0.8);
        }
        else
        {
            rightS.setPosition(0.2);
        }

        if (gamepad2.left_bumper)
        {
            intakePower = 1;
        }

        //intake

        intakePower = gamepad2.left_trigger - gamepad2.right_trigger;

        if (gamepad2.right_bumper)
        {
            intakeWheelPower = 0.5;
        }

        // Setting the power of the motors

        leftFrontDrive.setPower(leftFrontPower);
        rightFrontDrive.setPower(rightFrontPower);
        leftBackDrive.setPower(leftBackPower);
        rightBackDrive.setPower(rightBackPower);

        leftArm.setPower(leftArmPower);
        rightArm.setPower(rightArmPower);
        leftS.setPosition(servoPosition);
        rightS.setPosition(servoPosition);

        intake.setPower(intakePower);
        intakeWheel.setPower(intakeWheelPower);

        // Print the information on the driver hub screen

        telemetry.addData("Front left/Right", "%4.2f, %4.2f", leftFrontPower, rightFrontPower);
        telemetry.addData("Back  left/Right", "%4.2f, %4.2f", leftBackPower, rightBackPower);

        telemetry.addData("Arm left/Right", "%4.2f, %4.2f", leftArmPower, rightArmPower);

        telemetry.addData("intake  intake/intakeWheel", "%4.2f, %4.2f", intakePower, intakeWheelPower);


    }
}