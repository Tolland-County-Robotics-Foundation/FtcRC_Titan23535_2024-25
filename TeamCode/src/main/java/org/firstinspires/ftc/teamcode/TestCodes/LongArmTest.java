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

        telemetry.addData("basket position: ", basket.getPosition());

    }
    @Override
    public void loop()
    {
        /// Long arm ---------------------------------------------------------

        double armPower = 0.0;


        if (gamepad2.dpad_down)
        {
            armPower = -0.7;
        }
        else if (gamepad2.dpad_up)
        {
            armPower = 0.7;

        }

        leftArmLift.setPower(armPower);
        rightArmLift.setPower(armPower);

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


        telemetry.addData("intake arm power: ", armPower);

        telemetry.addData("basket position: ", basket.getPosition());


    }
}