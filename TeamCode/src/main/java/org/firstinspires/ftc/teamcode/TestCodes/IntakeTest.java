package org.firstinspires.ftc.teamcode.TestCodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@TeleOp
public class IntakeTest extends OpMode {

    private CRServo claw        = null;
    private DcMotor intakeArm   = null;

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
        double intakeArmPower = gamepad2.left_trigger - gamepad2.right_trigger;
        intakeArm.setPower(intakeArmPower);

        if (gamepad2.left_bumper)
        {
            claw.setPower(1);
        }
        else if (gamepad2.right_bumper)
        {
            claw.setPower(-1);
        }
        else
        {
            claw.setPower(0.0);
        }

        telemetry.addData("intake arm power: ", intakeArmPower);

        telemetry.addData("claw power: ", claw.getPower());

    }
}