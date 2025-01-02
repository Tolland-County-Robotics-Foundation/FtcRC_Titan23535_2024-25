package org.firstinspires.ftc.teamcode.TestCodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class LongArmAutoTest extends OpMode {

    private Servo   basket          = null;
    private DcMotor leftArmLift     = null;
    private DcMotor rightArmLift    = null;

    private double  ARM_POWER                   = 0.7;
    private double  BASKET_RESET_POSITION       = 0.4;
    private double  BASKET_SCORE_POSITION       = 0.9;
    private int     LEFT_ARM_LIFT_POSITION      = 3110; // High basket
    private int     RIGHT_ARM_LIFT_POSITION     = 3117; // High Basket
    private int     LEFT_ARM_RESET_POSITION     = 580;
    private int     RIGHT_ARM_RESET_POSITION    = 600;

    @Override
    public void init()
    {
        basket          = hardwareMap.get(Servo.class, "BasketArm");
        leftArmLift     = hardwareMap.get(DcMotor.class, "LeftArmLift");
        rightArmLift    = hardwareMap.get(DcMotor.class, "RightArmLift");

        basket.setDirection(Servo.Direction.FORWARD);
        leftArmLift.setDirection(DcMotorSimple.Direction.FORWARD);
        rightArmLift.setDirection(DcMotorSimple.Direction.REVERSE);

        leftArmLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightArmLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftArmLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightArmLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftArmLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightArmLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        telemetry.addData("Left arm power: ", leftArmLift.getPower());

        telemetry.addData("Right arm power: ", rightArmLift.getPower());

        telemetry.addData("basket position: ", basket.getPosition());

        telemetry.addData("Left Arm Position: ", leftArmLift.getCurrentPosition());

        telemetry.addData("Right arm position: ", rightArmLift.getCurrentPosition());

    }
    @Override
    public void loop() {
        /// Long arm ---------------------------------------------------------

        if (gamepad2.dpad_down)
        {
            leftArmLift.setPower(-0.7);
            rightArmLift.setPower(-0.7);
        }
        else if (gamepad2.dpad_up)
        {
            leftArmLift.setPower(0.7);
            rightArmLift.setPower(0.7);

        } else if (gamepad2.left_bumper)
        {
            autoLiftArm();

        } else if (gamepad2.right_bumper)
        {
            autoResetArm();
        }


        /// Basket -----------------------------------------------------------

        if (gamepad2.x)
        {
            basket.setPosition(0.9);
        }
        else if (gamepad2.b)
        {
            basket.setPosition(0.6);
        }
        else if (gamepad2.a)
        {
            basket.setPosition(0.4);
        }
        else if (gamepad2.y)
        {
            basket.setPosition(0.2);
        }


        telemetry.addData("Left arm power: ", leftArmLift.getPower());

        telemetry.addData("Right arm power: ", rightArmLift.getPower());

        telemetry.addData("basket position: ", basket.getPosition());

        telemetry.addData("Left Arm Position: ", leftArmLift.getCurrentPosition());

        telemetry.addData("Right arm position: ", rightArmLift.getCurrentPosition());


    }

    public void autoLiftArm()
    {
        leftArmLift.setTargetPosition(LEFT_ARM_LIFT_POSITION);
        rightArmLift.setTargetPosition(RIGHT_ARM_LIFT_POSITION);

        leftArmLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightArmLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftArmLift.setPower(ARM_POWER);
        rightArmLift.setPower(ARM_POWER);

        /*

        leftArmLift.setPower(0.0);
        rightArmLift.setPower(0.0);

        leftArmLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightArmLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

         */

    }

    public void autoResetArm()
    {
        leftArmLift.setTargetPosition(LEFT_ARM_RESET_POSITION);
        rightArmLift.setTargetPosition(RIGHT_ARM_RESET_POSITION);

        leftArmLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightArmLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftArmLift.setPower(ARM_POWER);
        rightArmLift.setPower(ARM_POWER);

        /*

        leftArmLift.setPower(0.0);
        rightArmLift.setPower(0.0);

        leftArmLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightArmLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

         */

    }
}
