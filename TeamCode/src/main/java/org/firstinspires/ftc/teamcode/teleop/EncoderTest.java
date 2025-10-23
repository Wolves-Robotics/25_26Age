package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.hardware.Robot;

public class EncoderTest implements TeleInterface {
    @Override
    public void setInitCommands(Robot robot, Gamepad gamepad1, Gamepad gamepad2) {

    }

    @Override
    public void setMainCommands(Robot robot, Gamepad gamepad1, Gamepad gamepad2) {
        robot.addAction(
                () -> gamepad1.a,
                () -> robot.getRobotHardware().resetOffset()
        );
    }
}
