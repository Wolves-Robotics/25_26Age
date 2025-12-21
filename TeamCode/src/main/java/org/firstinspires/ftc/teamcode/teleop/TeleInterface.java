package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.hardware.Robot;

public interface TeleInterface {
    void setInitCommands(Robot robot, Gamepad gamepad1, Gamepad gamepad2);
    void setMainCommands(Robot robot, Gamepad gamepad1, Gamepad gamepad2);
}
