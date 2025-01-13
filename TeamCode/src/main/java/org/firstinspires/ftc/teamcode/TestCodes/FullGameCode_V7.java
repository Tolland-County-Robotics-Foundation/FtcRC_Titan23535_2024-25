package org.firstinspires.ftc.teamcode.TestCodes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Drive_Mechanisms.Drive_v1;
import org.firstinspires.ftc.teamcode.Intake_Arm_Mechanisms.Intake_v1;
import org.firstinspires.ftc.teamcode.LongArm_Mechanisms.LongArm_v3;

@TeleOp(name = "Full Game Code7", group = "test")
@Disabled

public class FullGameCode_V7 extends OpMode {


    /// Necessary objects and variable creation --------------------------------------------------

    // Creating an object from ElapsedTime class to have run time information
    private ElapsedTime runtime = new ElapsedTime();

    //Creating two variables for capping the speed
    String speedcap = "Normal";

    double speed_percentage = 50.0;

    // Creating objects from Drive_V1, Intake_v1, and LongArm_v2 class
    
    Drive_v1    drive   = new Drive_v1();
    Intake_v1   intake  = new Intake_v1();
    LongArm_v3  longArm = new LongArm_v3();


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
        Pressing only left trigger will give positive values.
        So, it will move the intake towards the game piece.

        Pressing only right trigger will give negative values.
        So, it will move the intake towards the basket.

        Pressing left bumper will close the claw.
        So, the claw can hold the game piece.

        Pressing right bumper will open the claw.
        So, the claw can release the game piece.
     */

        double intakeArmPower           = gamepad2.left_trigger - gamepad2.right_trigger;

        boolean intakeClawCloseButton   = gamepad2.left_bumper;
        boolean intakeClawOpenButton    = gamepad2.right_bumper;



        // Long Arm

        boolean basketScoreButton   = gamepad2.x;
        boolean basketResetButton   = gamepad2.b;

        boolean longArmLiftButton   = gamepad2.dpad_up;
        boolean longArmResetButton  = gamepad2.dpad_down;
        boolean longArmStopButton   = gamepad2.a;


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

        intake.moveIntakeArm(intakeArmPower);

        // Intake claw control

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


        telemetry.addData("intake arm power: ", intakeArmPower);


        /// Long arm mechanism ------------------------------------------------------------------

        // Long arm control

        if (longArmResetButton)     { longArm.autoResetArm();   }
        else if (longArmLiftButton) { longArm.autoLiftArm();    }
        else if (longArmStopButton) { longArm.stopArm();        }


        // Basket control

        if (basketScoreButton)      { longArm.scoreGamePiece(); }
        else if (basketResetButton) { longArm.basketReset();    }

        /// Telemetry -----------------------------------------------------------------------------


        //Display Runtime
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        //Displaying Axial,Lateral,Yaw AKA horizontal, vertical, and spin
        telemetry.addData("Axial:", axialButton);
        telemetry.addData("Lateral:",lateralButton);
        telemetry.addData("Yaw:", yawButton);
        telemetry.addData("Current Speed Cap", speedcap);
        telemetry.addData("Speed percentage: ",speed_percentage);


    }
}