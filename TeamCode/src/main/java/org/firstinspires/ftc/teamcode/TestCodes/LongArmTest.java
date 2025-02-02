package org.firstinspires.ftc.teamcode.TestCodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class LongArmTest extends OpMode {

    private Servo   basket          = null;
    private DcMotor leftArmLift     = null;
    private DcMotor rightArmLift    = null;

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

        telemetry.addData("basket position: ", basket.getPosition());

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

}