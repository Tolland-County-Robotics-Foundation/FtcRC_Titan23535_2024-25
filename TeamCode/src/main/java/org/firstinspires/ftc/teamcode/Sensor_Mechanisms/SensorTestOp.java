package org.firstinspires.ftc.teamcode.Sensor_Mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class SensorTestOp extends OpMode{

    Color_Sensor_v2 colorB = new Color_Sensor_v2();
    Blinkin_v2 blinkinB = new Blinkin_v2();

    boolean redSpy;

    @Override
    public void init() {
        colorB.init(hardwareMap);
        blinkinB.init(hardwareMap);
    }

    @Override
    public void loop() {
        //blinkinB.light(redSpy);
        boolean alliance = false;
        telemetry.addData("Alliance", "Left Trigger for Red, Right Trigger for Blue");
        if ((gamepad1.left_trigger > 0) && (!alliance)) {
            redSpy = true;
            alliance = true;
            telemetry.addData("Alliance", "Red");
        }
        if ((gamepad1.right_trigger > 0) && (!alliance)) {
            redSpy = false;
            alliance = true;
            telemetry.addData("Alliance", "Blue");
        }

        String sampleColor = colorB.sampleColor();
        telemetry.addData("Block Color", sampleColor);
    }
}