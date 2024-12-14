package org.firstinspires.ftc.teamcode.Sensor_Mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.ColorSensor;

public class Color_Sensor_v1 {
  ColorSensor color;

  public void init (HardwareMap hardwareMap) {
    color = hardwareMap.get(ColorSensor.class, "color");
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
        double red = getAmountRed();
        double blue = getAmountBlue();
        double yellow = getAmountGreen() - 50;
        if (getDistance(DistanceUnit.INCH) < 2.5) {
            if (red > 100 && red > blue && red > yellow) {
                return (String) "red";
            } else if (blue > 100 && blue > red && blue > yellow) {
                return (String) "blue";
            } else if (yellow > 100 && yellow > blue && yellow > red) {
                return (String) "yellow";
            }
        }
        return null;
    }
    public void blockCheck(String color, Boolean redSpy) {
        if (redSpy && color == "blue") {
            intakeClaw.setPosition(0);
        }
        if (!redSpy && color == "red") {
            intakeClaw.setPosition(0);
        }
    }
}
