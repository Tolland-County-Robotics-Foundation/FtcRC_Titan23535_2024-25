package org.firstinspires.ftc.teamcode.LongArm_Mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class LongArm_v2 {

    private Servo   basket           = null;
    private DcMotor leftArmLift      = null;
    private DcMotor rightArmLift     = null;

    private double ARM_POWER             = 0.7;
    private double BASKET_RESET_POSITION = 0.4;
    private double BASKET_SCORE_POSITION = 0.9;

    public void init(HardwareMap hwMap)
    {
        basket          = hwMap.get(Servo.class, "BasketArm");
        leftArmLift     = hwMap.get(DcMotor.class, "LeftArmLift");
        rightArmLift    = hwMap.get(DcMotor.class, "RightArmLift");

        basket.setDirection(Servo.Direction.FORWARD);
        leftArmLift.setDirection(DcMotorSimple.Direction.FORWARD);
        rightArmLift.setDirection(DcMotorSimple.Direction.REVERSE);

        leftArmLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightArmLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }


    /// Long Arm ---------------------------------------------------------------------------------

    public void liftArm()
    {
        leftArmLift.setPower(ARM_POWER);
        rightArmLift.setPower(ARM_POWER);
    }

    public void resetArm()
    {
        leftArmLift.setPower(-ARM_POWER);
        rightArmLift.setPower(-ARM_POWER);
    }

    public void stopArm()
    {
        leftArmLift.setPower(0);
        rightArmLift.setPower(0);
    }


    /// Basket -----------------------------------------------------------------------------------

    public void basketReset()
    {
        basket.setPosition(BASKET_RESET_POSITION);
    }

    public void scoreGamePiece()
    {
        basket.setPosition(BASKET_SCORE_POSITION);
    }
}
