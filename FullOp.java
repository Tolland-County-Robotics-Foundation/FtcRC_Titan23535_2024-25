package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp
public class FullOp extends OpMode {
    FullBoard board = new FullBoard();

    public void init() {
        board.init(hardwareMap);
        //Husky Lens
        board.husky.selectAlgorithm(HuskyLens.Algorithm.TAG_RECOGNITION);
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
            double turn = gamepad2.right_stick_x;
            double turnPower = 0.0;

            if (turn > 0) {
                turnPower = turnPower + 0.2;
            }
            if (turn < 0) {
                turnPower = turnPower - 0.2;
            }

            board.leftArm.setPower(liftPower);
            board.rightArm.setPower(liftPower);

            board.leftS.setPosition(turnPower);
            board.rightS.setPosition(turnPower);
        }
        //Intake
        {
            if (gamepad2.left_bumper) { // Out
                board.intake.setPower(-0.7);
            } else if (gamepad2.right_bumper) { // In
                board.intake.setPower(0.7);
            } else {
                board.intake.setPower(0);
            }
        }
        //Color Sensor
        {
            telemetry.addData("Amount red", board.getAmountRed());
            telemetry.addData("Amount blue", board.getAmountBlue());
            telemetry.addData("Amount green", board.getAmountGreen());
            telemetry.addData("Distance CM", board.getDistance(DistanceUnit.CM));
            telemetry.addData("Distance IN", board.getDistance(DistanceUnit.INCH));
            double yellowCalc = board.getAmountGreen() - 50;
            if (board.getDistance(DistanceUnit.INCH) < 2.5) {
                if (board.getAmountRed() > 100 && board.getAmountRed() > board.getAmountBlue() && board.getAmountRed() > yellowCalc) {
                    telemetry.addData("Block Color", "RED");
                } else if (board.getAmountBlue() > 100 && board.getAmountBlue() > board.getAmountRed() && board.getAmountBlue() > yellowCalc) {
                    telemetry.addData("Block Color", "BLUE");
                } else if (board.getAmountGreen() > 150 && board.getAmountGreen() > yellowCalc && yellowCalc > board.getAmountRed()) {
                    telemetry.addData("Block Color", "YELLOW");
                }
            }
        }
        //Touch Sensor
        telemetry.addData("TouchPressed", board.isTouchSensorPressed());
        //Husky Lens
        {
            HuskyLens.Block[] blocks = board.husky.blocks();
            if (blocks.length > 0) {
                HuskyLens.Block[] id = board.husky.blocks();
            }
            telemetry.addData("Block count", blocks.length);
            for (HuskyLens.Block block : blocks) {
                telemetry.addData("Block", block.toString());
            }
        }
    }
}