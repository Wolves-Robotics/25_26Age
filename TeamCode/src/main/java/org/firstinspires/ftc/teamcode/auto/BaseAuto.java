package org.firstinspires.ftc.teamcode.auto;

import com.bylazar.telemetry.JoinedTelemetry;
import com.bylazar.telemetry.PanelsTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.hardware.Robot;

public abstract class BaseAuto extends OpMode {
    protected Robot robot;

    @Override
    final public void init() {
        robot = new Robot(gamepad1, gamepad2, new JoinedTelemetry(PanelsTelemetry.INSTANCE.getFtcTelemetry(), telemetry), hardwareMap);
        robot.initAuto();
        robot.initHardwareSelection();

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