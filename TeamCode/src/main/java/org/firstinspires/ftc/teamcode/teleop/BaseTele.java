package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.DashboardCore;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.bylazar.telemetry.JoinedTelemetry;
import com.bylazar.telemetry.PanelsTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.hardware.Robot;

public abstract class BaseTele extends OpMode {
    protected Robot robot;

    @Override
    final public void init() {
        robot = new Robot(gamepad1, gamepad2, new JoinedTelemetry(PanelsTelemetry.INSTANCE.getFtcTelemetry(), new MultipleTelemetry(FtcDashboard.getInstance().getTelemetry()), telemetry), hardwareMap);
        robot.initTeleop();

        robot.shootingSubsystem().setPidOn(false);

        setInitCommands();
    }

    protected abstract void setInitCommands();

    @Override
    public final void init_loop() {
        robot.update();
    }

    @Override
    public final void start() {
        robot.startMainLoop();

        setMainCommands();
    }

    protected abstract void setMainCommands();

    @Override
    public final void loop() {
        robot.update();
    }

    @Override
    public final void stop() {
        robot.clearActions();
    }
}