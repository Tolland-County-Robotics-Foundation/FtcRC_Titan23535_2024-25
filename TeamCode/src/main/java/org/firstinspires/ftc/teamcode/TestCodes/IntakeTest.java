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
    /// Create an object of colorsensor
    ///
    ///
    private double CLAW_POWER = 0.5;

    @Override
    public void init()
    {
        claw        = hardwareMap.get(CRServo.class, "claw");
        intakeArm   = hardwareMap.get(DcMotor.class, "intakeArm");

        claw.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeArm.setDirection(DcMotorSimple.Direction.FORWARD);

        intakeArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        /// Initialize the colorsensor



    }
    @Override
    public void loop()
    {
        double intakeArmPower = gamepad2.left_stick_y;
        double clawPower = gamepad2.left_stick_x;

        intakeArm.setPower(intakeArmPower);

        telemetry.addData("intake arm power: ", intakeArmPower);

        telemetry.addData("Intake arm position: ", intakeArm.getCurrentPosition());

        /// Use the colorsensor to detact the game piece color

    }
}