package org.firstinspires.ftc.teamcode.TestCodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Mechanisms_Final.Drive;
import org.firstinspires.ftc.teamcode.Mechanisms_Final.Intake;
import org.firstinspires.ftc.teamcode.Mechanisms_Final.LongArm;


@TeleOp(name = "Full Teleop 1", group = "AWindsor")

public class Windsor_FullGameCode_V1 extends OpMode {


    /// Necessary objects and variable creation --------------------------------------------------

    // Creating an object from ElapsedTime class to have run time information
    private ElapsedTime runtime = new ElapsedTime();

    //Creating two variables for capping the speed
    String speedcap = "Normal";

    double speed_percentage = 50.0;

    // Creating objects from Drive_V1, Intake_v1, and LongArm_v2 class

    Drive drive     = new Drive();
    Intake intake   = new Intake();
    LongArm longArm = new LongArm();


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
        /// Button configuration ----------------------------------------------------------------------

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
        double axialButton   = -gamepad1.left_stick_y;  // Note: pushing stick forward gives negative value
        double lateralButton =  gamepad1.left_stick_x;

        // Intake

    /*
        Gamepad 2 left stick y to move the intake arm

        Gamepad 2 right stick x to move the claw
     */

        double intakeArmPower   = gamepad2.left_stick_y;
        double intakeClawPower  = gamepad2.right_stick_x;


        // Long Arm

        double linearSlidePower = gamepad2.left_trigger - gamepad2.right_trigger;

        boolean basketScoreButton   = gamepad2.x;
        boolean basketCollectButton   = gamepad2.b;

        boolean linearSlideLiftButton   = gamepad2.dpad_up;
        boolean linearSlideResetButton  = gamepad2.dpad_down;
        boolean linerSlideStopButton   = gamepad2.a;


        // Set the speed cap for driver 1
        if (gamepad1.a)
        {
            speedcap = "Max";
            speed_percentage = 90.0;
        } else if (gamepad1.b)
        {
            speedcap = "Fast";
            speed_percentage = 65.0;
        } else if (gamepad1.y)
        {
            speedcap = "Normal";
            speed_percentage = 40.0;
        } else if (gamepad1.x)
        {
            speedcap = "Slow";
            speed_percentage = 25.0;
        }


        /*
        boolean goForward  = gamepad1.dpad_up;
        boolean goBackward = gamepad1.dpad_down;
        boolean goLeft  = gamepad1.dpad_left;
        boolean goRight  = gamepad1.dpad_right;
        */


        drive.setDriveMotorPower(axialButton, lateralButton, yawButton, speed_percentage);



        /// intake mechanism ----------------------------------------------------------------------

        // Intake arm control

        intake.moveArm(intakeArmPower);

        intake.moveClaw(intakeClawPower);

        // Intake claw control

        /*

        if (intakeClawCloseButton)
        {
            intake.closeClaw();
        }
        else if (intakeClawOpenButton)
        {
            intake.openClaw();
        }
        else
        {
            intake.stopClaw();
        }

         */



        /// Long arm mechanism ------------------------------------------------------------------

        // Long arm control

        /*

        if      (linearSlideResetButton)    { longArm.autoResetArm();   }
        else if (linearSlideLiftButton)     { longArm.autoLiftArm();    }
        else if (linerSlideStopButton)     { longArm.stopArm();        }

         */

        longArm.moveLinearSlide(linearSlidePower);


        // Basket control

        if (basketScoreButton)      { longArm.scoreGamePiece(); }
        else if (basketCollectButton) { longArm.collectGamePiece();    }



        /// Telemetry -----------------------------------------------------------------------------


        //Display Runtime
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        //Displaying Axial,Lateral,Yaw AKA horizontal, vertical, and spin
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