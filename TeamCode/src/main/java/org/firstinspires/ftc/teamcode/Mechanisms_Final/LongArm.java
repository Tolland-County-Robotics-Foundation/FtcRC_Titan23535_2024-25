package org.firstinspires.ftc.teamcode.Mechanisms_Final;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class LongArm {

    private Servo   basket           = null;
    private DcMotor leftLinearSlide = null;
    private DcMotor rightLinearSlide = null;

    private double  ARM_POWER                   = 1;
    private double  BASKET_RESET_POSITION       = 0.45;
    private double  BASKET_SCORE_POSITION       = 1;
    private double  BASKET_COLLECT_POSITION     = 0.6;
    private int     LEFT_ARM_SCORE_POSITION      = -4050;
    private int     RIGHT_ARM_SCORE_POSITION     = -4050;
    private int     LEFT_ARM_COLLECT_POSITION = -350;
    private int     RIGHT_ARM_COLLECT_POSITION = -350;

    // Public copies of linear slides score position
    public int left_arm_score_position = LEFT_ARM_SCORE_POSITION;
    public int right_arm_score_position = RIGHT_ARM_SCORE_POSITION;

    public int left_arm_collect_position = LEFT_ARM_COLLECT_POSITION;


    public void init(HardwareMap hwMap)
    {
        basket          = hwMap.get(Servo.class, "BasketArm");
        leftLinearSlide = hwMap.get(DcMotor.class, "LeftArmLift");
        rightLinearSlide = hwMap.get(DcMotor.class, "RightArmLift");

        basket.setDirection(Servo.Direction.FORWARD);
        leftLinearSlide.setDirection(DcMotorSimple.Direction.FORWARD);
        rightLinearSlide.setDirection(DcMotorSimple.Direction.REVERSE);

        leftLinearSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightLinearSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftLinearSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightLinearSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftLinearSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightLinearSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }


    /// Long Arm ---------------------------------------------------------------------------------

    public void moveLinearSlide(double power){
        leftLinearSlide.setPower(power);
        rightLinearSlide.setPower(power);
    }

    public void liftLinearSlide()
    {
        leftLinearSlide.setPower(ARM_POWER);
        rightLinearSlide.setPower(ARM_POWER);
    }

    public void resetLinearSlide()
    {
        leftLinearSlide.setPower(-ARM_POWER);
        rightLinearSlide.setPower(-ARM_POWER);
    }

    public void stopLinearSlide() {

        leftLinearSlide.setPower(0);
        rightLinearSlide.setPower(0);

        leftLinearSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightLinearSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }


    public void autoLiftLinearSlide()
    {
        leftLinearSlide.setTargetPosition(LEFT_ARM_SCORE_POSITION);
        rightLinearSlide.setTargetPosition(RIGHT_ARM_SCORE_POSITION);

        leftLinearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightLinearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftLinearSlide.setPower(ARM_POWER);
        rightLinearSlide.setPower(ARM_POWER);

    }

    public void autoCollectLinearSlide()
    {
        leftLinearSlide.setTargetPosition(LEFT_ARM_COLLECT_POSITION);
        rightLinearSlide.setTargetPosition(RIGHT_ARM_COLLECT_POSITION);

        leftLinearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightLinearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftLinearSlide.setPower(ARM_POWER);
        rightLinearSlide.setPower(ARM_POWER);

    }

    public int leftLSPosition(){
        return leftLinearSlide.getCurrentPosition();
    }

    public int rightLSPosition(){
        return rightLinearSlide.getCurrentPosition();
    }



    public boolean isLinearSlideBusy() {

        if (leftLinearSlide.isBusy() || rightLinearSlide.isBusy()) {
            return true;
        }
        return false;
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
