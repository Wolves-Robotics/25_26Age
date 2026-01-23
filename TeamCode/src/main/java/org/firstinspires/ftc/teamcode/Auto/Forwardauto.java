package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.Subsystems.Drive;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Spindexer;
import org.firstinspires.ftc.teamcode.Subsystems.Turret;

@Autonomous(name="fowawdadow", group="Robot")
public class Forwardauto extends OpMode {
    private RobotHardware RBHW;
    private Drive drivesubsystem;
    private ElapsedTime ELT;
    private Spindexer spindexerSubsystem;
    public void init(){
        RBHW = new RobotHardware(hardwareMap);
        RBHW.MapMotors();
        RBHW.setIMU();
        drivesubsystem = new Drive(RBHW);


    }
    @Override
    public void init_loop() {
    }
    @Override
    public void start() {
        ELT = new ElapsedTime();
        ELT.startTime();
    }
    @Override
    public void loop() {
        if(ELT.seconds() < 1){
            drivesubsystem.setAllDriveMotor(1.0);
        }
    }

    @Override
    public void stop() {
    }
}
