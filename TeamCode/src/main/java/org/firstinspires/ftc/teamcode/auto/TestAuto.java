package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.hardware.Robot;

@Autonomous
public class TestAuto extends BaseAuto {
    @Override
    protected void setInitCommands() {
        robot.addAction(()->gamepad1.a, ()->telemetry.addLine("whyyyyy"), true);
    }

    @Override
    protected void setMainCommands() {
        robot.addAction(()->gamepad1.b, ()->telemetry.addData("pressed", true), ()->telemetry.addData("pressed", false));
    }
}
