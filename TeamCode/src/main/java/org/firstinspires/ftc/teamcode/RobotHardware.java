package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class RobotHardware {
    public static HardwareMap HWMAP;
    public enum MOTORS{
        FRONTLEFTDRIVE("FLD"),
        FRONTRIGHTDRIVE("FRD"),
        BACKLEFTDRIVE("BLD"),
        BACKRIGHTDRIVE("BRD"),
        INTAKE("intake"),
        TURRETMOTOR("turret"),
        LEFTSHOOTERMOTOR("LshooterMotor"),
        RIGHTSHOOTERMOTOR("RshooterMotor");
       private final DcMotor motor;
     MOTORS(String motorName){
        motor = HWMAP.get(DcMotor.class, motorName);
    }
    public DcMotor getMotor(){
         return motor;
    }
    public void setMotorPower(double motorPower){
         motor.setPower(motorPower);
    }

    }
    public RobotHardware(HardwareMap _HWMAP){
        HWMAP = _HWMAP;
    }
}
