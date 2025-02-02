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

    private double CLAW_COLLECT_TIME = 5.0;

    private double CLAW_RELEASE_TIME = 5.0;

    private int GAMEPIECE_SCORED = 0;



    /// Necessary objects and variable creation --------------------------------------------------

    // Creating an object from ElapsedTime class to have run time information
    private ElapsedTime runtime = new ElapsedTime();

    // Creating objects from Drive_V1, Intake_v1, and LongArm_v2 class

    Drive drive   = new Drive();
    Intake intake  = new Intake();
    LongArm linearSlide = new LongArm();

    int task = 1;

    boolean taskproformed = false;

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
        switch (task){
            case 1:
                drive.autoDrive(Drive.Mode.LEFT,10,0.9);
                while (drive.isBusy()){
                    if (!taskproformed){
                    telemetry.addData("Robot is moving left:", "Wait");
                    taskproformed = true;
                    }
                }

                drive.autoDrive(Drive.Mode.BACKWARD,5,0.9);
                while (drive.isBusy()) {
                    if (!taskproformed) {
                        telemetry.addData("Robot is moving left:", "Wait");
                        taskproformed = true;
                    }
                }
                //task = 2;
                break;

                /*
            case 2:
                drive.autoDrive(Drive.Mode.RIGHT,10,0.9);
                while (drive.isBusy()){
                    if (!taskproformed){
                    telemetry.addData("Robot is while moving 2:", "Wait");
                        taskproformed = true;
                    }
                }
                task = 1;
                break;

                 */
            default:
                break;
        }
    }
}