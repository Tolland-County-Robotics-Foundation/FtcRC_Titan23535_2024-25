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

    Pose sample0ScorePose = new Pose(-10, 5, 0);
    Pose sample1CollectPose = new Pose(1, 1, -45);

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
             *  MOVING TO SAMPLE 0 SCORE
             * -------------------------------
             * Steps:
             *   1) Drive robot to sample 0 scoring position
             *   2) Move linear slide to collect position
             *   3) Move basket to reset position (it should be in this position at the beginning)
             *   4) Lift the linear slide
             */

            drive.autoDrivePose(sample0ScorePose, 0.9);
            intake.autoMoveArm(Intake.Mode.COLLECT);
            longArm.basketReset();
            longArm.autoLiftLinearSlide();

            while (drive.isBusy() || intake.isArmBusy() || longArm.isLinearSlideBusy()) {
                telemetry.addData("Sample 0: ", "Driving to score position");
                telemetry.addData("Drive busy: ", drive.isBusy());
                telemetry.addData("Intake busy: ", intake.isArmBusy());
                telemetry.addData("Long Arm busy: ", longArm.isLinearSlideBusy());
                telemetry.update();
            }

            drive.stop();
            intake.stopArm();
            longArm.stopLinearSlide();

            /*
             * -------------------------------
             *  SAMPLE 0 SCORE
             * -------------------------------
             * Steps:
             *   1) Move basket to score position
             */


            longArm.basketScoreSample();
            basketTimer.reset();
            while (basketTimer.milliseconds() < 2000) {
                telemetry.addData("Sample 0: ", "Scoring");
                telemetry.update();
            }

            /*
             * -------------------------------
             *  MOVING TO SAMPLE 1 COLLECT
             * -------------------------------
             * Steps:
             *   1) Drive robot to sample 1 collect position
             *   2) Drop linear slide
             *   3) Move basket to collect
             */

            drive.autoDrivePose(sample1CollectPose, 0.9);
            longArm.autoResetLinearSlide();
            longArm.basketCollectSample();

            while (drive.isBusy() || longArm.isLinearSlideBusy()) {
                telemetry.addData("Sample 1: ", "Driving to sample 1 collect");
                telemetry.update();
            }

            drive.stop();
            longArm.stopLinearSlide();

            /*
             * -------------------------------
             *  SAMPLE 1 COLLECT
             * -------------------------------
             * Steps:
             *   1) Close claw for 2 seconds
             */



            clawTimer.reset();
            while (clawTimer.milliseconds() < 2000) {
                intake.closeClaw();
            }

            /*
             * -------------------------------
             *  MOVING TO SAMPLE 1 SCORE
             * -------------------------------
             * Steps:
             *   1) Drive robot to sample 1 scoring position
             *   2) Move linear slide to deposit position
             *   3) Move basket to reset position (it should be in this position at the beginning)
             *   4) Lift the linear slide
             */

            intake.autoMoveArm(Intake.Mode.DEPOSIT);

        }
    }
}
