/*
This is a program to test the Linear slide. It can only be used when the linear slide is complete.
The buttons to test this program:
 * "y" = Lift the linear slide (both motors/arms)
 * "a" = drop the linear slide (both motors/  arms)
 * "b" = basket of linear slide operation.
   * When button pressed, basket drops. When (button) released, basket returns to original position.

 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;


//Motors
@TeleOp //assign motor to job
public class LongArm_v1 extends OpMode {

    private DcMotor LeftArmLift = null;
    private DcMotor RightArmLift = null;
    private Servo LeftArmBasket = null;
    private Servo RightArmBasket = null;


    @Override
    public void init() {
        /// ROBOT HARDWARE CONFIGURATION ---------------------------------------------------
        LeftArmLift = hardwareMap.get(DcMotor.class, "LeftArmLift"); // GoBilda 3/4
        RightArmLift = hardwareMap.get(DcMotor.class, "RightArmLift"); //GoBilda 3/4
        LeftArmBasket = hardwareMap.get(Servo.class, "LeftArmBasket"); // servo
        RightArmBasket = hardwareMap.get(Servo.class, "RightArmBasket"); //servo
        ///---------------------------------------------------------------------------------
        LeftArmLift.setDirection(DcMotor.Direction.FORWARD); //Left motor will forward to lift
        RightArmLift.setDirection(DcMotor.Direction.REVERSE); //Right motor will reverse to lift
        LeftArmBasket.setDirection(Servo.Direction.FORWARD); //Left motor will forward to drop basket
        LeftArmBasket.setDirection(Servo.Direction.REVERSE); //Right motor will reverse to drop basket
    }

    @Override
    public void loop() {
        //variables for power
        double armLiftPower = 0.0;
        double armBasketPower = 0.0;
        //button assignments
        boolean armLiftbutton = gamepad2.y; //Lift the arm
        boolean armDropbutton = gamepad2.a; //Drop the arm
        boolean armBasketbutton = gamepad2.b; //Drop the sample/specimen

        //press down y or a button
        if (armLiftbutton || armDropbutton) {
            armLiftPower = 1; //Hold down button for linear slide to go up
        } else {
            armLiftPower = 0.0; //will stop when released
        }



        //press b button
        if (armBasketbutton) {
            armBasketPower = 1; // Hold down button for basket to drop
        } else {
            armBasketPower = 0.0; // will come to original position when button released

            // to fix; need more movement on servo. test again when linear slide is complete.
        }

        //Setting power to robot motors
        LeftArmLift.setPower(armLiftPower);
        RightArmLift.setPower(armLiftPower);
        LeftArmBasket.setPosition(armBasketPower);
        LeftArmBasket.setPosition(armBasketPower);

    }
}  
