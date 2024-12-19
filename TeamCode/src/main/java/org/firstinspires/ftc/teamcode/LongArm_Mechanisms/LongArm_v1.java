package org.firstinspires.ftc.teamcode.LongArm_Mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;

// Remove line 13 (we want to make this class available for both autonomous and teleop
@Disabled
@TeleOp //assign motor to job

/*

    Line 21: Remove "extends OpMode"
    We can run only one OpMode at a time. So, we won't use OpMode in our derived class

 */
public class LongArm_v1 extends OpMode {

    // Remove line 25. We are creating a new function "init". Here we are not using init function of OpMode

    @Override
    public void init() {}


        /*
            The objects you are creating in line 30 - 32 should stay outside of any function
            You should move these 3 lines below line 21
         */
        /*
        private DcMotor LeftArmLift = null;
        private DcMotor RightArmLift = null;
        private Servo BasketArm = null;

         */

        /*
            The function is expecting an argument
            As we won't use OpMode class, we need to use HardwareMap class
            So, we need to pass an object of HardwareMap class as an argument
            I will show you how to do it
         */
/*
        public static void LinearSlideInit() {


            /// ROBOT HARDWARE CONFIGURATION ---------------------------------------------------
            LeftArmLift = hardwareMap.get(DcMotor.class, "LeftArmLift"); // GoBilda 3/4
            RightArmLift = hardwareMap.get(DcMotor.class, "RightArmLift"); //GoBilda 3/4
            BasketArm = hardwareMap.get(Servo.class, "BasketArm"); // Servo
            ///---------------------------------------------------------------------------------
            LeftArmLift.setDirection(DcMotor.Direction.FORWARD); //Left motor will forward to lift
            RightArmLift.setDirection(DcMotor.Direction.REVERSE); //Right motor will reverse to lift
            BasketArm.setDirection(Servo.Direction.FORWARD); //Left motor will forward to drop basket


        }

 */

/*
        public static void ArmBasket() {

            double armBasketPower = 1; //set position to 1; 180 degrees angle

            BasketArm.setPosition(armBasketPower);

        }


        public static void LiftArm() {

            double armLiftPower = 0.5; //set power to 50%

            LeftArmLift.setPower(armLiftPower);
            RightArmLift.setPower(armLiftPower);
        }

    }

 */
    @Override
    public void loop()
    {}

}

