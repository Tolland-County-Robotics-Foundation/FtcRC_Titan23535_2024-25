package org.firstinspires.ftc.teamcode.Sensor_Mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.hardware.dfrobot.HuskyLens;

public class Husky_Lens_v1 {
    HuskyLens husky;

    public void init(HardwareMap hardwareMap) {
        husky = hardwareMap.get(HuskyLens.class, "husky");
    }


}
