package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.CoreSystems.CentralSystem;
import org.firstinspires.ftc.teamcode.CoreSystems.RobotHardware;
import org.firstinspires.ftc.teamcode.Enums.Status;

@TeleOp(group = "test",name = "resrtres")
public class TeleOpthingy extends OpMode {
    private RobotHardware RBHW;
    private CentralSystem Ares;
    @Override
    public void init(){
        RBHW = new RobotHardware(hardwareMap);
        Ares = new CentralSystem(RBHW,gamepad1);
    }
    @Override
    public void loop(){
        if (gamepad1.left_bumper){
            Ares.updateBraking(Status.BRAKING);
        }
        else if(gamepad1.a){
            Ares.updateStatus(Status.INTAKING);
        }
        else if(gamepad1.right_bumper){
            Ares.updateStatus(Status.REVVINGUP);
        }else if(gamepad1.x){
            Ares.updateStatus(Status.OUTTAKE);
        }
        else{
            Ares.updateStatus(Status.IDLE);
        }

        Ares.doStuff();
    }

}
