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

    // Create a constant variable to set the claw power

    private double CLAW_POWER = 0.5;

    private String ALLIANCE_COLOR =  null;

    // For autonomous movement of intake arm

    private double DISTANCE_PER_INCH = 0.0;

    private double COUNTS_PER_REV = 0.0;

    private double COUNTS_PER_INCH = COUNTS_PER_REV / DISTANCE_PER_INCH;




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
        intakeArm   = hwMap.get(DcMotor.class, "intakeArm");

        claw.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeArm.setDirection(DcMotorSimple.Direction.FORWARD);

        intakeArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

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

    public void autoMove(String driveMode, double distance, double speed){

        if (driveMode == "collect"){
            int newIntakeArmTarget = intakeArm.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);
        } else if (driveMode == "release") {
            int newIntakeArmTarget = intakeArm.getCurrentPosition() + (int)(-distance * COUNTS_PER_INCH);
        }

        int newIntakeArmTarget = intakeArm.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);

        intakeArm.setTargetPosition(newIntakeArmTarget);

        intakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        intakeArm.setPower(speed);

        intakeArm.setPower(0.0);

        intakeArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

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
