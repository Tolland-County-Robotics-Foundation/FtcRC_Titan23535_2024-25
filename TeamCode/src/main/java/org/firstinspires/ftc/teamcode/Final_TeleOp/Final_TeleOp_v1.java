/*
    This is the simple TeleOp.
 */

package org.firstinspires.ftc.teamcode.Final_TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Mechanisms_Final.Climb;
import org.firstinspires.ftc.teamcode.Mechanisms_Final.Drive;
import org.firstinspires.ftc.teamcode.Mechanisms_Final.Intake;
import org.firstinspires.ftc.teamcode.Mechanisms_Final.LongArm;


@TeleOp(name = "Simple TeleOp", group = "AState")

public class Final_TeleOp_v1 extends OpMode {

    /// Necessary objects and variable creation --------------------------------------------------

    // Creating timers
    private ElapsedTime runtime = new ElapsedTime();
    private ElapsedTime hookTimer = new ElapsedTime();
    private ElapsedTime linearSlideTimer = new ElapsedTime();
    private ElapsedTime basketTimer = new ElapsedTime();

    //Creating two variables for capping the speed
    String speedcap = "Normal";

    double speed_percentage = 50.0;

    // Creating objects from Drive_V1, Intake_v1, and LongArm_v2 class

    Climb hook      = new Climb();
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
        hookTimer.reset();
        linearSlideTimer.reset();
        basketTimer.reset();

        /// Initialization ------------------------------------------------------------------------

        // Using "init" method of each class

        drive.init(hardwareMap);
        intake.init(hardwareMap);
        longArm.init(hardwareMap);
        hook.init(hardwareMap);

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

        /* -------------------------------------------------
        *
        * Gamepad 2 left stick y to move the intake arm
        * Gamepad 2 right stick x to move the claw
        *
        ------------------------------------------------------ */

        double intakeArmPower   = gamepad2.left_stick_y * 0.5;
        double intakeClawPower  = gamepad2.right_stick_x;


        // Long Arm
        /* ---------------------------------------------------------------------
         *
         * Gamepad 2 triggers to control the linear slides
         * Gamepad 2 dpad to control the basket
         */

        double linearSlidePower = gamepad2.left_trigger - gamepad2.right_trigger;

        boolean basketScoreButton   = gamepad2.dpad_down;
        boolean basketCollectButton = gamepad2.dpad_up;
        boolean basketResetButton = gamepad2.dpad_right;
        

        // Hook

        boolean hookResetButton = gamepad2.y;
        boolean hookGrabRungButton = gamepad2.a;


        /// Mechanisms ------------------------------------------------------------------------

        /// Drive Controls -----------------------------------------------------------------


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

        /* ----------------------- Drive controls ------------------------------------------------
         * Control 1: Use Gamepad 1 joysticks to control the movement
         * Control 2: Use Gamepad 1 dpad to have one-directional motion
         */

        // Control 1

        drive.setDriveMotorPower(axialButton, lateralButton, yawButton, speed_percentage);

        // Control 2

        if (goForwardButton) {
            drive.teleOpForward(speed_percentage / 100);
        } else if (goBackwardButton) {
            drive.teleOpBackward(speed_percentage / 100);
        } else if (goLeftButton) {
            drive.teleOpLeft(speed_percentage / 100);
        } else if (goRightButton) {
            drive.teleOpRight(speed_percentage / 100);
        }


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
        else if (basketCollectButton)   { longArm.basketCollectSample();    }
        else if (basketResetButton)     { longArm.basketReset();            }

        // Control 2
        if (linearSlidePower > 0.1 || linearSlidePower < -0.1) { longArm.basketReset(); }

        // Control 3
        if (intakeArmPower > 0.1) { longArm.basketCollectSample(); }

        /// Hook Controls ---------------------------------------------------------------------

        /* ----------------------- Hook Controls -----------------------------------------
         * Control 1: Use two buttons to control the hook
         *              When grab button is pressed, the state will change to grab
         *              When reset button is pressed, the state will change to reset
         *              Grab and reset state will be changed to stop states after 2 seconds
         */


        if (hookGrabRungButton) {
            hookStates = HookStates.GRAB;
            hookTimer.reset();
        } else if (hookResetButton) {
            hookStates = HookStates.RESET;
            hookTimer.reset();
        }

        switch (hookStates) {
            case STOP: {
                hook.stop();
                telemetry.addData("Hook: ", "Stopped");
                break;
            }
            case GRAB: {
                hook.grabRung();
                telemetry.addData("Hook: ", "Grab Rung");
                if (hookTimer.milliseconds() > 2000) {
                    hookStates = HookStates.STOP;
                }
                break;
            }
            case RESET: {
                hook.reset();
                telemetry.addData("Hook: ", "Reset");
                if (hookTimer.milliseconds() > 2000) {
                    hookStates = HookStates.STOP;
                }
                break;
            }
        }


        /// Telemetry -----------------------------------------------------------------------------

        //Display Runtime
        /*
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

         */

    }
}