package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.enums.HardwareEnum;

@TeleOp
public class TestTele extends BaseTele {
    @Override
    protected void setInitCommands() {
        robot.addAction(() -> gamepad1.a,
                () -> robot.getRobotHardware().setMotorPower(HardwareEnum.forwardLeft, 1),
                () -> robot.getRobotHardware().setMotorPower(HardwareEnum.forwardLeft, 0));
        robot.addAction(() -> gamepad1.b,
                () -> robot.getRobotHardware().setMotorPower(HardwareEnum.forwardRight, 1),
                () -> robot.getRobotHardware().setMotorPower(HardwareEnum.forwardRight, 0));
        robot.addAction(() -> gamepad1.x,
                () -> robot.getRobotHardware().setMotorPower(HardwareEnum.backLeft, 1),
                () -> robot.getRobotHardware().setMotorPower(HardwareEnum.backLeft, 0));
        robot.addAction(() -> gamepad1.y,
                () -> robot.getRobotHardware().setMotorPower(HardwareEnum.backRight, 1),
                () -> robot.getRobotHardware().setMotorPower(HardwareEnum.backRight, 0));
    }

    @Override
    protected void setMainCommands() {
        robot.setDrivingAction();
    }
}
