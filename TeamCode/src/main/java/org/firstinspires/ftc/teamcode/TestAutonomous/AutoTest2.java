package org.firstinspires.ftc.teamcode.TestAutonomous;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Mechanisms_Final.Drive;
import org.firstinspires.ftc.teamcode.Mechanisms_Final.Intake;
import org.firstinspires.ftc.teamcode.Mechanisms_Final.LongArm;

@Autonomous(name="Auto Test 2", group="Autonomous")

public class AutoTest2 extends LinearOpMode {


    // Hardware
    private Drive   drive   = new Drive();
    private Intake  intake  = new Intake();
    private LongArm longArm = new LongArm();

    // Timers
    private ElapsedTime runtime     = new ElapsedTime();
    private ElapsedTime clawTimer   = new ElapsedTime();
    private ElapsedTime basketTimer = new ElapsedTime();

    @Override
    public void runOpMode() {

        // 1) Initialize hardware
        drive.init(hardwareMap);
        intake.init(hardwareMap);
        longArm.init(hardwareMap);

        telemetry.addData("Status", "Initialized, waiting for start...");
        telemetry.update();

        // 2) Wait for start
        waitForStart();

        // 3) Reset timers after start
        runtime.reset();
        clawTimer.reset();
        basketTimer.reset();

        if (opModeIsActive()) {
            /*
             * -------------------------------
             *  SAMPLE 0 SCORE
             * -------------------------------
             * Steps:
             *   1) Drive left 10 inches
             *   2) Drive backward 5 inches
             *   3) Move intake arm to collect + lift linear slide
             *   4) Move basket to score sample
             */

            // Step 1) Drive LEFT 10 inches
            drive.autoDrive(Drive.Mode.LEFT, 10, 0.9);
            while (opModeIsActive() && drive.isBusy()) {
                telemetry.addData("Sample 0 Score", "Driving LEFT 10 in");
                telemetry.update();
            }

            // Step 2) Drive BACKWARD 5 inches
            drive.autoDrive(Drive.Mode.BACKWARD, 5, 0.9);
            longArm.basketReset();
            while (opModeIsActive() && drive.isBusy()) {
                telemetry.addData("Sample 0 Score", "Driving BACKWARD 5 in");
                telemetry.update();
            }

            // Step 3) Move intake arm to collect + lift linear slide
            intake.autoMoveArm(Intake.Mode.COLLECT);
            longArm.basketReset();
            longArm.autoLiftLinearSlide();
            while (opModeIsActive() && (intake.isArmBusy() || longArm.isLinearSlideBusy())) {
                telemetry.addData("Sample 0 Score", "Moving Arm & Linear Slide");
                telemetry.update();
            }

            // Step 4) Move basket to score sample (wait 3s)
            longArm.basketScoreSample();
            basketTimer.reset();
            while (opModeIsActive() && basketTimer.milliseconds() < 3000) {
                telemetry.addData("Sample 0 Score", "Scoring sample...");
                telemetry.update();
            }
            telemetry.addData("Sample 0 Score", "Done!");
            telemetry.update();


            /*
             * -------------------------------
             *  SAMPLE 1 COLLECT
             * -------------------------------
             * Steps:
             *   1) Reset basket & linear slide
             *   2) Turn LEFT 9 inches
             *   3) Drive FORWARD 7 inches
             *   4) Close claw to collect sample (3s)
             */

            // Step 1) Reset basket + reset linear slide
            longArm.basketReset();
            longArm.autoResetLinearSlide();
            while (opModeIsActive() && longArm.isLinearSlideBusy()) {
                telemetry.addData("Sample 1 Collect", "Resetting linear slide");
                telemetry.update();
            }

            // Step 2) Turn LEFT 9 inches
            drive.autoDrive(Drive.Mode.TURNLEFT, 9.0, 0.5);
            intake.openClaw();
            while (opModeIsActive() && drive.isBusy()) {
                telemetry.addData("Sample 1 Collect", "Turning LEFT");
                telemetry.update();
            }

            // Step 3) Drive FORWARD 7 inches
            drive.autoDrive(Drive.Mode.FORWARD, 7.0, 0.9);
            intake.openClaw();
            while (opModeIsActive() && drive.isBusy()) {
                telemetry.addData("Sample 1 Collect", "Driving FORWARD 7 in");
                telemetry.update();
            }

            // Step 4) Close claw (3s)
            intake.closeClaw();
            clawTimer.reset();
            while (opModeIsActive() && clawTimer.milliseconds() < 2000) {
                telemetry.addData("Sample 1 Collect", "Closing claw...");
                telemetry.update();
            }
            telemetry.addData("Sample 1 Collect", "Done!");
            telemetry.update();


            /*
             * -------------------------------
             *  SAMPLE 1 DEPOSIT
             * -------------------------------
             * Steps:
             *   1) Move basket + linear slide to deposit
             *   2) Open claw (2s)
             */

            // Step 1) Move basket to "collect sample" position & move arm to deposit
            longArm.basketCollectSample();
            intake.autoMoveArm(Intake.Mode.DEPOSIT);
            while (opModeIsActive() && intake.isArmBusy()) {
                telemetry.addData("Sample 1 Deposit", "Moving Arm to Deposit");
                telemetry.update();
            }

            // Step 2) Open claw (2s)
            intake.openClaw();
            clawTimer.reset();
            while (opModeIsActive() && clawTimer.milliseconds() < 2000) {
                telemetry.addData("Sample 1 Deposit", "Opening Claw...");
                telemetry.update();
            }
            intake.stopClaw();
            telemetry.addData("Sample 1 Deposit", "Done!");
            telemetry.update();


            /*
             * -------------------------------
             *  SAMPLE 1 SCORE
             * -------------------------------
             * Steps:
             *   1) Turn RIGHT 9 inches
             *   2) Drive BACKWARD 7 inches
             *   3) Arm to COLLECT, lift linear slide
             *   4) Move basket to score sample (3s)
             */

            // Step 1) Turn RIGHT 9 inches
            drive.autoDrive(Drive.Mode.TURNRIGHT, 9, 0.5);
            while (opModeIsActive() && drive.isBusy()) {
                telemetry.addData("Sample 1 Score", "Turning RIGHT");
                telemetry.update();
            }

            // Step 2) Drive BACKWARD 7 inches
            drive.autoDrive(Drive.Mode.BACKWARD, 7, 0.9);
            while (opModeIsActive() && drive.isBusy()) {
                telemetry.addData("Sample 1 Score", "Driving BACKWARD 7 in");
                telemetry.update();
            }

            // Step 3) Move Arm to COLLECT, lift linear slide
            intake.autoMoveArm(Intake.Mode.COLLECT);
            longArm.autoLiftLinearSlide();
            while (opModeIsActive() && (intake.isArmBusy() || longArm.isLinearSlideBusy())) {
                telemetry.addData("Sample 1 Score", "Moving Arm + Slide");
                telemetry.update();
            }

            // Step 4) Move basket to score sample (3s)
            longArm.basketScoreSample();
            basketTimer.reset();
            while (opModeIsActive() && basketTimer.milliseconds() < 3000) {
                telemetry.addData("Sample 1 Score", "Scoring sample...");
                telemetry.update();
            }
            telemetry.addData("Sample 1 Score", "Done!");
            telemetry.update();


            /*
             * -------------------------------
             *  COMPLETED / STOP
             * -------------------------------
             */
            drive.stop();
            intake.stopArm();
            intake.stopClaw();
            longArm.stopLinearSlide();

            telemetry.addData("Completed", "All tasks finished");
            telemetry.update();

            // Optionally sleep a bit to see the final telemetry
            sleep(1000);
        }
    }
}
