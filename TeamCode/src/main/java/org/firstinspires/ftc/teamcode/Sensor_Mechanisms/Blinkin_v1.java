package org.firstinspires.ftc.teamcode.Sensor_Mechanisms;


import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.Objects;
import static android.os.SystemClock.sleep;

public class Blinkin_v1 {
    RevBlinkinLedDriver blinkin;
    Color_Sensor_v1 color = new Color_Sensor_v1();

    public void init() {
        blinkin = hardwareMap.get(RevBlinkinLedDriver.class, "blinkin");
    }
    public void sample(boolean alliance) {
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
