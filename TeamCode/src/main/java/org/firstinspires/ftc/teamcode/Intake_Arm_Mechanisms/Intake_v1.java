package org.firstinspires.ftc.teamcode.Intake_Arm_Mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake_v1 extends LinearOpMode{
    @Override
    public void runOpMode() throws InterruptedException {

    }

    // Create one object of DcMotor class and one object of Servo class

    private Servo claw          = null;
    private DcMotor intakeArm   = null;


    /*Create an method (init)

        //Return of this method is void


        //Do hardware mapping for all the objects


        //Set the direction of rotation of the DC motor


        //Set the direction of rotation of the servo motor


        //Add break to the DC motor (using zero power behavior)

     */

    public void init(HardwareMap hwMap)
    {
        claw        = hwMap.get(Servo.class, "claw");
        intakeArm   = hwMap.get(DcMotor.class, "intakeArm");

        claw.setDirection(Servo.Direction.FORWARD);
        intakeArm.setDirection(DcMotorSimple.Direction.FORWARD);

        intakeArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }



    // Create a method to move the intake arm

    public void moveIntakeArm(double power)
    {
        intakeArm.setPower(power);
    }


    // create a method to collect and release the game piece

    public void collectGamePiece(boolean collectButton)
    {
        if (collectButton)
        {
            claw.setPosition(0.5);
        }
    }
    
}
