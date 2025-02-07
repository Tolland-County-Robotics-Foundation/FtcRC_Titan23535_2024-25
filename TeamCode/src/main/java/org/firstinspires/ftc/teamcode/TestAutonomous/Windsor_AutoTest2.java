package org.firstinspires.ftc.teamcode.TestAutonomous;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Mechanisms_Final.Drive;
import org.firstinspires.ftc.teamcode.Mechanisms_Final.Intake;
import org.firstinspires.ftc.teamcode.Mechanisms_Final.LongArm;


@Autonomous

public class Windsor_AutoTest2 extends OpMode {

    // Create a timer for the intake claw

    ElapsedTime clawTimer = new ElapsedTime();
    ElapsedTime basketTimer = new ElapsedTime();

    private double CLAW_COLLECT_TIME = 5.0;

    private double CLAW_RELEASE_TIME = 5.0;

    private int GAMEPIECE_SCORED = 0;

    private boolean step1Initialized = false;
    private boolean step2Initialized = false;
    private boolean step3Initialized = false;
    private boolean step4Initialized = false;



    /// Necessary objects and variable creation --------------------------------------------------

    // Creating an object from ElapsedTime class to have run time information
    private ElapsedTime runtime = new ElapsedTime();

    // Creating objects from Drive_V1, Intake_v1, and LongArm_v2 class

    Drive drive   = new Drive();
    Intake intake  = new Intake();
    LongArm longArm = new LongArm();

    int task = 1;

    boolean taskproformed = false;

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
        switch (task){
            case 1:
                if (!step1Initialized) {
                    drive.autoDrive(Drive.Mode.LEFT,10,0.9);
                    intake.autoMoveArm(Intake.Mode.COLLECT);
                    longArm.autoLiftLinearSlide();
                    longArm.basketCollectSample();

                    step1Initialized = true;
                    telemetry.addData("Case 1: ", "Started");
                }

                if (!drive.isBusy() && !intake.isArmBusy() && !longArm.isLinearSlideBusy()) {
                    task = 2;
                } else {
                    telemetry.addData("Case 1: ","Working");
                }
                break;
            case 2:
                if (!step2Initialized) {
                    longArm.basketScoreSample();
                    drive.autoDrive(Drive.Mode.BACKWARD,5,0.9);
                    basketTimer.reset();
                    step2Initialized = true;
                    telemetry.addData("Case 2: ","Started");
                }

                if (basketTimer.milliseconds() > 3000) {
                    telemetry.addData("Case 2:", "Done");
                    task = 3;
                } else {
                    telemetry.addData("Case 2:", "Basket is moving to score");
                }
                break;
            case 3:
                if (!step3Initialized) {
                    longArm.basketCollectSample();
                    longArm.autoResetLinearSlide();
                    drive.autoDrive(Drive.Mode.TURNLEFT, 9,0.5);

                    step3Initialized = true;
                    telemetry.addData("Case 3: ", "Started");
                }
                if (!drive.isBusy()) {
                    task = 4;
                } else {
                    telemetry.addData("Case 3: ","Working");
                }
                break;
            case 4:
                if (!step4Initialized) {
                    drive.autoDrive(Drive.Mode.FORWARD,7,0.9);
                    intake.closeClaw();

                    step4Initialized = true;
                    telemetry.addData("Case 4:", "Started");
                }

                if (!drive.isBusy()) {
                    clawTimer.reset();

                    if (clawTimer.milliseconds() > 2000) {
                        task = 5;
                    }
                } else {
                    telemetry.addData("Case 4: ","Working");
                }

                break;
            case 5:
                break;


            default:
                telemetry.addData("Done", " No more tasks");
                drive.stop();
                intake.stopClaw();
                intake.stopArm();
                longArm.stopLinearSlide();
                break;
        }
        telemetry.update();
    }
}