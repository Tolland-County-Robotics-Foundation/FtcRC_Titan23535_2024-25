package org.firstinspires.ftc.teamcode.TestAutonomous;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Mechanisms_Final.Drive;
import org.firstinspires.ftc.teamcode.Mechanisms_Final.Intake;
import org.firstinspires.ftc.teamcode.Mechanisms_Final.LongArm;


@Autonomous

public class Windsor_AutoSpecaminTest1 extends OpMode {

    private boolean autonomousMovementStart = false;

    private enum Events {
        Sample0Score, Sample1Collect, Sample1Deposit, Sample1Score, Completed
    }

    private Events events = Events.Sample0Score;
    private boolean sample0ScoreInitialized = false;
    private boolean sample1CollectInitialized = false;
    private boolean sample1ScoreInitialized = false;

    int sample0ScoreSteps = 0;
    int sample1CollectSteps = 0;
    int sample1DepositSteps = 0;
    int sample1ScoreSteps = 0;

    private boolean[] initializedSample0ScoreStep = {false, false, false, false};
    private boolean[] initializedSample1CollectStep = {false, false, false, false};
    private boolean[] initializedSample1DepositStep = {false, false, false, false};
    private boolean[] initializedSample1ScoreStep = {false, false, false, false};

    // Create a timer for the intake claw

    ElapsedTime clawTimer = new ElapsedTime();
    ElapsedTime basketTimer = new ElapsedTime();

    private double CLAW_COLLECT_TIME = 5.0;

    private double CLAW_RELEASE_TIME = 5.0;


    /// Necessary objects and variable creation --------------------------------------------------

    // Creating an object from ElapsedTime class to have run time information
    private ElapsedTime runtime = new ElapsedTime();

    // Creating objects from Drive_V1, Intake_v1, and LongArm_v2 class

    Drive drive   = new Drive();
    Intake intake  = new Intake();
    LongArm longArm = new LongArm();

