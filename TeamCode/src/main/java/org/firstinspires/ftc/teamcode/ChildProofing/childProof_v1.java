/*
    This is the simple TeleOp.
 */

package org.firstinspires.ftc.teamcode.ChildProofing;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Mechanisms_Final.Drive;
import org.firstinspires.ftc.teamcode.Mechanisms_Final.Intake;
import org.firstinspires.ftc.teamcode.Mechanisms_Final.LongArm;


@TeleOp(name = "ChildProofDrive", group = "ChildProof")

public class childProof_v1 extends OpMode {

    /// Necessary objects and variable creation --------------------------------------------------

    // Creating timers
    private ElapsedTime runtime = new ElapsedTime();
    private ElapsedTime linearSlideTimer = new ElapsedTime();
    private ElapsedTime basketTimer = new ElapsedTime();

    //Creating a variable for capping the speed.
    double speed_percentage = 35.0;

    // Creating objects from Drive_V1, Intake_v1, and LongArm_v2 class
    Drive drive     = new Drive();
    Intake intake   = new Intake();
    LongArm longArm = new LongArm();

    // Hook states enums
    private enum HookStates {
        STOP, GRAB, RESET
    }

    private HookStates hookStates = HookStates.STOP;

    @Override
    public void init()
    {
        // Resting runtime
        runtime.reset();
        linearSlideTimer.reset();
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
        /// Button configuration -------------------------------------------------------------------
        double yawButton     =  gamepad1.right_stick_x;
        double axialButton   = -gamepad1.left_stick_y;  // Negative value for pushing stick forward
        double lateralButton =  gamepad1.left_stick_x;


        double intakeArmPower   = gamepad2.right_stick_y * -0.5;
        double intakeClawPower = gamepad2.left_trigger - gamepad2.right_trigger;


        // Long Arm
        /* ---------------------------------------------------------------------
         *
         * Gamepad 2 triggers to control the linear slides
         * Gamepad 2 dpad to control the basket
         */

        double linearSlidePower = gamepad2.left_stick_y * 0.5;

        boolean basketScoreButton   = (gamepad1.a);

        /// Drive Controls -----------------------------------------------------------------------
        /* ----------------------- Drive controls ------------------------------------------------
         * Control 1: Use Gamepad 1 joysticks to control the movement
         * Control 2: Use Gamepad 1 dpad to have one-directional motion
         */

        // Control 1
        drive.setDriveMotorPower(axialButton, lateralButton, yawButton, speed_percentage);

        /// Intake Controls ----------------------------------------------------------------------
        /* ----------------------- Intake Arm Controls --------------------------------------------
         * Control 1: Use Gamepad 2 left stick y to control the movement of intake arm
         */

        intake.moveArm(intakeArmPower);

        /* ----------------------- Intake Claw Controls -------------------------------------------
         * Control 1: Use Gamepad 2 right stick x to control the movement of intake claw
         */

        intake.moveClaw(intakeClawPower);

        /// Long arm Controls ------------------------------------------------------------------

        /* ----------------------- Linear Slides Controls -----------------------------------------
         * Control 1: Use Gamepad 2 triggers to control the movement of the linear slides
         */

        longArm.moveLinearSlide(linearSlidePower);

        /* ----------------------- Basket controls ------------------------------------------------
         * Control 1: Use buttons to set the basket positions
         * Control 2: If linear slide is in motion, basket will move and stay in reset position
         * Control 3: When intake arm move towards the basket, the basket move to collect position
         */

        // Control 1
        if (basketScoreButton)          { longArm.basketScoreSample();      }

        // Control 2
        if (linearSlidePower > 0.1 || linearSlidePower < -0.1) { longArm.basketReset(); }

        // Control 3
        if (intakeArmPower > 0.1) { longArm.basketCollectSample(); }

    }
}