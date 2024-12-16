package org.firstinspires.ftc.teamcode.TestCodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class LongArmTest extends OpMode {

    private Servo   basket     = null;
    private DcMotor longArm    = null;

    @Override
    public void init()
    {
        basket   = hardwareMap.get(Servo.class, "claw");
        longArm  = hardwareMap.get(DcMotor.class, "intakeArm");

        basket.setDirection(Servo.Direction.FORWARD);
        longArm.setDirection(DcMotorSimple.Direction.FORWARD);

        longArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }
    @Override
    public void loop()
    {
        double armPower = gamepad2.left_trigger - gamepad2.right_trigger;
        longArm.setPower(armPower);

        if (gamepad2.left_bumper)
        {
            basket.setPosition(0.3);
        }
        else if (gamepad2.right_bumper)
        {
            basket.setPosition(-0.3);
        }


        telemetry.addData("intake arm power: ", armPower);

        telemetry.addData("basket position: ", basket.getPosition());


    }
}