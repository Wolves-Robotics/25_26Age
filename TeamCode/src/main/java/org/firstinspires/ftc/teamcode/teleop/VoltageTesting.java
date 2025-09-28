package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;


@TeleOp
public class VoltageTesting extends OpMode {
    private DcMotorEx frontLeft, frontRight, backLeft, backRight;
    private boolean brake;

    private ElapsedTime elapsedTime;


    @Override
    public void init() {
        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);

        brake = false;

        elapsedTime = new ElapsedTime();
    }

    @Override
    public void loop() {
        double y = -gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x * 1.1;
        double rx = gamepad1.right_stick_x;

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        frontLeft.setPower(frontLeftPower);
        backLeft.setPower(backLeftPower);
        frontRight.setPower(frontRightPower);
        backRight.setPower(backRightPower);

        if (gamepad1.a && elapsedTime.milliseconds() > 300) {
            elapsedTime.reset();
            brake = !brake;

            if (brake) {
                frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            } else {
                frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            }
        }

        double frontLeftVoltage = frontLeft.getCurrent(CurrentUnit.AMPS);
        double frontRightVoltage = frontRight.getCurrent(CurrentUnit.AMPS);
        double backLeftVoltage = backLeft.getCurrent(CurrentUnit.AMPS);
        double backRightVoltage = backRight.getCurrent(CurrentUnit.AMPS);

        telemetry.addData("Brake", brake);

        telemetry.addData("Front Left Voltage", frontLeftVoltage);
        telemetry.addData("Front Right Voltage", frontRightVoltage);
        telemetry.addData("Back Left Voltage", backLeftVoltage);
        telemetry.addData("Back Right Voltage", backRightVoltage);

        telemetry.addData("Average Voltage", (frontLeftVoltage + frontRightVoltage + backLeftVoltage + backRightVoltage) / 4);

        telemetry.update();
    }
}
