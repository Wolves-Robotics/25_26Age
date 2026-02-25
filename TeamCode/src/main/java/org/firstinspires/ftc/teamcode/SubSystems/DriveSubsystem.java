package org.firstinspires.ftc.teamcode.SubSystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.CoreSystems.CentralSystem;
import org.firstinspires.ftc.teamcode.CoreSystems.RobotHardware;
import org.firstinspires.ftc.teamcode.Enums.Status;

public class DriveSubsystem {
    private  DcMotorEx frontLeftMotor;
    private  DcMotorEx backLeftMotor;
    private DcMotorEx frontRightMotor;
    private DcMotorEx backRightMotor;
    public DriveSubsystem(
            DcMotorEx frontLeftMotor,
            DcMotorEx frontRightMotor,
            DcMotorEx backLeftMotor,
            DcMotorEx backRightMotor
                        )
    {
        this.frontLeftMotor = frontLeftMotor;
        this.frontRightMotor = frontRightMotor;
        this.backRightMotor = backRightMotor;
        this.backLeftMotor = backLeftMotor;
    }
    public void DriveCalculations(Gamepad gamepad1, RobotHardware RBHW){
        double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
        double x = gamepad1.left_stick_x;
        double rx = gamepad1.right_stick_x;

        // This button choice was made so that it is hard to hit on accident,
        // it can be freely changed based on preference.
        // The equivalent button is start on Xbox-style controllers.
        if (gamepad1.options) {
            RBHW.resetYaw();
        }

        double botHeading =  RBHW.getheading();

        // Rotate the movement direction counter to the bot's rotation
        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);
        double Precision = 1.0-gamepad1.right_trigger;
        rotX = rotX * 1.1;  // Counteract imperfect strafing

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        double frontLeftPower = (rotY + rotX + rx) / denominator;
        double backLeftPower = (rotY - rotX + rx) / denominator;
        double frontRightPower = (rotY - rotX - rx) / denominator;
        double backRightPower = (rotY + rotX - rx) / denominator;

        frontLeftMotor.setPower(frontLeftPower*Precision);
        backLeftMotor.setPower(backLeftPower*Precision);
        frontRightMotor.setPower(frontRightPower*Precision);
        backRightMotor.setPower(backRightPower*Precision);

    }
    public void brakeSwitch(Status brakeornottobrake){
        if(brakeornottobrake == Status.BRAKING){
            frontRightMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
            frontLeftMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
            backRightMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
            backLeftMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        }else{
            frontRightMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
            frontLeftMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
            backRightMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
            backLeftMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        }
    }
    public void test(){
        frontLeftMotor.setPower(1.0);// front right
        backLeftMotor.setPower(1.0); // correct
        frontRightMotor.setPower(1.); // front left
        backRightMotor.setPower(1.); // correct
    }
}
