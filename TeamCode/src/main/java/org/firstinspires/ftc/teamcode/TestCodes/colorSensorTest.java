package org.firstinspires.ftc.teamcode.TestCodes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Sensor_Mechanisms.Color_Sensor_v1;

@TeleOp
@Disabled
public class colorSensorTest extends OpMode {

    Color_Sensor_v1 color = new Color_Sensor_v1();

    @Override
    public void init()
    {

        color.init(hardwareMap);
        telemetry.addData("Initialized:", "ok");

    }
    @Override
    public void loop()
    {
        telemetry.addData("Loop:", "ok");
        telemetry.addData("red: ",color.getAmountRed());
        telemetry.addData("block color: ", color.blockColor());

    }
}
