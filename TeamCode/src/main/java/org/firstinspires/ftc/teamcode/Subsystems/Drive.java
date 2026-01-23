package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Motors;
import org.firstinspires.ftc.teamcode.RobotHardware;

public class Drive {
    private final RobotHardware  RBHW;
    public Drive(RobotHardware _RBHW){
        RBHW = _RBHW;
    }
    public void fieldCentricDriveCalculations(Gamepad gamepad1){
        double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
        double x = gamepad1.left_stick_x;
        double rx = gamepad1.right_stick_x;

        double botHeading = RBHW.getHeading();

        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);


        rotX = rotX * 1.1;  // Counteract imperfect strafing

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        double frontLeftPower = (rotY + rotX + rx) / denominator;
        double backLeftPower = (rotY - rotX + rx) / denominator;
        double frontRightPower = (rotY - rotX - rx) / denominator;
        double backRightPower = (rotY + rotX - rx) / denominator;

        RBHW.setMotorPower(Motors.FRONTLEFTMOTOR,frontLeftPower);
        RBHW.setMotorPower(Motors.FRONTRIGHTMOTOR,frontRightPower);
        RBHW.setMotorPower(Motors.BACKLEFTMOTOR,backLeftPower);
        RBHW.setMotorPower(Motors.BACKRIGHTMOTOR,backRightPower);
    }
    public void setAllDriveMotor(double power){
        RBHW.setMotorPower(Motors.FRONTLEFTMOTOR,power);
        RBHW.setMotorPower(Motors.FRONTRIGHTMOTOR,power);
        RBHW.setMotorPower(Motors.BACKLEFTMOTOR,power);
        RBHW.setMotorPower(Motors.BACKRIGHTMOTOR,power);
    }
}

