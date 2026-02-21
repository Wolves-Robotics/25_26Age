package org.firstinspires.ftc.teamcode.CoreSystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Enums.Status;
import org.firstinspires.ftc.teamcode.SubSystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.SubSystems.FlywheelSubsystem;
import org.firstinspires.ftc.teamcode.SubSystems.TurretSubsystem;

public class CentralSystem {
    private DcMotorEx intake;
    private RobotHardware RBHW;
    private DriveSubsystem driveSub;
    private TurretSubsystem turretSub;
    private FlywheelSubsystem flywheelSub;
    private Status State;
    private Status brakingState;
    private Gamepad player1;
    public CentralSystem(RobotHardware robohawdware, Gamepad prayrer){
        RBHW = robohawdware;
        State = Status.IDLE;
        brakingState = Status.IDLE;
        player1 = prayrer;
        driveSub = new DriveSubsystem(
                MOTORS.FRONTLEFTDRIVE.getMotor(),
                MOTORS.FRONTRIGHTDRIVE.getMotor(),
                MOTORS.BACKLEFTDRIVE.getMotor(),
                MOTORS.BACKRIGHTDRIVE.getMotor());

        intake = MOTORS.INTAKE.getMotor();
    }
    public void updateStatus(Status state){
        this.State = state;
    }
    public void updateBraking(Status state){
        this.brakingState = state;
    }
    public void doStuff(){
        switch (State){
            case INTAKING:
                intake.setPower(1.0);
                break;

            case REVVINGUP:
                //rev
                break;

            case SHOOTING:
                //shoot
                break;
            case OUTTAKE:
                intake.setPower(-1.0);
                break;
            default:
                intake.setPower(0.0);
        }
        driveSub.brakeSwitch(brakingState);
        driveSub.DriveCalculations(player1,RBHW);
    }



}
