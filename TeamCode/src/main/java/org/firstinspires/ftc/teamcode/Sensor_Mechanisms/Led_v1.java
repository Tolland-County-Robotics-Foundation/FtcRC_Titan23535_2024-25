package org.firstinspires.ftc.teamcode.Sensor_Mechanisms;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Led_v1 {

    RevBlinkinLedDriver LED;

    public void init(HardwareMap hardwareMap){
        LED = hardwareMap.get(RevBlinkinLedDriver.class, "blinkin");
    }

    public void redOn(){
        LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.SHOT_RED);
    }

    public void blueOn(){
        LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.SHOT_BLUE);
    }
}
