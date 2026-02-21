package org.firstinspires.ftc.teamcode.CoreSystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

enum MOTORS{
    FRONTLEFTDRIVE("FLD",true ),
    FRONTRIGHTDRIVE("FRD",false),
    BACKLEFTDRIVE("BLD",false),
    BACKRIGHTDRIVE("BRD",false),
    INTAKE("intake",true),
    TURRETMOTOR("turret",false),
    SHOOTER("shooter",false);

    private DcMotorEx motor;
    private final Boolean reverse;
    private final String motorName;
    MOTORS(String motorName, Boolean reverse){
        this.motorName = motorName;
        this.reverse = reverse;
    }
     DcMotorEx getMotor(){
       return motor;
    }
    public void setMotor(HardwareMap HWMAP){
        motor = HWMAP.get(DcMotorEx.class, motorName);
        if(reverse){
            motor.setDirection(DcMotorEx.Direction.REVERSE);}
    }
 }