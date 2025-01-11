
package org.firstinspires.ftc.teamcode.Sensor_Mechanisms;



import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.Objects;

public class Blinkin_v1 {
    RevBlinkinLedDriver blinkin;
    Color_Sensor_v1 color = new Color_Sensor_v1();

    static {
        new ElapsedTime();
    }


    public void init(HardwareMap hardwareMap) {
        blinkin = hardwareMap.get(RevBlinkinLedDriver.class, "blinkin");
    }

    public void sample(String redSpy) {
        String blockColor = color.blockColor();
        //timer.reset();
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
