package org.firstinspires.ftc.teamcode.SubSystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class FlywheelSubsystem {
    private DcMotorEx shooter;
    public FlywheelSubsystem(DcMotorEx shooter){
        this.shooter = shooter;

    }
    public void testRevUp(){
        shooter.setPower(1.0);
    }
    public void reverse(){
        shooter.setPower(-1.0);
    }
    public void STOPPLEASEIBEG(){
        shooter.setPower(0.0);
    }
}
