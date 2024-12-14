package org.firstinspires.ftc.teamcode.TestCodes;

import static android.os.SystemClock.sleep;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;

import java.util.Arrays;

public class FullBoard {
    //Drivetrain
    DcMotor leftFront;
    DcMotor leftBack;
    DcMotor rightBack;
    DcMotor rightFront;
    //Linear Slide
    DcMotor leftArm;
    DcMotor rightArm;
    Servo leftS;
    Servo rightS;
    //Intake

    DcMotor intake;
    Servo intakeClaw;
    //Color Sensor
    ColorSensor color;
    DistanceSensor distance;
    //Touch Sensor
    DigitalChannel touch;
    //Husky Lens
    HuskyLens husky;
    //REV Blinkin
    RevBlinkinLedDriver blinkin;
    public void init (HardwareMap hardwareMap) {
        //Drivetrain
        leftFront = hardwareMap.dcMotor.get("leftFront");
        leftFront.setDirection(DcMotorSimple.Direction.FORWARD);
        leftBack = hardwareMap.dcMotor.get("leftBack");
        leftBack.setDirection(DcMotorSimple.Direction.FORWARD);
        rightBack = hardwareMap.dcMotor.get("rightBack");
        rightBack.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFront = hardwareMap.dcMotor.get("rightFront");
        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
        //Linear Slide
        leftArm = hardwareMap.dcMotor.get("leftArm");
        leftArm.setDirection(DcMotorSimple.Direction.REVERSE);
        rightArm = hardwareMap.dcMotor.get("rightArm");
        rightArm.setDirection(DcMotorSimple.Direction.FORWARD);
        leftS = hardwareMap.servo.get("leftS");
        leftS.setDirection(Servo.Direction.FORWARD);
        rightS = hardwareMap.servo.get("rightS");
        rightS.setDirection(Servo.Direction.REVERSE);
        //Intake
        intakeClaw = hardwareMap.servo.get("intakeWheel");
        intakeClaw.setDirection(Servo.Direction.FORWARD);
        intake = hardwareMap.dcMotor.get("intake");
        intake.setDirection(DcMotorSimple.Direction.FORWARD);
        //Color Sensor
        color = hardwareMap.get(ColorSensor.class, "color");
        distance = hardwareMap.get(DistanceSensor.class, "color");
        //Touch Sensor
        touch = hardwareMap.get(DigitalChannel.class, "touch");
        touch.setMode(DigitalChannel.Mode.INPUT);
        //Husky Lens
        husky = hardwareMap.get(HuskyLens.class, "husky");
        //REV Blinkin
        //blinkin = hardwareMap.get(RevBlinkinLedDriver.class, "blinkin");
    }

    //Function Commands (For Autonomous)
    public void driveForward(Integer duration, double speed) {
        leftFront.setPower(speed);
        leftBack.setPower(speed);
        rightBack.setPower(speed);
        rightFront.setPower(speed);
        sleep(duration);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
     /*public void driveSide(Integer duration, double speed) {
        leftFront.setPower(-speed);
        leftBack.setPower(-speed);
        rightBack.setPower(speed);
        rightFront.setPower(speed);
        sleep(duration);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }*/ //The FORBIDDEN area (Its just a failed move sideways thingy, find actual motor movements)
    public void turn(Integer duration, double speed) {
        leftFront.setPower(speed);
        leftBack.setPower(speed);
        rightBack.setPower(-speed);
        rightFront.setPower(-speed);
        sleep(duration);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public void armRotate(double position) {
        leftS.setPosition(position);
        rightS.setPosition(position);
    }
    public void basket(Integer duration, double speed) {
        leftArm.setPower(speed);
        rightArm.setPower(speed);
        sleep(duration);
        leftArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public void intake(Integer duration, double speed) {
        intake.setPower(speed);
        sleep(duration);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    //Color Sensor
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
    public double getDistance(DistanceUnit du) {
        return distance.getDistance(du);
    }
    //Touch Sensor
    public boolean isTouchSensorPressed() {
        return !touch.getState();
    }
    //Husky Sensor
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