package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.enums.HardwareEnum;
import org.firstinspires.ftc.teamcode.hardware.Robot;

public class Testing implements TeleInterface {

    @Override
    public void setInitCommands(Robot robot, Gamepad gamepad1, Gamepad gamepad2) {
        robot.addAction(
                () -> gamepad1.left_bumper,
                () -> robot.getRobotHardware().setServoPosition(HardwareEnum.LATCH_SERVO, 0));

        robot.addAction(
                () -> gamepad1.right_bumper,
                () -> robot.getRobotHardware().setServoPosition(HardwareEnum.LIGHT, 0.5));

        robot.addAction(
                () -> true,
                () -> robot.getRobotHardware().setMotorPower(HardwareEnum.INTAKE_MOTOR, gamepad1.left_trigger),
                true);

        robot.addAction(
                () -> true,
                () -> robot.getRobotHardware().setMotorPower(HardwareEnum.FLYWHEEL_MOTOR2, gamepad1.right_trigger),
                true);
    }

    @Override
    public void setMainCommands(Robot robot, Gamepad gamepad1, Gamepad gamepad2) {
        robot.setDrivingActions();

        robot.addAction(
                () -> gamepad1.right_trigger > 0.75,
                () -> robot.shootingSubsystem().setIntaking(true),
                () -> robot.shootingSubsystem().setIntaking(false));

        robot.addAction(
                () -> gamepad1.x,
                () -> robot.shootingSubsystem().setOuttaking(true),
                () -> robot.shootingSubsystem().setOuttaking(false));

        robot.addAction(
                () -> gamepad1.a,
                () -> robot.driveSubsystem().setParking(true),
                () -> robot.driveSubsystem().setParking(false));

        robot.addAction(
                () -> gamepad1.share,
                () -> robot.getRobotHardware().resetFollowerPose(robot.getMatchSelection().getTeamColor()));

        robot.addAction(
                () -> gamepad1.left_bumper,
                () -> robot.shootingSubsystem().setSpeedUp(true),
                () -> robot.shootingSubsystem().setSpeedUp(false));

        robot.addAction(
                () -> gamepad1.left_trigger > 0.75,
                () -> robot.shootingSubsystem().setSpeedUp(true),
                () -> robot.shootingSubsystem().setSpeedUp(false));

        robot.addAction(
                () -> gamepad1.right_bumper,
                () -> robot.shootingSubsystem().setFiring(true),
                () -> robot.shootingSubsystem().setFiring(false));
    }
}
