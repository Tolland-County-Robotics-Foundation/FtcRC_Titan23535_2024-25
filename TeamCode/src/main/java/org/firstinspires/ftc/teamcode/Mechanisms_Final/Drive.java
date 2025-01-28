//Includes: Drivetrain

package org.firstinspires.ftc.teamcode.Mechanisms_Final;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Drive {


    private DcMotor leftFrontDrive  = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor leftBackDrive   = null;
    private DcMotor rightBackDrive  = null;


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

    }

    public void setDriveMotorPower(double forward, double right, double rotate, double cap)
    {

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



}
