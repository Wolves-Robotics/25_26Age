package org.firstinspires.ftc.teamcode.Subsystems;

import org.firstinspires.ftc.teamcode.Motors;
import org.firstinspires.ftc.teamcode.RobotHardware;

public class Turret {
    private RobotHardware RBHW;
    public Turret(RobotHardware _RBHW){
        RBHW = _RBHW;
    }
    public void turnTurret(double power){
        RBHW.setMotorPower(Motors.TURRET, power);
    }
    public void startFlywheel(double power){
        RBHW.setMotorPower(Motors.SHOOTERTOP, power);
        RBHW.setMotorPower(Motors.SHOOTERBOTTOM, power);
    }
}
