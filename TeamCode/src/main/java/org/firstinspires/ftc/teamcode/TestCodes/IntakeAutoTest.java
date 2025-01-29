package org.firstinspires.ftc.teamcode.TestCodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class IntakeAutoTest extends OpMode {

    private CRServo claw        = null;
    private DcMotor intakeArm   = null;

    private double CLAW_POWER = 0.5;

    private int COLLECT_GAMEPIECE_POSITION = 0;
    private int DEPOSIT_GAMEPIECE_POSITION = 0;
    private int RESET_ARM_POSITION = 0;

    int newIntakeArmTarget;

    @Override
    public void init()
    {
        claw        = hardwareMap.get(CRServo.class, "claw");
        intakeArm   = hardwareMap.get(DcMotor.class, "intakeArm");

        claw.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeArm.setDirection(DcMotorSimple.Direction.FORWARD);

        intakeArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }
    @Override
    public void loop()
    {
        double clawPower = gamepad2.left_stick_x;
        claw.setPower(clawPower);

        String armPosition = "reset";

        if (gamepad2.a) {
            newIntakeArmTarget = COLLECT_GAMEPIECE_POSITION;
            armPosition = "collect";
        }
        else if (gamepad2.x) {
            newIntakeArmTarget = DEPOSIT_GAMEPIECE_POSITION;
            armPosition = "deposit";
        }
        else if (gamepad2.b) {
            newIntakeArmTarget = RESET_ARM_POSITION;
            armPosition = "reset";
        }

        intakeArm.setTargetPosition(newIntakeArmTarget);

        intakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        intakeArm.setPower(0.5);

        while (intakeArm.isBusy()){
            telemetry.addData("Going to: ",armPosition);
            telemetry.addData("Intake arm power: ", intakeArm.getPower());
            telemetry.addData("Intake arm position: ", intakeArm.getCurrentPosition());
        }

        telemetry.addData("Intake arm reached to last destination", "waiting for next");
        telemetry.addData("Intake arm power: ", intakeArm.getPower());

    }
}