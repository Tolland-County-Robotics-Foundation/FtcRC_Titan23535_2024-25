
package org.firstinspires.ftc.teamcode.Mechanisms_Final;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Climb {

    // create an object from CRservo class

    private CRServo hookLeft = null;
    private CRServo hookRight = null;

    private double hookPower = 1;

    // create an init function for hardware mapping

    public void init(HardwareMap hardwareMap) {
        hookLeft = hardwareMap.get(CRServo.class, "hookLeft");
        hookRight = hardwareMap.get(CRServo.class, "hookRight");

        hookLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        hookRight.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void reset() {
        hookLeft.setPower(-hookPower);
        hookRight.setPower(-hookPower);
    }

    public void grabRung() {
        hookLeft.setPower(hookPower);
        hookRight.setPower(hookPower);
    }

    public void stop() {
        hookLeft.setPower(0);
        hookRight.setPower(0);
    }

}
