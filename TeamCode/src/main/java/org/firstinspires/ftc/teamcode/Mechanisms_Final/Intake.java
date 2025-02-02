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

    public enum Mode {
        COLLECT, DEPOSIT, RESET
    }

    private int COLLECT_GAMEPIECE_POSITION = 0;
    private int DEPOSIT_GAMEPIECE_POSITION = 0;
    private int RESET_ARM_POSITION = 0;


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

    /// Arm Mechanisms -------------------------------------------------------------------

    // Method to move the intake arm

    public void moveArm(double armPower) { arm.setPower(armPower); }

    public void autoMoveArm(Mode runMode){

        int newIntakeArmTarget = 0;

        if (runMode == Mode.COLLECT){

            newIntakeArmTarget = COLLECT_GAMEPIECE_POSITION;
            
        } else if (runMode == Mode.DEPOSIT) {

            newIntakeArmTarget = DEPOSIT_GAMEPIECE_POSITION;
            
        } else if (runMode == Mode.RESET) {

            newIntakeArmTarget = RESET_ARM_POSITION;
            
        }

        arm.setTargetPosition(newIntakeArmTarget);

        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        arm.setPower(0.5);
    }

    /// Claw Mechanisms -------------------------------------------------------------------

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
