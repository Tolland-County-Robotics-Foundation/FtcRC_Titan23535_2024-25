package org.firstinspires.ftc.teamcode.Intake_Arm_Mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class ServoTest extends OpMode {

    private Servo motor = null;
    private double intakePos = 0.0;

    @Override
    public void init() {
        motor  = hardwareMap.get(Servo.class, "BasketArm");
        motor.setDirection(Servo.Direction.FORWARD);
    }

    @Override
    public void loop() {

        boolean openButton = gamepad2.left_bumper;

        if (openButton) {
            intakePos = 0.1;
        }
        motor.setPosition(intakePos);


    }
}