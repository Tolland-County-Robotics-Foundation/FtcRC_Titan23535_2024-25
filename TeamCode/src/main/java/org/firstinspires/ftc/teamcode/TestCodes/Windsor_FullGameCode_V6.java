package org.firstinspires.ftc.teamcode.TestCodes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Mechanisms_Final.Climb;
import org.firstinspires.ftc.teamcode.Mechanisms_Final.Drive;
import org.firstinspires.ftc.teamcode.Mechanisms_Final.Intake;
import org.firstinspires.ftc.teamcode.Mechanisms_Final.LongArm;
import org.firstinspires.ftc.teamcode.Sensor_Mechanisms.Blinkin_v2;
import org.firstinspires.ftc.teamcode.Sensor_Mechanisms.Color_Sensor_v2;

import java.util.Objects;
@Disabled
@TeleOp(name = "Full TeleOp 6", group = "AWindsor")

public class Windsor_FullGameCode_V6 extends OpMode {

    /// Necessary objects and variable creation --------------------------------------------------

    // Creating timers
    private ElapsedTime runtime = new ElapsedTime();
    private ElapsedTime hookTimer = new ElapsedTime();
    private ElapsedTime linearSlideTimer = new ElapsedTime();

    //Creating two variables for capping the speed
    String speedcap = "Normal";
    double speed_percentage = 50.0;
    String sample = "none";
    Boolean reject;
    Boolean accept;
    String redSpy;
    double clawPower;

    // Creating objects from Drive_V1, Intake_v1, and LongArm_v2 class

    Climb hook      = new Climb();
    Drive drive     = new Drive();
    Intake intake   = new Intake();
    LongArm longArm = new LongArm();
    Color_Sensor_v2 colorB = new Color_Sensor_v2();
    Blinkin_v2 lightB = new Blinkin_v2();

    private enum HookStates {
        STOP, GRAB, RESET
    }
    private HookStates hookStates = HookStates.STOP;

    public double samplePick(Boolean accept, Boolean reject) {
        if (accept) {
            return 1.0;
        } else if (reject) {
            return -1.0;
        } else {
            return 0.0;
        }
    }

    @Override
    public void init()
    {
        // Resting runtime
        runtime.reset();
        hookTimer.reset();
        linearSlideTimer.reset();

        /// Initialization ------------------------------------------------------------------------

        // Using "init" method of each class

        drive.init(hardwareMap);
        intake.init(hardwareMap);
        longArm.init(hardwareMap);
        hook.init(hardwareMap);
        colorB.init(hardwareMap);
        lightB.init(hardwareMap);
        /// Telemetry -----------------------------------------------------------------------------

        // Wait for the game to start (driver presses PLAY)
        telemetry.addData("Status", "Initialized");

    }

    @Override
    public void loop()
    {
        /// Alliance data.
        telemetry.addData("Alliance", "Left Trigger for Red, Right Trigger for Blue");
        if (gamepad1.left_trigger > 0) {
            redSpy = "red";
            telemetry.addData("Alliance", "Red");
        }
        if (gamepad1.right_trigger > 0) {
            redSpy = "blue";
            telemetry.addData("Alliance", "Blue");
        }

        /// Button configuration -------------------------------------------------------------------
        double yawButton     =  gamepad1.right_stick_x;
        double axialButton   = -gamepad1.left_stick_y;  // Negative value for pushing stick forward
        double lateralButton =  gamepad1.left_stick_x;

        boolean goForwardButton     = gamepad1.dpad_up;
        boolean goBackwardButton    = gamepad1.dpad_down;
        boolean goLeftButton        = gamepad1.dpad_left;
        boolean goRightButton       = gamepad1.dpad_right;

        // Intake
        double intakeArmPower   = gamepad2.left_stick_y * 0.5;

        // Long Arm
        double linearSlidePower = gamepad2.left_trigger - gamepad2.right_trigger;

        boolean basketScoreButton   = gamepad2.dpad_down;
        boolean basketCollectButton = gamepad2.dpad_up;
        boolean basketResetButton = gamepad2.dpad_right;
        

        // Hook
        boolean hookResetButton = gamepad2.y;
        boolean hookGrabRungButton = gamepad2.a;

        /// Color sensor -----------------------------------------------------------------
        sample = colorB.sampleColor();

        //Automatic sample rejection & acceptation system.
        reject = false;
        accept = false;

        if (Objects.equals(redSpy, "red")) {
            if (Objects.equals(sample, "blue")) {
                reject = true;
            }
            if (Objects.equals(sample, "red")) {
                accept = true;
            }
        }
        if (Objects.equals(redSpy, "blue")) {
            if (Objects.equals(sample, "red")) {
                reject = true;
            }
            if (Objects.equals(sample, "blue")) {
                accept = true;
            }
        }
        if (Objects.equals(sample, "yellow"))

        /// Drive Controls -----------------------------------------------------------------
        // Set the speed cap for driver 1
        if (gamepad1.y) {
            speedcap = "Max";
            speed_percentage = 90.0;
        } else if (gamepad1.b) {
            speedcap = "Fast";
            speed_percentage = 65.0;
        } else if (gamepad1.x) {
            speedcap = "Normal";
            speed_percentage = 40.0;
        } else if (gamepad1.a) {
            speedcap = "Slow";
            speed_percentage = 25.0;
        }

        // Control for setting the motors power
        drive.setDriveMotorPower(axialButton, lateralButton, yawButton, speed_percentage);

        // Control for driving straight forward, backward, left and right



        /// Intake Controls ----------------------------------------------------------------------
        // Intake arm controls
        intake.moveArm(intakeArmPower);

        // Intake claw controls
        if (gamepad2.right_stick_x > 0) {
            clawPower = 1.0;
        } else if ((gamepad2.right_stick_x < 0)) {
            clawPower = -1.0;
        } else {
            clawPower = samplePick(accept, reject);
        }

        intake.moveClaw(clawPower);

        /// Long arm Controls ------------------------------------------------------------------
        longArm.moveLinearSlide(linearSlidePower);


        if (basketScoreButton) {
            linearSlideTimer.reset();
            if (linearSlideTimer.milliseconds() < 1500) {
                longArm.moveLinearSlide(0.1);
            }
        }

        // Basket controls
        if (basketScoreButton)          { longArm.basketScoreSample();    }
        else if (basketCollectButton)   { longArm.basketCollectSample();  }
        else if (basketResetButton)     { longArm.basketReset();    }

        if (linearSlidePower > 0.1 || linearSlidePower < -0.1) {longArm.basketReset();}
        if (intakeArmPower > 0.1) {longArm.basketCollectSample();}


        /// Hook Controls ---------------------------------------------------------------------
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

        /// Rev BLINKIN
        lightB.light(redSpy);

        /// Telemetry -----------------------------------------------------------------------------
        //Display Runtime
        telemetry.addData("Alliance: ", redSpy);
        telemetry.addData("Current Speed Cap", speedcap);
        telemetry.addData("Sample Color", colorB.sampleColor());
        telemetry.addData("Status", "Run Time: " + runtime.toString());
    }
}