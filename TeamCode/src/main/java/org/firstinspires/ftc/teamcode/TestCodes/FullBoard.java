package org.firstinspires.ftc.teamcode.TestCodes;

import static android.os.SystemClock.sleep;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.hardware.Servo;
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
    DcMotor intakeWheel;
    //Color Sensor
    private ColorSensor color;
    private DistanceSensor distance;
    //Touch Sensor
    private DigitalChannel touch;
    //Husky Lens
    HuskyLens husky;

    public void init (HardwareMap hardwareMap) {
        //Drivetrain
        leftFront = BlocksOpModeCompanion.hardwareMap.get(DcMotor.class, "leftFront");
        leftFront.setDirection(DcMotorSimple.Direction.FORWARD);
        leftBack = BlocksOpModeCompanion.hardwareMap.get(DcMotor.class, "leftBack");
        leftBack.setDirection(DcMotorSimple.Direction.FORWARD);
        rightBack = BlocksOpModeCompanion.hardwareMap.get(DcMotor.class, "rightBack");
        rightBack.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFront = BlocksOpModeCompanion.hardwareMap.get(DcMotor.class, "rightFront");
        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
        //Linear Slide
        leftArm = BlocksOpModeCompanion.hardwareMap.get(DcMotor.class, "leftArm");
        leftArm.setDirection(DcMotorSimple.Direction.REVERSE);
        rightArm = BlocksOpModeCompanion.hardwareMap.get(DcMotor.class, "rightArm");
        rightArm.setDirection(DcMotorSimple.Direction.FORWARD);
        leftS = BlocksOpModeCompanion.hardwareMap.get(Servo.class, "leftS");
        leftS.setDirection(Servo.Direction.FORWARD);
        rightS = BlocksOpModeCompanion.hardwareMap.get(Servo.class, "rightS");
        rightS.setDirection(Servo.Direction.REVERSE);
        //Intake
        intakeWheel = BlocksOpModeCompanion.hardwareMap.get(DcMotor.class, "intakeWheel");
        intakeWheel.setDirection(DcMotorSimple.Direction.FORWARD);
        intake = BlocksOpModeCompanion.hardwareMap.get(DcMotor.class, "intake");
        intake.setDirection(DcMotorSimple.Direction.FORWARD);
        //Color Sensor
        color = BlocksOpModeCompanion.hardwareMap.get(ColorSensor.class, "color");
        distance = BlocksOpModeCompanion.hardwareMap.get(DistanceSensor.class, "color");
        //Touch Sensor
        touch = BlocksOpModeCompanion.hardwareMap.get(DigitalChannel.class, "touch");
        touch.setMode(DigitalChannel.Mode.INPUT);
        //Husky Lens
        husky = BlocksOpModeCompanion.hardwareMap.get(HuskyLens.class, "husky");
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
    public double getDistance(DistanceUnit du) {
        return distance.getDistance(du);
    }
    //Touch Sensor
    public boolean isTouchSensorPressed() {
        return !touch.getState();
    }
}