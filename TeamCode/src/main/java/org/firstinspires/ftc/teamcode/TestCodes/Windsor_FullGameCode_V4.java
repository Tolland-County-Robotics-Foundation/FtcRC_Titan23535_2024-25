// v3 + alliance selection

package org.firstinspires.ftc.teamcode.TestCodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Mechanisms_Final.Climb;
import org.firstinspires.ftc.teamcode.Mechanisms_Final.ColorDistanceSensor;
import org.firstinspires.ftc.teamcode.Mechanisms_Final.Drive;
import org.firstinspires.ftc.teamcode.Mechanisms_Final.Intake;
import org.firstinspires.ftc.teamcode.Mechanisms_Final.LongArm;


@TeleOp(name = "Full Teleop 4", group = "AWindsor")

public class Windsor_FullGameCode_V4 extends OpMode {

    /// Necessary objects and variable creation --------------------------------------------------

    // Creating an object from ElapsedTime class to have run time information
    private ElapsedTime runtime = new ElapsedTime();
    private ElapsedTime hookTimer = new ElapsedTime();

    //Creating two variables for capping the speed
    String speedcap = "Normal";

    double speed_percentage = 50.0;

    String alliance_color = "Not Selected";
    String sample_color = "Not Selected";
    String wrong_sample_color = "Not Selected";


    // Creating objects from Drive_V1, Intake_v1, and LongArm_v2 class

    Climb hook      = new Climb();
    Drive drive     = new Drive();
    Intake intake   = new Intake();
    LongArm longArm = new LongArm();
    ColorDistanceSensor clrSensor = new ColorDistanceSensor();



    @Override
    public void init()
    {
        // Resting runtime
        runtime.reset();
        hookTimer.reset();

        /// Initialization ------------------------------------------------------------------------

        // Using "init" method of each class

        drive.init(hardwareMap);
        intake.init(hardwareMap);
        longArm.init(hardwareMap);
        hook.init(hardwareMap);
        clrSensor.init(hardwareMap);

        /// Telemetry -----------------------------------------------------------------------------

        // Wait for the game to start (driver presses PLAY)
        telemetry.addData("Status", "Initialized");

    }
    @Override
    public void loop()
    {
        /// Button configuration -------------------------------------------------------------------

        // Drive

        /* Uses left joystick to go forward, backward, left, and right, and right joystick to rotate.

           Left joystick up --> forward

           Left joystick down --> Backward

           Left joystick right --> Right

           Left joystick left --> Left

           Right joystick left --> rotate left

           Right joystick right --> rotate right

        */

        double yawButton     =  gamepad1.right_stick_x;
        double axialButton   = -gamepad1.left_stick_y;  // Negative value for pushing stick forward
        double lateralButton =  gamepad1.left_stick_x;

        boolean goForwardButton     = gamepad1.dpad_up;
        boolean goBackwardButton    = gamepad1.dpad_down;
        boolean goLeftButton        = gamepad1.dpad_left;
        boolean goRightButton       = gamepad1.dpad_right;

        // Intake

    /*
        Gamepad 2 left stick y to move the intake arm

        Gamepad 2 right stick x to move the claw
     */

        double intakeArmPower   = gamepad2.left_stick_y * 0.5;
        double intakeClawPower  = gamepad2.right_stick_x;


        // Long Arm

        double linearSlidePower = gamepad2.left_trigger - gamepad2.right_trigger;

        boolean basketScoreButton   = gamepad2.dpad_down;
        boolean basketCollectButton = gamepad2.dpad_up;
        boolean basketResetButton = gamepad2.dpad_right;
        

        // Hook

        boolean hookResetButton = gamepad2.y;
        boolean hookGrabRungButton = gamepad2.a;

        // Alliance color change to red
        boolean allianceRedButton = gamepad1.left_bumper;
        boolean allianceBlueButton = gamepad1.right_bumper;


        /// Mechanisms ------------------------------------------------------------------------

        /// Alliance selection --------------------------------------------------

        if (allianceRedButton) { alliance_color = "red"; wrong_sample_color = "blue"; }
        else if (allianceBlueButton) { alliance_color = "blue"; wrong_sample_color = "red"; }

        /// Color sensor -----------------------------------------------------------------

        sample_color = clrSensor.detectColor();

        /// Drive mechanisms -----------------------------------------------------------------


        // Set the speed cap for driver 1
        if (gamepad1.y)
        {
            speedcap = "Max";
            speed_percentage = 90.0;
        } else if (gamepad1.b)
        {
            speedcap = "Fast";
            speed_percentage = 65.0;
        } else if (gamepad1.x)
        {
            speedcap = "Normal";
            speed_percentage = 40.0;
        } else if (gamepad1.a)
        {
            speedcap = "Slow";
            speed_percentage = 25.0;
        }

        // Control for setting the motors power

        drive.setDriveMotorPower(axialButton, lateralButton, yawButton, speed_percentage);

        // Control for driving straight forward, backward, left and right

        if (goForwardButton) {
            drive.teleOpForward();
        } else if (goBackwardButton) {
            drive.teleOpBackward();
        } else if (goLeftButton) {
            drive.teleOpLeft();
        } else if (goRightButton) {
            drive.teleOpRight();
        }


        /// intake mechanism ----------------------------------------------------------------------

        // Intake arm control

        intake.moveArm(intakeArmPower);

        // Intake claw control

        intake.moveClaw(intakeClawPower);

        if (intakeClawPower == 0 && sample_color == wrong_sample_color) {
            intake.openClaw(); }



        /// Long arm mechanism ------------------------------------------------------------------

        // Linear slide control

        /*

        if      (linearSlideResetButton)    { longArm.autoResetArm();   }
        else if (linearSlideLiftButton)     { longArm.autoLiftArm();    }
        else if (linerSlideStopButton)     { longArm.stopArm();        }

         */

        longArm.moveLinearSlide(linearSlidePower);


        // Basket control

        if (basketScoreButton)          { longArm.scoreSample();    }
        else if (basketCollectButton)   { longArm.collectSample();  }
        else if (basketResetButton)     { longArm.basketReset();    }


        /// Hook Mechanism ---------------------------------------------------------------------

        if (hookGrabRungButton) {
            hookTimer.reset();
            while (hookTimer.seconds() < 2.5) { hook.grabRung(); }
        } else if (hookResetButton) {
            hook.reset();
        } else hook.stop();

        /// Telemetry -----------------------------------------------------------------------------

        //Display Runtime
        telemetry.addData("Alliance: ", alliance_color);
        telemetry.addData("Sample Color: ", clrSensor.detectColor());
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Axial:", axialButton);
        telemetry.addData("Lateral:",lateralButton);
        telemetry.addData("Yaw:", yawButton);
        telemetry.addData("Current Speed Cap", speedcap);
        telemetry.addData("Speed percentage: ",speed_percentage);

        telemetry.addData("Linear slide power: ", linearSlidePower);

        telemetry.addData("intake arm power: ", intakeArmPower);
        telemetry.addData("intake claw power: ", intakeClawPower);

    }
}