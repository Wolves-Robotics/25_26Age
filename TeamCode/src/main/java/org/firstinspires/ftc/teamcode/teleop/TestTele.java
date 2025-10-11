package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.enums.HardwareEnum;

@TeleOp
public class TestTele extends BaseTele {
    @Override
    protected void setInitCommands() {
        robot.addAction(
                () -> gamepad2.a,
                () -> robot.getRobotHardware().setMotorPower(HardwareEnum.turretMotor, 1),
                () -> robot.getRobotHardware().setMotorPower(HardwareEnum.turretMotor, 0));
        robot.addAction(
                () -> gamepad1.b,
                () -> robot.getRobotHardware().setMotorPower(HardwareEnum.forwardRight, 1),
                () -> robot.getRobotHardware().setMotorPower(HardwareEnum.forwardRight, 0));
        robot.addAction(
                () -> gamepad1.x,
                () -> robot.getRobotHardware().setMotorPower(HardwareEnum.backLeft, 1),
                () -> robot.getRobotHardware().setMotorPower(HardwareEnum.backLeft, 0));
        robot.addAction(
                () -> gamepad1.y,
                () -> robot.getRobotHardware().setMotorPower(HardwareEnum.backRight, 1),
                () -> robot.getRobotHardware().setMotorPower(HardwareEnum.backRight, 0));
    }

    @Override
    protected void setMainCommands() {
        robot.setDrivingAction();

        robot.addAction(
                () -> gamepad1.b,
                () -> robot.shootingSubsystem().setIntaking());

        robot.addAction(
                () -> gamepad1.x,
                () -> robot.shootingSubsystem().setOuttaking(true),
                () -> robot.shootingSubsystem().setOuttaking(false));


        robot.addAction(
                () -> true,
                () -> robot.shootingSubsystem().setTurretPower(gamepad2.left_stick_x),
                true);

        robot.addAction(
                () -> gamepad2.left_bumper,
                () -> robot.shootingSubsystem().setSpeedUp(true),
                () -> robot.shootingSubsystem().setSpeedUp(false));

        robot.addAction(
                () -> gamepad2.right_bumper,
                () -> robot.shootingSubsystem().setFiring(true),
                () -> robot.shootingSubsystem().setFiring(false));

    }
}
