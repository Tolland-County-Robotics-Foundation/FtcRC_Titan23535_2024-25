package org.firstinspires.ftc.teamcode.TestAutonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Mechanisms_Final.Drive;
import org.firstinspires.ftc.teamcode.Mechanisms_Final.Intake;
import org.firstinspires.ftc.teamcode.Mechanisms_Final.LongArm;

@Autonomous(name="Auto Test 2", group="Autonomous")

public class AutoTest3 extends LinearOpMode {


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
        }
    }
}
