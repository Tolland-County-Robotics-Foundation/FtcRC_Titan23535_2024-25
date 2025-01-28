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

    @Override
    public void init()
    {
        claw        = hardwareMap.get(CRServo.class, "claw");
        intakeArm   = hardwareMap.get(DcMotor.class, "intakeArm");

        claw.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeArm.setDirection(DcMotorSimple.Direction.FORWARD);

        intakeArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }
    @Override
    public void loop()
    {
        double clawPower = gamepad2.left_stick_x;
        claw.setPower(clawPower);

        int newIntakeArmTarget = -1350;
        double speed = 0.3;

        intakeArm.setTargetPosition(newIntakeArmTarget);

        intakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        intakeArm.setPower(speed);

        while (intakeArm.isBusy()){
            telemetry.addData("intake arm power: ", newIntakeArmTarget);

            telemetry.addData("Intake arm position: ", intakeArm.getCurrentPosition());
        }

        telemetry.addData("intake arm power: ", newIntakeArmTarget);

        telemetry.addData("Intake arm position: ", intakeArm.getCurrentPosition());

        intakeArm.setPower(0);
        intakeArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }
}