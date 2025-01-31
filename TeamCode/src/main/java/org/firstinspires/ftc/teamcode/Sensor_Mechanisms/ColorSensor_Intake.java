package org.firstinspires.ftc.teamcode.Sensor_Mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Drive_Mechanisms.Drive_v1;

import java.util.Objects;

@TeleOp
public class ColorSensor_Intake extends OpMode {
    /// Creating objects for the drivetrain, color sensor, REV Blinkin, and runtime.
    Color_Sensor_v2 colorB = new Color_Sensor_v2();
    Blinkin_v2 lightB = new Blinkin_v2();
    private final ElapsedTime runtime = new ElapsedTime();

    /// Initializing variables to use across the entire program.
    Boolean reject;
    Boolean accept;
    String redSpy;
    Double clawPower;

    ///Creating objects for the intake mechanisms.
    private CRServo claw        = null;
    private DcMotor intakeArm   = null;

    @Override
    public void init()
    {
        // Resetting runtime
        runtime.reset();

        // Use init method to do the initialization of drive object
        colorB.init(hardwareMap);
        lightB.init(hardwareMap);

        /// Initialization of intake mechanisms.
        claw        = hardwareMap.get(CRServo.class, "claw");
        claw.setDirection(DcMotorSimple.Direction.FORWARD);

        intakeArm   = hardwareMap.get(DcMotor.class, "intakeArm");
        intakeArm.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    @Override
    public void loop()
    {
        /// Alliance data.
        telemetry.addData("Alliance", "Left Trigger for Red, Right Trigger for Blue");
        if (gamepad1.left_trigger > 0) {
            redSpy = "red";
            telemetry.addData("Alliance", "Red");
        }
        if (gamepad1.right_trigger > 0) {
            redSpy = "blue";
            telemetry.addData("Alliance", "Blue");
        }

        ///Miscellaneous telemetries.
        //Displaying runtime.
        telemetry.addData("Status", "Run Time: " + runtime.seconds());

        /// Intake mechanisms.
        double intakeArmPower = gamepad2.left_stick_y * 0.5;

        intakeArm.setPower(intakeArmPower);
/*
        if (accept) {
            clawPower = 1.0;
        } else if (reject) {
            clawPower = -1.0;
        } else {
            clawPower = 0.0;
        }
*/
        if (gamepad2.right_stick_x > 0) {
            clawPower = 1.0;
        } else if ((gamepad2.right_stick_x < 0)) {
            clawPower = -1.0;
        } else {
            clawPower = 0.0;
        }

        claw.setPower(clawPower);
        /// Color Sensor.
        String sample = colorB.sampleColor();

        //Automatic sample rejection system.
        reject = false;
        accept = false;

        if (Objects.equals(redSpy, "red")) {
            if (Objects.equals(sample, "blue")) {
                reject = true;
            }
        }
        if (Objects.equals(redSpy, "blue")) {
            if (Objects.equals(sample, "red")) {
                reject = true;
            }
        }

        //Automatic sample acceptation system.
        if (Objects.equals(redSpy, "red")) {
            if (Objects.equals(sample, "red")) {
                accept = true;
            }
        }
        if (Objects.equals(redSpy, "blue")) {
            if (Objects.equals(sample, "blue")) {
                accept = true;
            }
        }
        if (Objects.equals(sample, "yellow")) {
            accept = true;
        }

        //Telemetry
        telemetry.addData("Sample Color", colorB.sampleColor());
        telemetry.addData("Reject?", reject);
        telemetry.addData("Accept?", accept);

        /// Rev BLINKIN
        lightB.light(redSpy);
    }
}