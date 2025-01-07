package org.firstinspires.ftc.teamcode.TestCodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Sensor_Mechanisms.Color_Sensor_v1;


@TeleOp
public class IntakeTest extends OpMode {

    private CRServo claw        = null;
    private DcMotor intakeArm   = null;
    private Color_Sensor_v1 colorSensor = null;

    private double CLAW_POWER = 0.5;

    private String ALLIANCE_COLOR =  "blue";

    private String colorDetected = "null";

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
        colorDetected = colorSensor.blockColor();
        double intakeArmPower = gamepad2.left_stick_y;
        double clawPower = gamepad2.left_stick_x;

        intakeArm.setPower(intakeArmPower);

        /*
                if alliance color is blue and we detected red then open claw
                else claw.setPower
         */

        if      (ALLIANCE_COLOR == "blue" && colorDetected == "red") {claw.setPower(-0.5);}
        else if (ALLIANCE_COLOR == "red" && colorDetected == "blue") {claw.setPower(-0.5);}
        else {claw.setPower(clawPower);}

        telemetry.addData("claw power 1: ", clawPower);

        telemetry.addData("intake arm power: ", intakeArmPower);

        telemetry.addData("claw power 2: ", claw.getPower());

        telemetry.addData("Color detected: ", colorDetected);

    }
}