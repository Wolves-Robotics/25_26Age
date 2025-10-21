package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.enums.HardwareEnum;
import org.firstinspires.ftc.teamcode.hardware.Robot;

public class Testing implements TeleInterface {

    @Override
    public void setInitCommands(Robot robot, Gamepad gamepad1, Gamepad gamepad2) {
        robot.addAction(
                () -> gamepad1.a,
                () -> robot.getRobotHardware().setPipeLine(1));

        robot.addAction(
                () -> gamepad1.x,
                () -> robot.getRobotHardware().setPipeLine(0));

        robot.addAction(
                () -> gamepad1.b,
                () -> robot.getRobotHardware().setPipeLine(2));
    }

    @Override
    public void setMainCommands(Robot robot, Gamepad gamepad1, Gamepad gamepad2) {
        robot.setDrivingActions();

        robot.addAction(
                () -> true,
                () -> robot.shootingSubsystem().setIntaking(gamepad1.right_trigger),
                true);

        robot.addAction(
                () -> gamepad1.x,
                () -> robot.shootingSubsystem().setOuttaking(true),
                () -> robot.shootingSubsystem().setOuttaking(false));

        robot.addAction(
                () -> gamepad1.a,
                () -> robot.driveSubsystem().setParking(true),
                () -> robot.driveSubsystem().setParking(false));

        robot.addAction(
                () -> gamepad1.y,
                ()-> robot.shootingSubsystem().toggleIntake());


        robot.addAction(
                () -> gamepad1.left_bumper,
                () -> robot.shootingSubsystem().setSpeedUp(true, 0.8),
                () -> robot.shootingSubsystem().setSpeedUp(false));

        robot.addAction(
                () -> gamepad1.left_trigger > 0.75,
                () -> robot.shootingSubsystem().setSpeedUp(true, 1),
                () -> robot.shootingSubsystem().setSpeedUp(false));

        robot.addAction(
                () -> gamepad1.right_bumper,
                () -> robot.shootingSubsystem().setFiring(true),
                () -> robot.shootingSubsystem().setFiring(false));
    }
}
