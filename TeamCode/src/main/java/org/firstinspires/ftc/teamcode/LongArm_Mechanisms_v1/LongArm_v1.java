package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp //assign motor to job
public class LongArm_v1 extends OpMode {

    @Override
    public void init() {

        private DcMotor LeftArmLift = null;
        private DcMotor RightArmLift = null;
        private Servo BasketArm = null;

        public static void LinearSlideInit () {

            /// ROBOT HARDWARE CONFIGURATION ---------------------------------------------------
            LeftArmLift = hardwareMap.get(DcMotor.class, "LeftArmLift"); // GoBilda 3/4
            RightArmLift = hardwareMap.get(DcMotor.class, "RightArmLift"); //GoBilda 3/4
            BasketArm = hardwareMap.get(Servo.class, "BasketArm"); // Servo
            ///---------------------------------------------------------------------------------
            LeftArmLift.setDirection(DcMotor.Direction.FORWARD); //Left motor will forward to lift
            RightArmLift.setDirection(DcMotor.Direction.REVERSE); //Right motor will reverse to lift
            BasketArm.setDirection(Servo.Direction.FORWARD); //Left motor will forward to drop basket
        }

        public static void ArmBasket() {

            double armBasketPower = 1; //set position to 1; 180 degrees angle

            BasketArm.setPosition(armBasketPower);

        }

        public static void LiftArm()

            double armLiftPower = 0.5; //set power to 50%

            LeftArmLift.setPower(armLiftPower);
            RightArmLift.setPower(armLiftPower);

    }

}

