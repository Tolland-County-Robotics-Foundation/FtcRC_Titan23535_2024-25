package org.firstinspires.ftc.teamcode.TestCodes;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.LED;

@TeleOp
public class BlinkinTest extends OpMode{
    RevBlinkinLedDriver blinkin;

    @Override
    public void init() {
        blinkin = hardwareMap.get(RevBlinkinLedDriver.class, "blinkin");
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.DARK_GREEN);
    }

    @Override
    public void loop() {

    }
}
