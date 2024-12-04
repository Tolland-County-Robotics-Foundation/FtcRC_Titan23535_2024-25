package org.firstinspires.ftc.teamcode;
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