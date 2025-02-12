package org.firstinspires.ftc.teamcode.Mechanisms_Final;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class LongArm {

    private Servo   basket           = null;
    private DcMotorEx leftArmLift      = null;
    private DcMotorEx rightArmLift     = null;

    private double  ARM_POWER                   = 0.7;
    private double  BASKET_RESET_POSITION       = 0.45;
    private double  BASKET_SCORE_POSITION       = 1;
    private double  BASKET_COLLECT_POSITION     = 0.6;
    private int     LEFT_ARM_SCORE_POSITION      = -6050;
    private int     RIGHT_ARM_SCORE_POSITION     = -6050;
    private int     LEFT_ARM_RESET_POSITION     = 0;
    private int     RIGHT_ARM_RESET_POSITION    = 0;


    public void init(HardwareMap hwMap)
    {
        basket          = hwMap.get(Servo.class, "BasketArm");
        leftArmLift     = hwMap.get(DcMotorEx.class, "LeftArmLift");
        rightArmLift    = hwMap.get(DcMotorEx.class, "RightArmLift");

        basket.setDirection(Servo.Direction.FORWARD);
        leftArmLift.setDirection(DcMotorEx.Direction.FORWARD);
        rightArmLift.setDirection(DcMotorEx.Direction.REVERSE);

        leftArmLift.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightArmLift.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        leftArmLift.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        rightArmLift.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        leftArmLift.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        rightArmLift.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
    }


    /// Long Arm ---------------------------------------------------------------------------------

    public void moveLinearSlide(double power){
        leftArmLift.setPower(power);
        rightArmLift.setPower(power);
    }

    public void liftLinearSlide()
    {
        leftArmLift.setPower(ARM_POWER);
        rightArmLift.setPower(ARM_POWER);
    }

    public void resetLinearSlide()
    {
        leftArmLift.setPower(-ARM_POWER);
        rightArmLift.setPower(-ARM_POWER);
    }

    public void stopLinearSlide()
    {
        leftArmLift.setPower(0);
        rightArmLift.setPower(0);
    }

    public void autoLiftLinearSlide()
    {
        leftArmLift.setTargetPosition(LEFT_ARM_SCORE_POSITION);
        rightArmLift.setTargetPosition(RIGHT_ARM_SCORE_POSITION);

        leftArmLift.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        rightArmLift.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

        leftArmLift.setPower(ARM_POWER);
        rightArmLift.setPower(ARM_POWER);

    }

    public void autoResetLinearSlide()
    {
        leftArmLift.setTargetPosition(LEFT_ARM_RESET_POSITION);
        rightArmLift.setTargetPosition(RIGHT_ARM_RESET_POSITION);

        leftArmLift.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        rightArmLift.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

        leftArmLift.setPower(ARM_POWER);
        rightArmLift.setPower(ARM_POWER);


    }

    public boolean isLinearSlideBusy() {
        boolean busy = true;
        if (!leftArmLift.isBusy() && !rightArmLift.isBusy()) {
            busy = false;
        }
        return busy;
    }


    /// Basket -----------------------------------------------------------------------------------

    public void basketReset()
    {
        basket.setPosition(BASKET_RESET_POSITION);
    }

    public void basketScoreSample()
    {
        basket.setPosition(BASKET_SCORE_POSITION);
    }

    public void basketCollectSample(){basket.setPosition(BASKET_COLLECT_POSITION);}
}
