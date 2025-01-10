
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

    public void sample(String redSpy) {
        String blockColor = color.blockColor();
        timer.reset();
        if (Objects.equals(blockColor, "red")) {
            blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.STROBE_RED);
        } else if (Objects.equals(blockColor, "blue")) {
            blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.STROBE_BLUE);
        } else if (Objects.equals(blockColor, "yellow")) {
            blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.STROBE_GOLD);
        } else {
            if (Objects.equals(redSpy, "red")) {
                blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED);
            } else if (Objects.equals(redSpy, "blue")) {
                blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
            } else {
                blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.WHITE);
            }
        }
    }
}
