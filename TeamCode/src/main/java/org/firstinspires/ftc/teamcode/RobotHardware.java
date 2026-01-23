package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.util.HashMap;

public class RobotHardware {
    public void MapMotors() {
        MotorMap = new HashMap<>();
        MotorMap.put(Motors.FRONTLEFTMOTOR, new MotorClass(Motors.FRONTLEFTMOTOR.motor, false,true));
        MotorMap.put(Motors.FRONTRIGHTMOTOR, new MotorClass(Motors.FRONTRIGHTMOTOR.motor, true, true));
        MotorMap.put(Motors.BACKLEFTMOTOR, new MotorClass(Motors.BACKLEFTMOTOR.motor, false, true));
        MotorMap.put(Motors.BACKRIGHTMOTOR, new MotorClass(Motors.BACKRIGHTMOTOR.motor, true,true ));
       /* MotorMap.put(Motors.SHOOTERTOP, new MotorClass(Motors.SHOOTERTOP.motor, false));
        MotorMap.put(Motors.SHOOTERBOTTOM, new MotorClass(Motors.SHOOTERBOTTOM.motor, false));
        MotorMap.put(Motors.INTAKE, new MotorClass(Motors.INTAKE.motor, false));
        MotorMap.put(Motors.TURRET, new MotorClass(Motors.TURRET.motor, false));
    */
    }

  /* public void MapServos() {
        ServoMap = new HashMap<>();
        ServoMap.put(Servos.LEFTSPINSERVO, new ServoClass(Servos.LEFTSPINSERVO.Servo));
        ServoMap.put(Servos.RIGHTSPINSERVO, new ServoClass(Servos.RIGHTSPINSERVO.Servo));
        ServoMap.put(Servos.LATCH, new ServoClass(Servos.LATCH.Servo));
    }*/

    private HashMap<Motors, MotorClass> MotorMap;
    private HashMap<Servos, ServoClass> ServoMap;
    private final HardwareMap HWMAP;
    private final IMU imu;
    private class MotorClass {
        DcMotor MOTOR;

        MotorClass(String Name, Boolean reverse) {
            MOTOR = HWMAP.dcMotor.get(Name);
            if (reverse) {
                MOTOR.setDirection(DcMotor.Direction.REVERSE);
            }
        }
        MotorClass(String Name, Boolean reverse, boolean brake) {
            MOTOR = HWMAP.dcMotor.get(Name);
            if (reverse) {
                MOTOR.setDirection(DcMotor.Direction.REVERSE);
            }
            if(brake){
                MOTOR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }
        }
        public DcMotor getMotor() {
            return MOTOR;
        }
    }
    private class ServoClass {
        CRServo SERVO;

        ServoClass(String Name) {
            SERVO = HWMAP.get(CRServo.class, Name);

        }

        public CRServo getServo() {
            return SERVO;
        }
    }


    public RobotHardware(HardwareMap HWmap) {
        HWMAP = HWmap;
        imu = HWMAP.get(IMU.class, "imu");

    }

    public void setIMU(){

        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.BACKWARD,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));
        imu.initialize(parameters);
    }
    public double getHeading(){
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
    }
    public void resetYAW(){
        imu.resetYaw();
    }
    public void setMotorPower(Motors motor, double power) {
        DcMotor Mota = MotorMap.get(motor).getMotor();
        Mota.setPower(power);
    }
    public void setServoPower(Servos servo, double power){
        CRServo serva = ServoMap.get(servo).getServo();
        serva.setPower(power);

    }
}