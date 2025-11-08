package org.firstinspires.ftc.teamcode.hardware.singleSystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Motor {
    private DcMotorEx motor;

    private boolean voltage;

    private int position;

    private double previousPower;
    private double setPower;

    public Motor(String name, boolean reverse, boolean brake, boolean voltage, HardwareMap hardwareMap) {
        motor = hardwareMap.get(DcMotorEx.class, name);

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

    public void setBrake(boolean brake) {
        if (brake) motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        else motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

    }

    public int getPosition() {
        return position;
    }

    public double getVelocity() {
        return motor.getVelocity();
    }

    public void update() {
        position = motor.getCurrentPosition();

        if (previousPower != setPower) {
            motor.setPower(setPower);
            previousPower = setPower;
        }
    }
}
