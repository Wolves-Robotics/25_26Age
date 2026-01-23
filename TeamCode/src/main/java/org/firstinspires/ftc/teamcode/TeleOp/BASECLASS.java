package org.firstinspires.ftc.teamcode.TeleOp;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.Subsystems.Drive;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Spindexer;
import org.firstinspires.ftc.teamcode.Subsystems.Turret;
@Disabled
@TeleOp(name="base", group="TeleOp")
public class BASECLASS extends OpMode {
    private RobotHardware RBHW;
    private Drive drivesubsystem;
    private Intake intakeSubsystem;
    private Turret turretSubsystem;
    private Spindexer spindexerSubsystem;


    @Override
    public void init(){
        RBHW = new RobotHardware(hardwareMap);
        RBHW.MapMotors();
        RBHW.setIMU();

        drivesubsystem = new Drive(RBHW);
        intakeSubsystem = new Intake(RBHW);
        turretSubsystem = new Turret(RBHW);
        spindexerSubsystem = new Spindexer(RBHW);

    }
    @Override
    public void init_loop() {
    }
    @Override
    public void start() {

    }
    @Override
    public void loop() {}

    @Override
    public void stop() {
    }
}

