package org.firstinspires.ftc.teamcode.TestCodes;


import static android.os.SystemClock.sleep;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Drive_Mechanisms.Drive_v1;

@TeleOp(name = "Full Game Code", group = "test")
public class FullGameCode_V2 extends OpMode {
    // Creating an object from Drive_V1 class
    Drive_v1 drive = new Drive_v1();

    // Creating an object from ElapsedTime class to have run time information
    private ElapsedTime runtime = new ElapsedTime();

    //Creating two variables for capping the speed
    String speedcap = "Normal";
    double speed_percentage = 40.0;

    @Override
    public void init()
    {
        // Wait for the game to start (driver presses PLAY)
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Resting runtime
        runtime.reset();

        telemetry.addData(">", "Press Start To Run The Robot" );
        // Use init method to do the initialization of drive object
        drive.init(hardwareMap);
    }
    @Override
    public void loop()
    {
        //Speed
        {
            // Set the speed cap for driver 1 via face buttons
            if (gamepad1.a) {
                speedcap = "Max";
                speed_percentage = 90.0;
            } else if (gamepad1.b) {
                speedcap = "Fast";
                speed_percentage = 65.0;
            } else if (gamepad1.y) {
                speedcap = "Normal";
                speed_percentage = 40.0;
            } else if (gamepad1.x) {
                speedcap = "Slow";
                speed_percentage = 25.0;
            }
            // Set the speed cap for driver 1 via bumpers
            if (gamepad1.left_bumper && speed_percentage > 20) {
                speed_percentage = speed_percentage - 20.0;
                while (true) {
                    if (!gamepad1.left_bumper) {
                        break;
                    }
                }
            }
            if (gamepad1.right_bumper && speed_percentage < 100) {
                speed_percentage = speed_percentage + 20.0;
                while (true) {
                    if (!gamepad1.left_bumper) {
                        break;
                    }
                }
            }
        }

        /* Uses left joystick to go forward, backward, left, and right, and right joystick to rotate.

           Left joystick up --> forward

           Left joystick down --> Backward

           Left joystick right --> Right

           Left joystick left --> Left

           Right joystick left --> rotate left

           Right joystick right --> rotate right

        */
        double axial   = -gamepad1.left_stick_y;  // Note: pushing stick forward gives negative value
        double lateral =  gamepad1.left_stick_x;
        double yaw     =  gamepad1.right_stick_x;
        /*
        boolean goForward  = gamepad1.dpad_up;
        boolean goBackward = gamepad1.dpad_down;
        boolean goLeft  = gamepad1.dpad_left;
        boolean goRight  = gamepad1.dpad_right;
        */
        drive.setDriveMotorPower(axial, lateral, yaw, speed_percentage);

        //Displaying Statistics
        {
            //Display Runtime
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            //Displaying Axial,Lateral,Yaw AKA horizontal, vertical, and spin
            telemetry.addData("Axial:", axial);
            telemetry.addData("Lateral:", lateral);
            telemetry.addData("Yaw:", yaw);
            telemetry.addData("Current Speed", speedcap);
            telemetry.addData("Speed percentage: ", speed_percentage);
        }
    }
}