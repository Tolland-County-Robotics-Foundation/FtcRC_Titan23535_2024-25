package org.firstinspires.ftc.teamcode.TestAutonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Drive_Mechanisms.Drive_v2;
import org.firstinspires.ftc.teamcode.Intake_Arm_Mechanisms.Intake_v2;
import org.firstinspires.ftc.teamcode.LongArm_Mechanisms.LongArm_v3;

@Autonomous

public class Windsor_AutoTest2 extends OpMode {
    enum State {
        START,
        SCORE,
        MOVE_COLLECT_POSITION,
        COLLECT_GAMEPIECE,
        STORE_GAMEPIECE,
        MOVE_SCORE_POSITION,
        PARK,
        STOP
    }

    // Create a timer for the intake claw

    ElapsedTime clawTimer = new ElapsedTime();

    private double CLAW_COLLECT_TIME = 5.0;

    private double CLAW_RELEASE_TIME = 5.0;

    private int GAMEPIECE_SCORED = 0;

    State state = State.START;

    /// Necessary objects and variable creation --------------------------------------------------

    // Creating an object from ElapsedTime class to have run time information
    private ElapsedTime runtime = new ElapsedTime();

    // Creating objects from Drive_V1, Intake_v1, and LongArm_v2 class

    Drive_v2 drive   = new Drive_v2();
    Intake_v2 intake  = new Intake_v2();
    LongArm_v3 linearSlide = new LongArm_v3();



    @Override
    public void init()
    {
        // Resting runtime
        runtime.reset();
        clawTimer.reset();

        /// Initialization ------------------------------------------------------------------------

        // Using "init" method of each class

        drive.init(hardwareMap);
        intake.init(hardwareMap);
        linearSlide.init(hardwareMap);

        /// Telemetry -----------------------------------------------------------------------------

        // Wait for the game to start (driver presses PLAY)
        telemetry.addData("Status", "Initialized");

    }
    @Override
    public void loop()
    {
        drive.autoDrive(Drive_v2.Mode.FORWARD, 10.0, 0.9);

        if (clawTimer.seconds() > 2 && clawTimer.seconds() <4){
            intake.moveIntakeArm(-0.5);
        }

        telemetry.addData("Claw Time: ", clawTimer);

    }
}