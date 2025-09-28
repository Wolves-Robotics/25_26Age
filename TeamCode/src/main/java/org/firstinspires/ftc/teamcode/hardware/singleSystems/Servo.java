package org.firstinspires.ftc.teamcode.hardware.singleSystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class Servo {
    public Servo(String name, HardwareMap hardwareMap) {
        hardwareMap.servo.get(name);
    }

    public void setPosition(double position) {

    }

    public void update() {

    }
}
