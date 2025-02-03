package org.firstinspires.ftc.teamcode.Mechanisms_Final;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class ColorDistanceSensor {

    ColorSensor colorSensor;
    DistanceSensor distanceSensor;

    public void init(HardwareMap hardwareMap) {
        colorSensor = hardwareMap.get(ColorSensor.class, "color");
        distanceSensor = hardwareMap.get(DistanceSensor.class, "color");
    }

    public double getDistance(DistanceUnit du) {
        return distanceSensor.getDistance(du);
    }

    public String detectColor() {

        int red = colorSensor.red();
        int blue = colorSensor.blue();
        int yellow = colorSensor.green() - 50;

        if (red > 100 && red > blue && red > yellow) { return "red"; }
        else if (blue > 100 && blue > red && blue > yellow) { return "blue"; }
        else if (yellow > 100 && yellow > blue && yellow > red) { return "yellow"; }
        else return "none";
    }

}
