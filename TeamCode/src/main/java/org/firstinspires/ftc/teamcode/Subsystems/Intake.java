package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.robot.Robot;

import org.firstinspires.ftc.teamcode.Motors;
import org.firstinspires.ftc.teamcode.RobotHardware;

public class Intake {
    private RobotHardware RBHW;
    public Intake(RobotHardware _RBHW){
        RBHW = _RBHW;
    }
    public void Dotheintake(double power){
        RBHW.setMotorPower(Motors.INTAKE, power);

    }
}
