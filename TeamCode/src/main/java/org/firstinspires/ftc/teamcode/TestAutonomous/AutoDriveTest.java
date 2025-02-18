package org.firstinspires.ftc.teamcode.TestAutonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Mechanisms_Final.Drive;
import org.firstinspires.ftc.teamcode.Mechanisms_Final.Intake;
import org.firstinspires.ftc.teamcode.Mechanisms_Final.LongArm;
import org.firstinspires.ftc.teamcode.Mechanisms_Final.Pose;

@Autonomous(name="Auto Drive Test", group="Autonomous")

public class AutoDriveTest extends LinearOpMode {


    // Hardware
    private Drive   drive   = new Drive();
    private Intake  intake  = new Intake();
    private LongArm longArm = new LongArm();

    // Timers
    private ElapsedTime runtime     = new ElapsedTime();
    private ElapsedTime clawTimer   = new ElapsedTime();
    private ElapsedTime basketTimer = new ElapsedTime();

    Pose sample0ScorePose = new Pose(5, 0, 0);
    Pose sample1CollectPose = new Pose(0, 5, 0);
    Pose sample2CollectPose = new Pose(0, 0, 45);

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

           drive.autoDrivePose(sample0ScorePose,0.9);
           while(drive.isBusy()) {
               telemetry.addData("Moving To Sample 0","");
           }
           drive.autoDrivePose(sample1CollectPose,0.9);
            while(drive.isBusy()) {
                telemetry.addData("Moving To Sample 1","");
            }
            drive.autoDrivePose(sample2CollectPose,0.9);
            while(drive.isBusy()) {
                telemetry.addData("Moving To Sample 2","");
            }
        }
    }
}
