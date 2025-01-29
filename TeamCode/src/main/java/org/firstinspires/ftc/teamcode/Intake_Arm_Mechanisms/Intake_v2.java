/*
    Intake_v1 + color sensor
 */

package org.firstinspires.ftc.teamcode.Intake_Arm_Mechanisms;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake_v2 {

    // Create one object of DcMotor class for intake arm and one object of Servo class for claw

    private CRServo claw        = null;
    private DcMotor intakeArm   = null;

    /// Create an object of colorsensor

    // Create a constant variable to set the claw power

    private double CLAW_POWER = 0.5;

    private String ALLIANCE_COLOR =  null;


    public void init(HardwareMap hwMap)
    {
        claw        = hwMap.get(CRServo.class, "claw");
        intakeArm   = hwMap.get(DcMotor.class, "intakeArm");

        claw.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeArm.setDirection(DcMotorSimple.Direction.FORWARD);

        intakeArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        /// Initialize the colorsensor

    }


    /// Intake Arm

    // Stop intake arm

    public void stopArm(){ intakeArm.setPower(0.0);}

    // Method to move the intake arm teleop

    public void moveIntakeArm(double intakeArmPower)
    {
        intakeArm.setPower(intakeArmPower);
    }

    // Method to move the intake arm autonomously

    public void autoMove(String driveMode, double speed){

        int newIntakeArmTarget = 0;

        // Option 1

        if (driveMode == "collect"){
            newIntakeArmTarget = 575;
        } else if (driveMode == "deposit") {
            newIntakeArmTarget = 256;
        }

        intakeArm.setTargetPosition(newIntakeArmTarget);

        intakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        intakeArm.setPower(speed);

    }

    public void moveIntakeClaw(double intakeClawPower)
    {
        claw.setPower(intakeClawPower);
    }


    // create methods to control the claw

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
