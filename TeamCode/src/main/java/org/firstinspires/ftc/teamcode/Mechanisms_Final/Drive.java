//Includes: Drivetrain

package org.firstinspires.ftc.teamcode.Mechanisms_Final;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Drive {

    private DcMotorEx leftFrontDrive  = null;
    private DcMotorEx rightFrontDrive = null;
    private DcMotorEx leftBackDrive   = null;
    private DcMotorEx rightBackDrive  = null;

    private double MOTOR_POWER      = 0.5;

    static final double     COUNTS_PER_MOTOR_REV    = 28;    //
    static final double     DRIVE_GEAR_REDUCTION    =  12.0;     // 4:1 External Gearing.
    static final double     WHEEL_DIAMETER_INCHES   =  2.95276;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.25;

    public enum Mode {
        FORWARD, BACKWARD, LEFT, RIGHT, TURNLEFT, TURNRIGHT
    }


    public void init(HardwareMap hardwareMap)
    {

        //Hardware mapping
        leftFrontDrive  = hardwareMap.get(DcMotorEx.class,"leftFront");
        rightFrontDrive = hardwareMap.get(DcMotorEx.class,"rightFront");
        leftBackDrive   = hardwareMap.get(DcMotorEx.class,"leftBack");
        rightBackDrive  = hardwareMap.get(DcMotorEx.class,"rightBack");

        // Setting the direction
        leftFrontDrive.setDirection(DcMotorEx.Direction.FORWARD);
        rightFrontDrive.setDirection(DcMotorEx.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotorEx.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotorEx.Direction.REVERSE);

        leftFrontDrive.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        leftBackDrive.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightFrontDrive.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightBackDrive.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        leftFrontDrive.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        leftBackDrive.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontDrive.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDrive.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        leftFrontDrive.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        leftBackDrive.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        rightFrontDrive.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        rightBackDrive.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);


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

    /// Drive straight forward, backward, left, and right teleop
    public void teleOpForward(){
        leftFrontDrive.setPower(MOTOR_POWER);
        rightFrontDrive.setPower(MOTOR_POWER);
        leftBackDrive.setPower(MOTOR_POWER);
        rightBackDrive.setPower(MOTOR_POWER);
    }

    public void teleOpBackward(){
        leftFrontDrive.setPower(-MOTOR_POWER);
        rightFrontDrive.setPower(-MOTOR_POWER);
        leftBackDrive.setPower(-MOTOR_POWER);
        rightBackDrive.setPower(-MOTOR_POWER);
    }

    public void teleOpLeft(){
        leftFrontDrive.setPower(-MOTOR_POWER);
        rightFrontDrive.setPower(MOTOR_POWER);
        leftBackDrive.setPower(MOTOR_POWER);
        rightBackDrive.setPower(-MOTOR_POWER);
    }

    public void teleOpRight(){
        leftFrontDrive.setPower(MOTOR_POWER);
        rightFrontDrive.setPower(-MOTOR_POWER);
        leftBackDrive.setPower(-MOTOR_POWER);
        rightBackDrive.setPower(MOTOR_POWER);
    }

    public void autoDrive(Drive.Mode driveMode, double distance, double drive_speed){

        int newLeftFrontTarget=0;
        int newLeftBackTarget=0;
        int newRightFrontTarget=0;
        int newRightBackTarget=0;

        if (driveMode == Drive.Mode.FORWARD){
            newLeftFrontTarget = leftFrontDrive.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);
            newLeftBackTarget = leftBackDrive.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);
            newRightFrontTarget = rightFrontDrive.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);
            newRightBackTarget = rightBackDrive.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);
        } else if (driveMode == Drive.Mode.BACKWARD) {
            newLeftFrontTarget = leftFrontDrive.getCurrentPosition() + (int)(-distance * COUNTS_PER_INCH);
            newLeftBackTarget = leftBackDrive.getCurrentPosition() + (int)(-distance * COUNTS_PER_INCH);
            newRightFrontTarget = rightFrontDrive.getCurrentPosition() + (int)(-distance * COUNTS_PER_INCH);
            newRightBackTarget = rightBackDrive.getCurrentPosition() + (int)(-distance * COUNTS_PER_INCH);
        } else if (driveMode == Drive.Mode.RIGHT) {
            newLeftFrontTarget = leftFrontDrive.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);
            newLeftBackTarget = leftBackDrive.getCurrentPosition() + (int)(-distance * COUNTS_PER_INCH);
            newRightFrontTarget = rightFrontDrive.getCurrentPosition() + (int)(-distance * COUNTS_PER_INCH);
            newRightBackTarget = rightBackDrive.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);

        } else if (driveMode == Drive.Mode.LEFT) {
            newLeftFrontTarget = leftFrontDrive.getCurrentPosition() + (int)(-distance * COUNTS_PER_INCH);
            newLeftBackTarget = leftBackDrive.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);
            newRightFrontTarget = rightFrontDrive.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);
            newRightBackTarget = rightBackDrive.getCurrentPosition() + (int)(-distance * COUNTS_PER_INCH);

        }else if (driveMode == Drive.Mode.TURNLEFT) {
            newLeftFrontTarget = leftFrontDrive.getCurrentPosition() + (int)(-distance * COUNTS_PER_INCH);
            newLeftBackTarget = leftBackDrive.getCurrentPosition() + (int)(-distance * COUNTS_PER_INCH);
            newRightFrontTarget = rightFrontDrive.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);
            newRightBackTarget = rightBackDrive.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);

        } else if (driveMode == Drive.Mode.TURNRIGHT) {
            newLeftFrontTarget = leftFrontDrive.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);
            newLeftBackTarget = leftBackDrive.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);
            newRightFrontTarget = rightFrontDrive.getCurrentPosition() + (int)(-distance * COUNTS_PER_INCH);
            newRightBackTarget = rightBackDrive.getCurrentPosition() + (int)(-distance * COUNTS_PER_INCH);

        }
        leftFrontDrive.setTargetPosition(newLeftFrontTarget);
        leftBackDrive.setTargetPosition(newLeftBackTarget);
        rightFrontDrive.setTargetPosition(newRightFrontTarget);
        rightBackDrive.setTargetPosition(newRightBackTarget);

        leftFrontDrive.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        leftBackDrive.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        rightFrontDrive.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        rightBackDrive.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

        leftFrontDrive.setPower(drive_speed);
        rightFrontDrive.setPower(drive_speed);
        leftBackDrive.setPower(drive_speed);
        rightBackDrive.setPower(drive_speed);

    }

    public void autoStop(){
        leftFrontDrive.setPower(0.0);
        rightFrontDrive.setPower(0.0);
        leftBackDrive.setPower(0.0);
        rightBackDrive.setPower(0.0);

        leftFrontDrive.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        leftBackDrive.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        rightFrontDrive.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        rightBackDrive.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

    }

    public boolean isBusy(){
        boolean busy = true;
        if (!leftFrontDrive.isBusy() && !leftBackDrive.isBusy() && !rightBackDrive.isBusy() && !rightFrontDrive.isBusy())
        { busy = false; }
        return busy;
    }

}
