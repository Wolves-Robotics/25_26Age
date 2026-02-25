package org.firstinspires.ftc.teamcode.AUTO;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.CoreSystems.CentralSystem;
import org.firstinspires.ftc.teamcode.CoreSystems.RobotHardware;

@Autonomous(group = "auto",name = "easy")
public class AutoLeave extends OpMode {
    private RobotHardware RBHW;
    private CentralSystem Ares;
    @Override
    public void init(){
        RBHW = new RobotHardware(hardwareMap);
        Ares = new CentralSystem(RBHW,gamepad1);
    }
    @Override
    public void loop(){
        Ares.forward();
    }
}
