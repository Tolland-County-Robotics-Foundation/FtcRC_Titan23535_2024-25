package org.firstinspires.ftc.teamcode.Sensor_Mechanisms;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.Objects;

public class Blinkin_v2 {
    RevBlinkinLedDriver blinkin;
    RevBlinkinLedDriver.BlinkinPattern pattern;
    Color_Sensor_v2 colorS = new Color_Sensor_v2();

    public void init (HardwareMap hardwareMap) {
        colorS.init(hardwareMap);

        blinkin = hardwareMap.get(RevBlinkinLedDriver.class, "blinkin");
        pattern = RevBlinkinLedDriver.BlinkinPattern.RAINBOW_PARTY_PALETTE;
        blinkin.setPattern(pattern);
    }

    public void light(String redSpy) {
        String color = colorS.sampleColor();

        if (Objects.equals(color, "none")) {
            if (Objects.equals(redSpy, "red")) {
                blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.TWINKLES_LAVA_PALETTE);
            } else if (Objects.equals(redSpy, "blue")) {
                blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.TWINKLES_OCEAN_PALETTE);
            } else {
                blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.RAINBOW_PARTY_PALETTE);
            }
        }
        if (Objects.equals(color, "red")) {
            blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED);
        }
        if (Objects.equals(color, "blue")) {
            blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
        }
        if (Objects.equals(color, "yellow")) {
            blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.YELLOW);
        }
    }
}