package org.firstinspires.ftc.teamcode.Sensor_Mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;

import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Distance_Sensor_v1 {
  DistanceSensor distance;

  public void init (HardwareMap hardwareMap) {
    distance = hardwareMap.get(DistanceSensor.class, "color");
  }

  public double getDistance(DistanceUnit du) {
    return distance.getDistance(du);
  }
}
