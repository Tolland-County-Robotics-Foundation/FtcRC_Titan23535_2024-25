package org.firstinspires.ftc.teamcode.TestCodes;

import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp
public class FullTeleOp extends OpMode {
    FullBoard board = new FullBoard();
    Boolean redSpy;

    public void init() {
        board.init(hardwareMap);
        //Husky Lens
        board.husky.selectAlgorithm(HuskyLens.Algorithm.TAG_RECOGNITION);
        telemetry.addData("Are you on the Red Alliance or the Blue Alliance?", "Left trigger for Red, right trigger for Blue");
        while (true) {
            if (gamepad1.left_trigger > 0.1) {
                redSpy = true;
                break;
            }
            if (gamepad1.right_trigger > 0.1) {
                redSpy = false;
                break;
            }
        }
    }

    public void loop() {
        //Drivetrain
        {
            double leftFrontPower = 0.0;
            double leftBackPower = 0.0;
            double rightFrontPower = 0.0;
            double rightBackPower = 0.0;
            //Controller Reading
            double forward = -gamepad1.left_stick_y;
            double right = gamepad1.left_stick_x;
            double rotate = gamepad1.right_stick_x;
            //Boring Math
            leftFrontPower = forward + right + rotate;
            leftBackPower = forward - right + rotate;
            rightFrontPower = forward - right - rotate;
            rightBackPower = forward + right - rotate;
            double maxPower = 0.0;
            maxPower = Math.max(Math.abs(leftFrontPower), Math.abs(leftBackPower));
            maxPower = Math.max(maxPower, Math.abs(rightFrontPower));
            maxPower = Math.max(maxPower, Math.abs(rightBackPower));
            telemetry.addData("Max", maxPower);
            if (maxPower > 1) {
                leftFrontPower = leftFrontPower / maxPower;
                leftBackPower = leftBackPower / maxPower;
                rightFrontPower = rightFrontPower / maxPower;
                rightBackPower = rightBackPower / maxPower;
            }
            board.leftFront.setPower(leftFrontPower);
            board.rightFront.setPower(rightFrontPower);
            board.leftBack.setPower(leftBackPower);
            board.rightBack.setPower(rightBackPower);
        }
        //Linear Slide
        {
            double liftPower = gamepad2.left_stick_y;
            double turn = gamepad2.right_stick_y;
            double turnPower = 0.5;

            if (turn > 0) {
                turnPower = turnPower + 0.1;
            }
            if (turn < 0) {
                turnPower = turnPower - 0.1;
            }

            board.leftSlide.setPower(liftPower);
            board.rightSlide.setPower(liftPower);

            board.leftBasket.setPosition(turnPower);
            board.rightBasket.setPosition(turnPower);
        }
        //Intake
        {
            float leftTrig = gamepad2.left_trigger;
            float rightTrig = gamepad2.right_trigger;
            if (leftTrig > 0 && rightTrig <= 0) {
                board.intakeArm.setPower(leftTrig);
            } else if (rightTrig > 0 && leftTrig <= 0) {
                board.intakeArm.setPower(rightTrig);
            } else {
                board.intakeArm.setPower(0);
            }
            if (gamepad2.left_bumper) { // Out
                board.intakeArm.setPower(-0.7);
            } else if (gamepad2.right_bumper) { // In
                board.intakeArm.setPower(0.7);
            } else {
                board.intakeArm.setPower(0);
            }
        }
        //Color Sensor
        {
            telemetry.addData("Amount red", board.getAmountRed());
            telemetry.addData("Amount blue", board.getAmountBlue());
            telemetry.addData("Amount green", board.getAmountGreen());
            telemetry.addData("Distance CM", board.getDistance(DistanceUnit.CM));
            telemetry.addData("Distance IN", board.getDistance(DistanceUnit.INCH));
            String color = board.blockColor();
            board.blockCheck(color, redSpy);
        }
        //Touch Sensor
        telemetry.addData("TouchPressed", board.isTouchSensorPressed());
        //Husky Lens
        Integer id = board.getId();
    }
}