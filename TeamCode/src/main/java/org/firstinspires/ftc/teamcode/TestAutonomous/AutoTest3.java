package org.firstinspires.ftc.teamcode.TestAutonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Mechanisms_Final.Drive;
import org.firstinspires.ftc.teamcode.Mechanisms_Final.Intake;
import org.firstinspires.ftc.teamcode.Mechanisms_Final.LongArm;
import org.firstinspires.ftc.teamcode.Mechanisms_Final.Pose;

@Autonomous(name="Auto Test 3", group="Autonomous")

public class AutoTest3 extends LinearOpMode {


    // Hardware
    private Drive   drive   = new Drive();
    private Intake  intake  = new Intake();
    private LongArm longArm = new LongArm();

    // Timers
    private ElapsedTime runtime     = new ElapsedTime();
    private ElapsedTime clawTimer   = new ElapsedTime();
    private ElapsedTime basketTimer = new ElapsedTime();

    Pose sample1ScorePose = new Pose(-8.75, -6, 0);
    Pose sample2CollectPose = new Pose(4.5, 1.75, -35);
    Pose sample2DepositPose = new Pose(-4.5, 2, 18);

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

        if (opModeIsActive() && runtime.seconds() <=30) {

            /*
             * -------------------------------
             *  MOVING TO SAMPLE 1 SCORE
             * -------------------------------
             * Steps:
             *   1) Drive robot to sample 1 scoring position
             *   2) Move basket to reset position (it should be in this position at the beginning)
             */

            drive.autoDrivePose(sample1ScorePose, 0.5);
            longArm.basketReset();

            while (drive.isBusy()) {
                telemetry.addData("Sample 1: ", "MOVING TO SAMPLE 1 SCORE");
                telemetry.addData("Drive busy: ", drive.isBusy());
                telemetry.update();
            }
            /*
             * -------------------------------
             *  SAMPLE 1 SCORE
             * -------------------------------
             * Steps:
             *   1) Move intake arm to collect position
             *   2) Lift the linear slide
             *   3) Move basket to score position after lift linear slide complete
             */

            intake.autoMoveArm(Intake.Mode.COLLECT);
            longArm.autoLiftLinearSlide();

            while (longArm.isLinearSlideBusy()) {
                telemetry.addData("Linear Slide busy: ", longArm.isLinearSlideBusy());
                telemetry.addData("Sample 1: ", "Lifting linear slide");
            }
            longArm.basketScoreSample();
            basketTimer.reset();
            while (basketTimer.milliseconds() < 1500) {
                telemetry.addData("Sample 1: ", "Scoring");
                telemetry.update();
            }
            /*
             * -------------------------------
             *  Reset
             * -------------------------------
             * Steps:
             *   1) Reset basket
             *   2) Drop the linear slide
             */

            longArm.basketReset();
            longArm.autoCollectLinearSlide();

            while (longArm.isLinearSlideBusy()) {
                telemetry.addData("Linear Slide busy: ", longArm.isLinearSlideBusy());
                telemetry.addData("Reset: ", "Long Arm");
                telemetry.update();
                intake.openClaw();
            }
            /*
             * -------------------------------
             *  MOVING TO SAMPLE 2 COLLECT
             * -------------------------------
             * Steps:
             *   1) Drive robot to sample 2 COLLECT position
             */

            drive.autoDrivePose(sample2CollectPose, 0.5);

            while (drive.isBusy()) {
                telemetry.addData("Driving: ", "SAMPLE 2 COLLECT");
                telemetry.addData("Drive busy: ", drive.isBusy());
                telemetry.update();
                intake.stopClaw();
            }

            /*
             * -------------------------------
             *  SAMPLE 2 COLLECT
             * -------------------------------
             * Steps:
             *   1) Close claw for 2 seconds
             */

            intake.closeClaw();
            clawTimer.reset();
            while (clawTimer.milliseconds() < 1500) {
                telemetry.addData("Collecting: ", "Sample 2");
                telemetry.update();
            }

            /*
             * -------------------------------
             *  SAMPLE 2 DEPOSIT
             * -------------------------------
             * Steps:
             *   1) Stop claw after 2 seconds
             *   2) Move intake arm to deposit position
             *   3) Move basket to collect position
             *   4) Drive to score sample 2
             */

            intake.stopClaw();
            intake.autoMoveArm(Intake.Mode.DEPOSIT);
            longArm.basketCollectSample();
            drive.autoDrivePose(sample2DepositPose, 0.5);

            while (intake.isArmBusy()) {
                telemetry.addData("Deposit: ", "Sample 2");
            }
            intake.openClaw();

            clawTimer.reset();
            while (clawTimer.milliseconds() < 2000) {
                telemetry.addData("Collecting: ", "Sample 2");
                telemetry.update();
            }
            intake.stopClaw();

         /*   while (drive.isBusy()) {
                telemetry.addData("Driving: ", "Deposit 2 position");
                telemetry.update();
            }

          */

            /*
             * -------------------------------
             *  SAMPLE 2 SCORE
             * -------------------------------
             * Steps:
             *   1) Move intake arm to collect position
             *   2) Move basket to reset position
             *   3) Lift linear slide
             *   4)
             *
             */

            intake.autoMoveArm(Intake.Mode.COLLECT);
            longArm.basketReset();
            longArm.autoLiftLinearSlide();

            while (longArm.isLinearSlideBusy()) {
                telemetry.addData("Linear Slide busy: ", longArm.isLinearSlideBusy());
                telemetry.addData("Sample 2: ", "Lifting linear slide");
                telemetry.update();
            }

            longArm.basketScoreSample();

            telemetry.addData("Mission: ","Complete");
            telemetry.update();

        }
        else {
            telemetry.addData("Out of time:", "Boom");
        }

    }
}
