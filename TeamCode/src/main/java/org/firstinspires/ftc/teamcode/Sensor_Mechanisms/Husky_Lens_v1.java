package org.firstinspires.ftc.teamcode.Sensor_Mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.hardware.dfrobot.HuskyLens;

public class Husky_Lens_v1 {
    HuskyLens husky;

    public void init(HardwareMap hardwareMap) {
        husky = hardwareMap.get(HuskyLens.class, "husky");
        husky.selectAlgorithm(HuskyLens.Algorithm.TAG_RECOGNITION);
    }

    public Integer getId() {
        HuskyLens.Block[] blocks = husky.blocks(0);
        if (blocks.length > 0) {
            HuskyLens.Block[] id = husky.blocks();
        }
        String blockString = blocks.toString();
        Character idChar = blockString.charAt(4);
        return Integer.parseInt(String.valueOf(idChar));
    }


}
