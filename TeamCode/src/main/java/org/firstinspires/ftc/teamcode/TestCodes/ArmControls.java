/*
This is a program to test the Linear slide. It can only be used when the linear slide is complete.
The buttons to test this program:
 * "y" = Lift the linear slide (both arms)
 * "a" = drop the linear slide (both arms)
 * "b" = basket of linear slide operation.
   * When button pressed, basket drops. When (button) released, basket returns to original position.

 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;


//Motors
@TeleOp //assign motor to job
public class ArmControls extends OpMode {

    private DcMotor LeftArmLift = null;
    private DcMotor RightArmLift = null;
    private Servo LeftArmRotate = null;
    private Servo RightArmRotate = null;


    @Override
    public void init() {
        /// ROBOT HARDWARE CONFIGURATION ---------------------------------------------------
        LeftArmLift = hardwareMap.get(DcMotor.class, "LeftArmLift");
        RightArmLift = hardwareMap.get(DcMotor.class, "RightArmLift");
        LeftArmRotate = hardwareMap.get(Servo.class, "LeftArmRotate");
        RightArmRotate = hardwareMap.get(Servo.class, "RightArmRotate");
        ///---------------------------------------------------------------------------------
        LeftArmLift.setDirection(DcMotor.Direction.REVERSE); //Left motor will reverse to lift
        RightArmLift.setDirection(DcMotor.Direction.FORWARD); //Right motor will forward to lift
        LeftArmRotate.setDirection(Servo.Direction.FORWARD); //Left motor will forward to drop basket
        RightArmRotate.setDirection(Servo.Direction.REVERSE); //Right motor will reverse to drop basket
    }

    @Override
    public void loop() {
        //variables for power
        double armLiftPower = 0.0;
        double armRotatePower = 0.0;
        //button assignments
        boolean armLiftbutton = gamepad2.y; //Lift the arm
        boolean armDropbutton = gamepad2.a; //Drop the arm
        boolean armRotatebutton = gamepad2.b; //Drop the sample/specimen

        //press down y button
        if (armLiftbutton) {
            armLiftPower = 0.5; //Hold down button for linear slide to go up
        } else {
            armLiftPower = 0.0; //will stop when released
        }

        //press 'a' button
        if (armDropbutton) {
            armLiftPower = -0.5; // Hold down button for linear slide to go down
        } else {
            armLiftPower = 0.0; //will stop when released
        }

        //press b button
        if (armRotatebutton) {
            armRotatePower = 0.5; // Hold down button for basket to drop
        } else {
            armRotatePower = 0.0; // will come to original position when button released
        }

        //Setting power to robot motors
        LeftArmLift.setPower(armLiftPower);
        RightArmLift.setPower(armLiftPower);
        LeftArmRotate.setPosition(armRotatePower);
        RightArmRotate.setPosition(armRotatePower);

    }
}
