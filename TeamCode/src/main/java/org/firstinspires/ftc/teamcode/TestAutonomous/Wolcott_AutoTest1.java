package org.firstinspires.ftc.teamcode.TestAutonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Drive_Mechanisms.Drive_v2;
import org.firstinspires.ftc.teamcode.Intake_Arm_Mechanisms.Intake_v2;
import org.firstinspires.ftc.teamcode.LongArm_Mechanisms.LongArm_v3;

@Autonomous

public class Wolcott_AutoTest1 extends OpMode {
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
    LongArm_v3 longArm = new LongArm_v3();



    @Override
    public void init()
    {
        // Resting runtime
        runtime.reset();

        /// Initialization ------------------------------------------------------------------------

        // Using "init" method of each class

        drive.init(hardwareMap);
        intake.init(hardwareMap);
        longArm.init(hardwareMap);

        /// Telemetry -----------------------------------------------------------------------------

        // Wait for the game to start (driver presses PLAY)
        telemetry.addData("Status", "Initialized");

    }
    @Override
    public void loop()
    {
        telemetry.addData("State: ", state);

        switch (state){

            case START:

                drive.autoDrive(Drive_v2.Mode.FORWARD, 10, 0.5);
                drive.autoDrive(Drive_v2.Mode.LEFT, 15, 0.5);
                drive.autoDrive(Drive_v2.Mode.TURNLEFT, 45, 0.5);
                longArm.autoLiftArm();
                state = State.SCORE;

                break;
/*
            case MOVE_COLLECT_POSITION:

                longArm.autoResetArm();
                longArm.basketReset();
                intake.autoMove("collect", 10, 0.5);
                if (GAMEPIECE_SCORED == 1) {
                    drive.autoDrive(Drive_v2_shafiq.Mode.TURNLEFT, 5, 0.5);
                    drive.autoDrive(Drive_v2_shafiq.Mode.FORWARD, , );
                    drive.autoDrive(Drive_v2_shafiq.Mode.RIGHT, , );
                } else if (GAMEPIECE_SCORED == 2) {
                    drive.autoDrive("turnLeft", 5, 0.5);
                    drive.autoDrive("forward", , );
                    drive.autoDrive("right", , );
                } else if (GAMEPIECE_SCORED == 3) {
                    drive.autoDrive("turnLeft", 5, 0.5);
                    drive.autoDrive("forward", , );
                    drive.autoDrive("right", , );
                }
                state = State.COLLECT_GAMEPIECE;
                clawTimer.reset();

                break;

            case COLLECT_GAMEPIECE:

                intake.closeClaw();

                if (clawTimer.seconds() >= CLAW_COLLECT_TIME){
                    state = State.STORE_GAMEPIECE;
                    clawTimer.reset();
                }

                break;

            case STORE_GAMEPIECE:

                intake.moveIntakeArm("release",);

                intake.openClaw();

                if (clawTimer.seconds() >= CLAW_RELEASE_TIME){
                    state = State.MOVE_SCORE_POSITION;
                }

                break;

            case MOVE_SCORE_POSITION:

                if (GAMEPIECE_SCORED == 1){
                    drive.autoDrive("forward", 10, 0.5);
                } else if (GAMEPIECE_SCORED == 2) {

                    
                } else if (GAMEPIECE_SCORED == 3) {
                    
                }
                longArm.autoLiftArm();
                state = State.SCORE;

            case SCORE:

                longArm.scoreGamePiece();
                GAMEPIECE_SCORED++;

                if (GAMEPIECE_SCORED < 4){
                    state = State.MOVE_COLLECT_POSITION;
                }else {
                    state = State.PARK;
                }

            case PARK:
                drive.autoDrive("forward", 45 ,0.8);
                drive.autoDrive("turnRight", 45,0.5);

            case STOP:

                drive.stop();
                intake.stopArm();
                intake.stopClaw();
                longArm.stopArm();

                break;

 */
            default:
                telemetry.addData("Finished: ", "Yes");

                break;

        }

    }

}
