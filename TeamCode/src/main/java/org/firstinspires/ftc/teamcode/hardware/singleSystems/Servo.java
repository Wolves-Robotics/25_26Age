package org.firstinspires.ftc.teamcode.hardware.singleSystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class Servo {
    private com.qualcomm.robotcore.hardware.Servo servo;

    private double previousPosition = 0;
    private double setPosition = 0;

    public Servo(String name, HardwareMap hardwareMap) {
        servo = hardwareMap.servo.get(name);
    }

    public void setPosition(double position) {
        setPosition = position;
    }

    public void update() {
        if (setPosition != previousPosition) {
            previousPosition = setPosition;
            servo.setPosition(setPosition);
        }
    }
}
