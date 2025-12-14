package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.hardware.Robot;

public class Competition implements TeleInterface{
    @Override
    public void setInitCommands(Robot robot, Gamepad gamepad1, Gamepad gamepad2) {

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
                () -> gamepad2.left_bumper,
                () -> robot.shootingSubsystem().setSpeedUp(true),
                () -> robot.shootingSubsystem().setSpeedUp(false));

        robot.addAction(
                () -> gamepad2.left_trigger > 0.75,
                () -> robot.shootingSubsystem().setSpeedUp(true),
                () -> robot.shootingSubsystem().setSpeedUp(false));

        robot.addAction(
                () -> gamepad2.right_bumper,
                () -> robot.shootingSubsystem().setFiring(true),
                () -> robot.shootingSubsystem().setFiring(false));
    }
}