    @Override
    public void init()
    {
        // Resting runtime
        runtime.reset();
        clawTimer.reset();
        basketTimer.reset();

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
        switch (events){
            case Sample0Score:{
                /*
                    Sample 0 Score Steps:

                        Step 0: Drive left 10 inches
                        Step 1: Drive backward 5 inches
                        Step 2: Move intake arm to collect position and lift linear slide
                        Step 3: Move basket to score sample
                 */

                switch (sample0ScoreSteps){
                    case 0:{
                        if (!initializedSample0ScoreStep[0]){
                            drive.autoDrive(Drive.Mode.LEFT,10,0.9);
                            initializedSample0ScoreStep[0] = true;
                        }

                        if (!drive.isBusy()){ sample0ScoreSteps = 1; }
                        else { telemetry.addData("Sample 0 Score: ", "Step 0"); }
                    } break;

                    case 1:{
                        if (!initializedSample0ScoreStep[1]){
                            drive.autoDrive(Drive.Mode.BACKWARD,5,0.9);
                            initializedSample0ScoreStep[1] = true;
                        }

                        if (!drive.isBusy()){ sample0ScoreSteps = 2; }
                        else { telemetry.addData("Sample 0 Score: ", "Step 1"); }
                    } break;

                    case 2:{
                        if (!initializedSample0ScoreStep[2]){
                            intake.autoMoveArm(Intake.Mode.COLLECT);
                            longArm.autoLiftLinearSlide();
                            initializedSample0ScoreStep[2] = true;
                        }

                        if (!intake.isArmBusy() && !longArm.isLinearSlideBusy()) {sample0ScoreSteps = 3; }
                        else { telemetry.addData("Sample 0 Score: ", "Step 2"); }

                    }break;

                    case 3:{
                        if (!initializedSample0ScoreStep[3]){
                            longArm.basketScoreSample();
                            basketTimer.reset();
                            initializedSample0ScoreStep[3] = true;
                        }

                        if (basketTimer.milliseconds() > 2500) {sample0ScoreSteps = 4; }
                        else { telemetry.addData("Sample 0 Score: ", "Step 3"); }

                    }break;

                    default:
                        telemetry.addData("Sample 0 Score: ", "Done");
                        events = Events.Sample1Collect;
                        break;
                }

            } break;
            case Sample1Collect:{
                /*
                    Sample 1 Collect Steps:

                        Step 0: Reset basket and linear slide
                        Step 1: Rotate the robot left 9 inches
                        Step 2: Drive the robot forward 7 inches
                        Step 3: Close claw to collect sample 1
                 */

                switch (sample1CollectSteps){

                    case 0:{
                        if (!initializedSample1CollectStep[0]){
                            longArm.basketReset();
                            longArm.autoResetLinearSlide();
                            initializedSample1CollectStep[0] = true;
                        }

                        if (!longArm.isLinearSlideBusy()) { sample1CollectSteps = 1; }
                        else { telemetry.addData("Sample 1 Collect: ", "Step 1"); }
                    } break;

                    case 1:{
                        if (!initializedSample1CollectStep[1]){
                            drive.autoDrive(Drive.Mode.TURNLEFT,9.0,0.5);
                            intake.openClaw();
                            initializedSample1CollectStep[1] = true;
                        }

                        if (!drive.isBusy()) { sample1CollectSteps = 2; }
                        else { telemetry.addData("Sample 1 Collect: ", "Step 2"); }
                    }break;

                    case 2:{
                        if (!initializedSample1CollectStep[2]){
                            drive.autoDrive(Drive.Mode.FORWARD,7.0,0.9);
                            intake.closeClaw();
                            initializedSample1CollectStep[2] = true;
                        }

                        if (!drive.isBusy()) { sample1CollectSteps = 3; }
                        else { telemetry.addData("Sample 1 Collect: ", "Step 3"); }
                    }break;

                    case 3:{
                        if (!initializedSample1CollectStep[3]){
                            intake.closeClaw();
                            clawTimer.reset();
                            initializedSample1CollectStep[3] = true;
                        }

                        if (clawTimer.milliseconds() > 3000) { sample1CollectSteps = 4; }
                        else { telemetry.addData("Sample 1 Collect: ", "Step 4"); }
                    }break;
                    default:
                        telemetry.addData("Sample 1 Collect: ", "Done");
                        events = Events.Sample1Deposit;
                        break;
                }

            }break;

            case Sample1Deposit:{
                /*
                    Sample 1 Deposit Steps:

                        Step 0: Move basket and linear slide to deposit position
                        Step 1: Deposit sample
                 */

                switch (sample1DepositSteps){
                    case 0:{
                        if (!initializedSample1DepositStep[0]){
                            longArm.basketCollectSample();
                            intake.autoMoveArm(Intake.Mode.DEPOSIT);
                        }

                        if (!intake.isArmBusy()){ sample1DepositSteps = 1; }
                        else { telemetry.addData("Sample 1 Deposit: ", "Step 0"); }
                    }break;

                    case 1:{
                        if (!initializedSample1DepositStep[1]){
                            intake.openClaw();
                            clawTimer.reset();
                        }

                        if (clawTimer.milliseconds() > 2000) {
                            intake.stopClaw();
                            sample1DepositSteps = 2;
                        }
                        else { telemetry.addData("Sample 1 Deposit: ", "Step 1"); }

                    }break;

                    default:
                        telemetry.addData("Sample 1 Deposit: ", "Done");
                        events = Events.Sample1Score;
                        break;
                }

            }break;

            case Sample1Score:{
                /*
                    Sample 1 Score Steps:

                        Step 0: Rotate the robot right 9 inches
                        Step 1: Drive backward 7 inches
                        Step 2: Move intake arm to collect position and lift linear slide
                        Step 3: Move basket to score sample
                 */

                switch (sample1ScoreSteps){
                    case 0:{
                        if (!initializedSample1ScoreStep[0]){
                            drive.autoDrive(Drive.Mode.TURNRIGHT,9,0.5);
                            initializedSample1ScoreStep[0] = true;
                        }

                        if (!drive.isBusy()){ sample1ScoreSteps = 1; }
                        else { telemetry.addData("Sample 1 Score: ", "Step 0"); }
                    } break;

                    case 1:{
                        if (!initializedSample1ScoreStep[1]){
                            drive.autoDrive(Drive.Mode.BACKWARD,7,0.9);
                            initializedSample1ScoreStep[1] = true;
                        }

                        if (!drive.isBusy()){ sample1ScoreSteps = 2; }
                        else { telemetry.addData("Sample 1 Score: ", "Step 1"); }
                    } break;

                    case 2:{
                        if (!initializedSample1ScoreStep[2]){
                            intake.autoMoveArm(Intake.Mode.COLLECT);
                            longArm.autoLiftLinearSlide();
                            initializedSample1ScoreStep[2] = true;
                        }

                        if (!intake.isArmBusy() && !longArm.isLinearSlideBusy()) {sample1ScoreSteps = 3; }
                        else { telemetry.addData("Sample 1 Score: ", "Step 2"); }

                    }break;

                    case 3:{
                        if (!initializedSample1ScoreStep[3]){
                            longArm.basketScoreSample();
                            basketTimer.reset();
                            initializedSample1ScoreStep[3] = true;
                        }

                        if (basketTimer.milliseconds() > 3000) {sample1ScoreSteps = 4; }
                        else { telemetry.addData("Sample 1 Score: ", "Step 3"); }

                    }break;

                    default:
                        telemetry.addData("Sample 1 Score: ", "Done");
                        events = Events.Completed;
                        break;
                }


            }break;

            case Completed:{
                drive.stop();
                intake.stopArm();
                intake.stopClaw();
                longArm.stopLinearSlide();
                telemetry.addData("Completed: ", "All tasks");
            }break;

            default:
                break;
        }

        telemetry.update();
    }
}