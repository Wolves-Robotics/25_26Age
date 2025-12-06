package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.bylazar.telemetry.JoinedTelemetry;
import com.bylazar.telemetry.PanelsTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.enums.Color;
import org.firstinspires.ftc.teamcode.hardware.Robot;

@TeleOp
public class Tele extends OpMode {
    private Robot robot;
    private TeleInterface teleInterface;

    @Override
    public final void init() {
        robot = new Robot(gamepad1, gamepad2, new JoinedTelemetry(PanelsTelemetry.INSTANCE.getFtcTelemetry(), new MultipleTelemetry(FtcDashboard.getInstance().getTelemetry()), telemetry), hardwareMap);
        robot.initTeleop();

        robot.getRobotHardware().setFollowerPose(robot.getMatchSelection().getLastPose());
        robot.shootingSubsystem().setTargetPose(robot.getMatchSelection().getTeamColor());

        robot.getRobotHardware().setPipeLine(
                robot.getMatchSelection().getTeamColor() == Color.BLUE ?
                        0 : 1
        );

        switch (robot.getMatchSelection().getTeleOps()) {
            case COMPETITION:
                teleInterface = new Competition();
                break;
            case TESTING:
                teleInterface = new Testing();
                break;
        }

        teleInterface.setInitCommands(robot, gamepad1, gamepad2);
    }

    @Override
    public final void init_loop() {
        robot.update();
    }

    @Override
    public final void start() {
        robot.startMainLoop();

        teleInterface.setMainCommands(robot, gamepad1, gamepad2);
    }

    @Override
    public final void loop() {
        robot.update();
    }

    @Override
    public final void stop() {
        robot.clearActions();
    }
}