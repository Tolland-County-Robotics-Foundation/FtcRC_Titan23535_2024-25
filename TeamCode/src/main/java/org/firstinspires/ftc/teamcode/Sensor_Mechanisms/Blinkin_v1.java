package org.firstinspires.ftc.teamcode.Sensor_Mechanisms;


import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.Objects;
import static android.os.SystemClock.sleep;

public class Blinkin_v1 {
    RevBlinkinLedDriver blinkin;
    Color_Sensor_v1 color = new Color_Sensor_v1();

    private final ElapsedTime timer = new ElapsedTime();


    public void init() {
        blinkin = hardwareMap.get(RevBlinkinLedDriver.class, "blinkin");
    }

    public void allianceSet(boolean alliance) {
        if (timer.milliseconds() >= 3000) {
            if (alliance) {
                blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.COLOR_WAVES_LAVA_PALETTE);
            } else if (!alliance) {
                blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.COLOR_WAVES_OCEAN_PALETTE);
            }
        }
    }

    public void sample(boolean alliance) {
        String blockColor = color.blockColor();
        timer.reset();
        if (Objects.equals(blockColor, "red")) {
            blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.STROBE_RED);
        } else if (Objects.equals(blockColor, "blue")) {
            blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.STROBE_BLUE);
        } else if (Objects.equals(blockColor, "yellow")) {
            blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.STROBE_GOLD);
        } else {
            blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.STROBE_WHITE);
        }
        if (timer.milliseconds() >= 3000) {
            allianceSet(alliance);
        }
    }
}
