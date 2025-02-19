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

    Pose sample0ScorePose = new Pose(-8, -5, 0);
    Pose sample1CollectPose = new Pose(4.5, -3, -23);

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
             *  MOVING TO SAMPLE 0 SCORE
             * -------------------------------
             * Steps:
             *   1) Drive robot to sample 0 scoring position
             *   2) Move basket to reset position (it should be in this position at the beginning)
             */

            drive.autoDrivePose(sample0ScorePose, 0.9);
            longArm.basketReset();

            while (drive.isBusy()) {
                telemetry.addData("Sample 0: ", "MOVING TO SAMPLE 0 SCORE");
                telemetry.addData("Drive busy: ", drive.isBusy());
                telemetry.update();
            }

            // drive.stop();

            /*
             * -------------------------------
             *  SCORING SAMPLE 0
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
                telemetry.addData("Sample 0: ", "Lifting linear slide");
            }
            longArm.basketScoreSample();
            basketTimer.reset();
            while (basketTimer.milliseconds() < 2000) {
                telemetry.addData("Sample 0: ", "Scoring");
                telemetry.update();
            }

            /*
             * -------------------------------
             *  Reset
             * -------------------------------
             * Steps:
             *   1) Reset basket
             *   2) Drop the linear slide
             *
             */

            longArm.basketReset();
            longArm.autoCollectLinearSlide();


            while (longArm.isLinearSlideBusy()) {
                telemetry.addData("Linear Slide busy: ", longArm.isLinearSlideBusy());
                telemetry.addData("Reset: ", "Long Arm");
                telemetry.update();
            }

            /*
             * -------------------------------
             *  MOVING TO SAMPLE 2 COLLECT
             * -------------------------------
             * Steps:
             *   1) Drive robot to sample 2 COLLECT position
             *
             */

            drive.autoDrivePose(sample1CollectPose, 0.3);

            while (drive.isBusy()) {
                telemetry.addData("Driving: ", "SAMPLE 2 COLLECT");
                telemetry.addData("Drive busy: ", drive.isBusy());
                telemetry.update();
            }

            /*
             * -------------------------------
             *  SAMPLE 2 COLLECT
             * -------------------------------
             * Steps:
             *   1) close claw
             *
             */

            intake.closeClaw();
            clawTimer.reset();
            while (clawTimer.milliseconds() < 2000) {
                telemetry.addData("Collecting: ", "Sample 2");
            }

            intake.stopClaw();





            telemetry.addData("Mission: ","Completed");

        }
    }
}
