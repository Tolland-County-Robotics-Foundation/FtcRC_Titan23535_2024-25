package org.firstinspires.ftc.teamcode.Sensor_Mechanisms;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import java.util.Objects;

@Autonomous
public class Blinkin_Test {
    RevBlinkinLedDriver blinkin;
    Color_Sensor_v1 color = new Color_Sensor_v1();

    public void init() {
        blinkin = hardwareMap.get(RevBlinkinLedDriver.class, "blinkin");
    }

    public void loop() {
        String blockColor = color.blockColor();

        if (Objects.equals(blockColor, "red")) {
            blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.STROBE_RED);
        } else if (Objects.equals(blockColor, "blue")) {
            blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.STROBE_BLUE);
        } else if (Objects.equals(blockColor, "yellow")) {
            blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.STROBE_GOLD);
        } else {
            blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.STROBE_WHITE);
        }
    }
}
