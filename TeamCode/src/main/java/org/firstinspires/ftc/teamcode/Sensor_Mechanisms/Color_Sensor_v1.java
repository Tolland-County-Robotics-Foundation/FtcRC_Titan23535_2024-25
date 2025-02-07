package org.firstinspires.ftc.teamcode.Sensor_Mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Color_Sensor_v1 {
  ColorSensor color;
  DistanceSensor distance;

    public void init (HardwareMap hardwareMap) {
        color = hardwareMap.get(ColorSensor.class, "color");
        distance = hardwareMap.get(DistanceSensor.class, "color");
    }

    public double getDistance(DistanceUnit du) {
        return distance.getDistance(du);
    }
    public int getAmountRed() {
        return color.red();
    }
    public int getAmountBlue() {
        return color.blue();
    }
    public int getAmountGreen() {
        return color.green();
    }
    public String blockColor() {
        int red = getAmountRed();
        int blue = getAmountBlue();
        int yellow = getAmountGreen() - 50;

        if (red > 100 && red > blue && red > yellow) {
            return "red";
        } else if (blue > 100 && blue > red && blue > yellow) {
            return "blue";
        } else if (yellow > 100 && yellow > blue && yellow > red) {
            return "yellow";
        } else {
            return "none";
        }
    }
}