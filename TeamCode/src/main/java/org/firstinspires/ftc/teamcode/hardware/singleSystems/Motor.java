package org.firstinspires.ftc.teamcode.hardware.singleSystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Motor {
    private DcMotor motor;

    private boolean voltage;

    private int position;

    private double previousPower;
    private double setPower;

    public Motor(String name, boolean reverse, boolean brake, boolean voltage, HardwareMap hardwareMap) {
        motor = hardwareMap.dcMotor.get(name);

        motor.setDirection(reverse ?
                DcMotorSimple.Direction.REVERSE:
                DcMotorSimple.Direction.FORWARD);
        motor.setZeroPowerBehavior(brake ?
                DcMotor.ZeroPowerBehavior.BRAKE:
                DcMotor.ZeroPowerBehavior.FLOAT);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        this.voltage = voltage;
    }

    public void setPower(double power) {
        setPower = power;
    }

    public int getPosition() {
        return position;
    }

    public void update() {
        position = motor.getCurrentPosition();

        if (previousPower != setPower) {
            motor.setPower(setPower);
            previousPower = setPower;
        }
    }
}
