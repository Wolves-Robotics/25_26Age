package org.firstinspires.ftc.teamcode.CoreSystems;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class RobotHardware {
    private HardwareMap HWMAP;
    private IMU imu;


    public RobotHardware(HardwareMap HWMAP){

        this.HWMAP = HWMAP;
        //lowk not good practice but stfu
        for(MOTORS motor : MOTORS.values()){
        motor.setMotor(HWMAP);
        }
        imu = this.HWMAP.get(IMU.class, "imu");
        //imu stuff
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD));
        imu.initialize(parameters);
    }

    public double getheading(){
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
    }
    public void resetYaw(){
        imu.resetYaw();
    }



}


