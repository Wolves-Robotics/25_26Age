package org.firstinspires.ftc.teamcode.CoreSystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public class TESTCLASS {
    public DcMotorEx turret;
    public TESTCLASS(){
            turret = MOTORS.TURRETMOTOR.getMotor();
    }
}
