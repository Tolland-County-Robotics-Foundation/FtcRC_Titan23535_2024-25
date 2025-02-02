package org.firstinspires.ftc.teamcode.Sensor_Mechanisms;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Disabled
public class Color_Sensor_v2 {
    private ColorSensor color;
    private DistanceSensor distance;

    public void init (HardwareMap hardwareMap) {
        color = hardwareMap.get(ColorSensor.class, "color");
        distance = hardwareMap.get(DistanceSensor.class, "color");
    }

    public int red() {
        return color.red();
    }

    public int blue() {
        return color.blue();
    }
    public int yellow() {
        return color.green() - 50;
    }
    public String sampleColor() {
        int red = red();
        int blue = blue();
        int yellow = yellow();

        if (distance.getDistance(DistanceUnit.INCH) < 2) {
            if (red > 100 && red > blue && red > yellow) {
                return "red";
            } else if (blue > 100 && blue > red && blue > yellow) {
                return "blue";
            } else if (yellow > 100 && yellow > blue && yellow > red) {
                return "yellow";
            }
        } else {
            return "none";
        }
        return "none";
    }
}
