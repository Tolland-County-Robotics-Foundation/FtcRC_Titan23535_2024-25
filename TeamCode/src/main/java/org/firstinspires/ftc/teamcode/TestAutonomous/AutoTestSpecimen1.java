package org.firstinspires.ftc.teamcode.TestAutonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Mechanisms_Final.Drive;
import org.firstinspires.ftc.teamcode.Mechanisms_Final.Intake;
import org.firstinspires.ftc.teamcode.Mechanisms_Final.LongArm;

@Autonomous(name="Auto Test Specimen 1", group="Autonomous")

public class AutoTestSpecimen1 extends LinearOpMode {


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

            // Drive forward 17 inches
            drive.autoDrive(Drive.Mode.FORWARD, 17, 0.9);
            while (opModeIsActive() && drive.isBusy()) {
                telemetry.addData("Drive:", "Forward 17 inches");
                telemetry.update();
            }

            // Raise intake to half way down
            intake.autoMoveArm(Intake.Mode.HANG);
            while (opModeIsActive() && intake.isArmBusy()) {
                telemetry.addData("Intake: ", "Move");
                telemetry.update();
            }

            // Drive backward 16 inches
            drive.autoDrive(Drive.Mode.FORWARD, 16, 0.9);
            while (opModeIsActive() && drive.isBusy()) {
                telemetry.addData("Drive:", "Backward 16 inches");
                telemetry.update();
            }

            // Drive right 50 inches
            drive.autoDrive(Drive.Mode.RIGHT, 9, 0.9);
            while (opModeIsActive() && drive.isBusy()) {
                telemetry.addData("Drive:", "Right 50 inches");
                telemetry.update();
            }
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
