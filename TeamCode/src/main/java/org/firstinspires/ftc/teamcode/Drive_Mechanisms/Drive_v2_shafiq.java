package org.firstinspires.ftc.teamcode.Drive_Mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Drive_v2_shafiq {


    private DcMotor leftFrontDrive  = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor leftBackDrive   = null;
    private DcMotor rightBackDrive  = null;

    private double MOTOR_POWER      = 0.5;

    static final double     COUNTS_PER_MOTOR_REV    = 28;    //
    static final double     DRIVE_GEAR_REDUCTION    =  12.0;     // 4:1 External Gearing.
    static final double     WHEEL_DIAMETER_INCHES   =  2.95276;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.25;

    public enum Mode {
        FORWARD, BACKWARD, LEFT, RIGHT, TURNLEFT, TURNRIGHT, STOP
    }


    public void init(HardwareMap hardwareMap)
    {

        //Hardware mapping
        leftFrontDrive  = hardwareMap.get(DcMotor.class,"leftFront");
        rightFrontDrive = hardwareMap.get(DcMotor.class,"rightFront");
        leftBackDrive   = hardwareMap.get(DcMotor.class,"leftBack");
        rightBackDrive  = hardwareMap.get(DcMotor.class,"rightBack");

        // Setting the direction
        leftFrontDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFrontDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotorSimple.Direction.REVERSE);

        leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


    }

    public void stop() {
        leftFrontDrive.setPower(0.0);
        rightFrontDrive.setPower(0.0);
        leftBackDrive.setPower(0.0);
        rightBackDrive.setPower(0.0);
    }

    public void setDriveMotorPower(double forward, double right, double rotate, double cap) {

        // Drivetrain

        double leftFrontPower   = 0.0;
        double leftBackPower    = 0.0;
        double rightFrontPower  = 0.0;
        double rightBackPower   = 0.0;

        leftFrontPower  = forward + right + rotate;
        leftBackPower   = forward - right + rotate;
        rightFrontPower = forward - right - rotate;
        rightBackPower  = forward + right - rotate;

        double maxPower = 1.0;

        maxPower = Math.max(Math.abs(leftFrontPower), Math.abs(leftBackPower));
        maxPower = Math.max(maxPower, Math.abs(rightFrontPower));
        maxPower = Math.max(maxPower, Math.abs(rightBackPower));


        if (maxPower > 1) {
            leftFrontPower  = leftFrontPower / maxPower;
            leftBackPower   = leftBackPower / maxPower;
            rightFrontPower = rightFrontPower / maxPower;
            rightBackPower  = rightBackPower / maxPower;
        }


        leftFrontPower  = leftFrontPower * cap / 100.0;
        leftBackPower   = leftBackPower * cap / 100.0;
        rightFrontPower = rightFrontPower * cap / 100.0;
        rightBackPower  = rightBackPower * cap / 100.0;

        leftFrontDrive.setPower(leftFrontPower);
        rightFrontDrive.setPower(rightFrontPower);
        leftBackDrive.setPower(leftBackPower);
        rightBackDrive.setPower(rightBackPower);


    }

    public void teleOpForward(){

        leftFrontDrive.setPower(MOTOR_POWER);
        rightFrontDrive.setPower(MOTOR_POWER);
        leftBackDrive.setPower(MOTOR_POWER);
        rightBackDrive.setPower(MOTOR_POWER);

    }

    public void autoDrive(Mode driveMode, double distance, double drive_speed){

        int newLeftFrontTarget=0;
        int newLeftBackTarget=0;
        int newRightFrontTarget=0;
        int newRightBackTarget=0;

        if (driveMode == Mode.FORWARD){
            newLeftFrontTarget = leftFrontDrive.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);
            newLeftBackTarget = leftBackDrive.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);
            newRightFrontTarget = rightFrontDrive.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);
            newRightBackTarget = rightBackDrive.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);
        } else if (driveMode == Mode.BACKWARD) {
            newLeftFrontTarget = leftFrontDrive.getCurrentPosition() + (int)(-distance * COUNTS_PER_INCH);
            newLeftBackTarget = leftBackDrive.getCurrentPosition() + (int)(-distance * COUNTS_PER_INCH);
            newRightFrontTarget = rightFrontDrive.getCurrentPosition() + (int)(-distance * COUNTS_PER_INCH);
            newRightBackTarget = rightBackDrive.getCurrentPosition() + (int)(-distance * COUNTS_PER_INCH);
        } else if (driveMode == Mode.RIGHT) {
            newLeftFrontTarget = leftFrontDrive.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);
            newLeftBackTarget = leftBackDrive.getCurrentPosition() + (int)(-distance * COUNTS_PER_INCH);
            newRightFrontTarget = rightFrontDrive.getCurrentPosition() + (int)(-distance * COUNTS_PER_INCH);
            newRightBackTarget = rightBackDrive.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);

        } else if (driveMode == Mode.LEFT) {
            newLeftFrontTarget = leftFrontDrive.getCurrentPosition() + (int)(-distance * COUNTS_PER_INCH);
            newLeftBackTarget = leftBackDrive.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);
            newRightFrontTarget = rightFrontDrive.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);
            newRightBackTarget = rightBackDrive.getCurrentPosition() + (int)(-distance * COUNTS_PER_INCH);

        }else if (driveMode == Mode.TURNLEFT) {
            newLeftFrontTarget = leftFrontDrive.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);
            newLeftBackTarget = leftBackDrive.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);
            newRightFrontTarget = rightFrontDrive.getCurrentPosition() + (int)(-distance * COUNTS_PER_INCH);
            newRightBackTarget = rightBackDrive.getCurrentPosition() + (int)(-distance * COUNTS_PER_INCH);

        } else if (driveMode == Mode.TURNRIGHT) {
            newLeftFrontTarget = leftFrontDrive.getCurrentPosition() + (int)(-distance * COUNTS_PER_INCH);
            newLeftBackTarget = leftBackDrive.getCurrentPosition() + (int)(-distance * COUNTS_PER_INCH);
            newRightFrontTarget = rightFrontDrive.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);
            newRightBackTarget = rightBackDrive.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);

        }
        leftFrontDrive.setTargetPosition(newLeftFrontTarget);
        leftBackDrive.setTargetPosition(newLeftBackTarget);
        rightFrontDrive.setTargetPosition(newRightFrontTarget);
        rightBackDrive.setTargetPosition(newRightBackTarget);

        leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFrontDrive.setPower(drive_speed);
        rightFrontDrive.setPower(drive_speed);
        leftBackDrive.setPower(drive_speed);
        rightBackDrive.setPower(drive_speed);

        // Stop all motion;
        leftFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightFrontDrive.setPower(0);
        rightBackDrive.setPower(0);

        // Turn off RUN_TO_POSITION
        leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }






}
