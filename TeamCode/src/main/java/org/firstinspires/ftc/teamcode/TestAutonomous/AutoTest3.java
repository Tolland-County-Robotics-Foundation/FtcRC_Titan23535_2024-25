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

    Pose sample0Pose = new Pose(-8.5, 5, 45);

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

            drive.autoDrivePose(sample0Pose, 0.5);
            longArm.basketReset();

            while (opModeIsActive() && drive.isBusy()) {
                telemetry.addData("Sample 0 Score", "Driving to sample 0 score");
                telemetry.update();
            }

            drive.stop();
            intake.autoMoveArm(Intake.Mode.COLLECT);
            longArm.basketReset();
            longArm.autoLiftLinearSlide();
            while (opModeIsActive() && (intake.isArmBusy() || longArm.isLinearSlideBusy())) {
                telemetry.addData("Sample 0 Score", "Moving Arm & Linear Slide");
                telemetry.update();
            }

            drive.stop();
            intake.stopArm();
            longArm.stopLinearSlide();
            telemetry.update();


        }
    }
}
