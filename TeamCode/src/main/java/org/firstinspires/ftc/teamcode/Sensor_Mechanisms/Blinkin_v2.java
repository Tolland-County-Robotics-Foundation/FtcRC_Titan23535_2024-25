package org.firstinspires.ftc.teamcode.Sensor_Mechanisms;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Blinkin_v2 {
    RevBlinkinLedDriver blinkin;
    RevBlinkinLedDriver.BlinkinPattern pattern;
    Color_Sensor_v2 colorS = new Color_Sensor_v2();

    public void init (HardwareMap hardwareMap) {
        colorS.init(hardwareMap);

        blinkin = hardwareMap.get(RevBlinkinLedDriver.class, "blinkin");
        pattern = RevBlinkinLedDriver.BlinkinPattern.FIRE_LARGE;
        blinkin.setPattern(pattern);
    }

    public void light(String redSpy) {
        String color = colorS.sampleColor();

        if (color == "none") {
            if (redSpy == "red") {
                blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.TWINKLES_LAVA_PALETTE);
            } else if (redSpy == "blue") {
                blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.TWINKLES_OCEAN_PALETTE);
            } else {
                blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.COLOR_WAVES_RAINBOW_PALETTE);
            }
        }
        if (color == "red") {
            blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED);
        }
        if (color == "blue") {
            blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
        }
        if (color == "yellow") {
            blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.YELLOW);
        }
    }
}