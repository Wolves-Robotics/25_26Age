package org.firstinspires.ftc.teamcode.Subsystems;

import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.Servos;

public class Spindexer {
    private RobotHardware RBHW;
    public Spindexer(RobotHardware _RBHW){
        RBHW = _RBHW;
    }
    public void turnSpindexer(double power){
        RBHW.setServoPower(Servos.LEFTSPINSERVO,power);
        RBHW.setServoPower(Servos.RIGHTSPINSERVO,-power);
    }
    public void moveLatch(double power){

    }
}
