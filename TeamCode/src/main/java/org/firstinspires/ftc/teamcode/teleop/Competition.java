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


//        robot.addAction(
//                () -> true,
//                () -> robot.shootingSubsystem().setTurretPower(gamepad2.left_stick_x),
//                true);

        robot.addAction(
                () -> gamepad2.dpad_left,
                () -> robot.shootingSubsystem().setTarget(67));

        robot.addAction(
                () -> gamepad2.dpad_up,
                () -> robot.shootingSubsystem().setTarget(167));

        robot.addAction(
                () -> gamepad2.dpad_right,
                () -> robot.shootingSubsystem().setTarget(284));


        robot.addAction(
                () -> gamepad2.left_bumper,
                () -> robot.shootingSubsystem().setSpeedUp(true, 0.8),
                () -> robot.shootingSubsystem().setSpeedUp(false));

        robot.addAction(
                () -> gamepad2.left_trigger > 0.75,
                () -> robot.shootingSubsystem().setSpeedUp(true, 1),
                () -> robot.shootingSubsystem().setSpeedUp(false));

        robot.addAction(
                () -> gamepad2.right_bumper,
                () -> robot.shootingSubsystem().setFiring(true),
                () -> robot.shootingSubsystem().setFiring(false));
    }
}
