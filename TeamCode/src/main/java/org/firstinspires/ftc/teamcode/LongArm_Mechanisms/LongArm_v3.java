package org.firstinspires.ftc.teamcode.LongArm_Mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class LongArm_v3 {

    private Servo   basket           = null;
    private DcMotor leftArmLift      = null;
    private DcMotor rightArmLift     = null;

    private double  ARM_POWER                   = 0.7;
    private double  BASKET_RESET_POSITION       = 0.4;
    private double  BASKET_SCORE_POSITION       = 0.9;
    private int     LEFT_ARM_LIFT_POSITION      = 0;
    private int     RIGHT_ARM_LIFT_POSITION     = 0;
    private int     LEFT_ARM_RESET_POSITION     = 0;
    private int     RIGHT_ARM_RESET_POSITION    = 0;

    /*
    private int    LIFT_DISTANCE         = 10;

     */

    /*

    static final double     COUNTS_PER_MOTOR_REV    = 28;    // Gobilda 5203
    static final double     DRIVE_GEAR_REDUCTION    =  1.0;     // No gear
    static final double     WHEEL_DIAMETER_INCHES   =  1.5;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);


     */

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
        rightArmLift.setPower(-power);
    }

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
