//Includes: Drivetrain

package org.firstinspires.ftc.teamcode.Drive_Mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Drive_v1 {


    private DcMotor leftFrontDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightBackDrive = null;


    public void init(HardwareMap hardwareMap)
    {
        leftFrontDrive = hardwareMap.get(DcMotor.class,"leftFront");
        rightFrontDrive = hardwareMap.get(DcMotor.class,"rightFront");
        leftBackDrive = hardwareMap.get(DcMotor.class,"leftBack");
        rightBackDrive = hardwareMap.get(DcMotor.class,"rightBack");
        leftFrontDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotorSimple.Direction.FORWARD);

    }

    public void setDriveMotorPower(double forward, double right, double rotate, double cap)
    {

// Drivetrain

        double leftFrontPower = 0.0;
        double leftBackPower = 0.0;
        double rightFrontPower = 0.0;
        double rightBackPower = 0.0;

        leftFrontPower = forward + right + rotate;
        leftBackPower = forward - right + rotate;
        rightFrontPower = forward - right - rotate;
        rightBackPower = forward + right - rotate;

        double maxPower = 0.0;
        maxPower = Math.max(Math.abs(leftFrontPower), Math.abs(leftBackPower));
        maxPower = Math.max(maxPower, Math.abs(rightFrontPower));
        maxPower = Math.max(maxPower, Math.abs(rightBackPower));


        if (maxPower > 1) {
            leftFrontPower = leftFrontPower / maxPower;
            leftBackPower = leftBackPower / maxPower;
            rightFrontPower = rightFrontPower / maxPower;
            rightBackPower = rightBackPower / maxPower;
        }


        leftFrontPower = leftFrontPower * cap;
        leftBackPower = leftBackPower * cap;
        rightFrontPower = rightFrontPower * cap;
        rightBackPower = rightBackPower * cap;

        leftFrontDrive.setPower(leftFrontPower);
        rightFrontDrive.setPower(rightFrontPower);
        leftBackDrive.setPower(leftBackPower);
        rightBackDrive.setPower(rightBackPower);


    }


}
