package org.firstinspires.ftc.teamcode.Mechanisms_Final;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class LongArm {

    private Servo   basket           = null;
    private DcMotor leftArmLift      = null;
    private DcMotor rightArmLift     = null;

    private double  ARM_POWER                   = 1;
    private double  BASKET_RESET_POSITION       = 0.45;
    private double  BASKET_SCORE_POSITION       = 1;
    private double  BASKET_COLLECT_POSITION     = 0.6;
    private int     LEFT_ARM_SCORE_POSITION      = -6050;
    private int     RIGHT_ARM_SCORE_POSITION     = -6050;
    private int     LEFT_ARM_COLLECT_POSITION = -350;
    private int     RIGHT_ARM_COLLECT_POSITION = -350;

    // Public copies of linear slides score position
    public int left_arm_score_position = LEFT_ARM_SCORE_POSITION;
    public int right_arm_score_position = RIGHT_ARM_SCORE_POSITION;

    public int left_arm_collect_position = LEFT_ARM_COLLECT_POSITION;


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

        leftArmLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightArmLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftArmLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightArmLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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

    public void stopLinearSlide() {

        leftArmLift.setPower(0);
        rightArmLift.setPower(0);

        leftArmLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightArmLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }


    public void autoLiftLinearSlide()
    {
        leftArmLift.setTargetPosition(LEFT_ARM_SCORE_POSITION);
        rightArmLift.setTargetPosition(RIGHT_ARM_SCORE_POSITION);

        leftArmLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightArmLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftArmLift.setPower(ARM_POWER);
        rightArmLift.setPower(ARM_POWER);

    }

    public void autoCollectLinearSlide()
    {
        leftArmLift.setTargetPosition(LEFT_ARM_COLLECT_POSITION);
        rightArmLift.setTargetPosition(RIGHT_ARM_COLLECT_POSITION);

        leftArmLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightArmLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftArmLift.setPower(ARM_POWER);
        rightArmLift.setPower(ARM_POWER);

    }

    public int leftLSPosition(){ return leftArmLift.getCurrentPosition();
    }

    public int rightLSPosition(){
        return rightArmLift.getCurrentPosition();
    }



    public boolean isLinearSlideBusy() {

        if (leftArmLift.isBusy() || rightArmLift.isBusy()) {
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
