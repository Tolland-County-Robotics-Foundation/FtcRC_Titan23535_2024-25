package org.firstinspires.ftc.teamcode.Mechanisms_Final;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class Intake {

    // Create one object of DcMotor class for intake arm and one object of Servo class for claw

    private CRServo claw = null;
    private DcMotor arm = null;

    // Create a constant variable to set the claw power

    private double CLAW_POWER = 0.5;


    /*Create an method (init)

        //Return of this method is void


        //Do hardware mapping for all the objects


        //Set the direction of rotation of the DC motor


        //Set the direction of rotation of the servo motor


        //Add break to the DC motor (using zero power behavior)

     */

    public void init(HardwareMap hwMap)
    {
        claw        = hwMap.get(CRServo.class, "claw");
        arm = hwMap.get(DcMotor.class, "intakeArm");

        claw.setDirection(DcMotorSimple.Direction.FORWARD);
        arm.setDirection(DcMotorSimple.Direction.FORWARD);

        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }


    // Method to move the intake arm

    public void moveArm(double armPower)
    {
        arm.setPower(armPower);
    }

    // Method to move the intake claw

    public void moveClaw(double clawPower)
    {
        claw.setPower(clawPower);
    }


    // create a method to open the claw

    public void openClaw()
    {
        claw.setPower(-CLAW_POWER);
    }

    public void closeClaw()
    {
        claw.setPower(CLAW_POWER);
    }

    public void stopClaw()
    {
        claw.setPower(0.0);
    }

}
