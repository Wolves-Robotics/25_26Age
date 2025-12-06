package org.firstinspires.ftc.teamcode.auto;

import com.bylazar.telemetry.JoinedTelemetry;
import com.bylazar.telemetry.PanelsTelemetry;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.enums.Color;
import org.firstinspires.ftc.teamcode.hardware.Robot;

@Autonomous(preselectTeleOp = "Tele")
public class Auto extends OpMode {
    private Robot robot;
    private AutoInterface autoInterface;

    private int patternID;

    @Override
    public final void init() {
        robot = new Robot(gamepad1, gamepad2, new JoinedTelemetry(PanelsTelemetry.INSTANCE.getFtcTelemetry(), telemetry), hardwareMap);
        robot.initAuto();
        robot.initHardwareSelection();

        robot.getRobotHardware().setPipeLine(2);

        patternID = 21;
    }

    @Override
    public final void init_loop() {
        robot.update();

        LLResult result = robot.getRobotHardware().getLLResult();
        if (result != null && result.isValid()) {
            patternID = result.getFiducialResults().get(0).getFiducialId();
        }
    }

    @Override
    public final void start() {
        robot.startMainLoop();

        robot.getRobotHardware().setPipeLine(
                robot.getMatchSelection().getTeamColor() == Color.BLUE ?
                        0 : 1
        );

        robot.getRobotHardware().setFollowerPose(robot.getMatchSelection().getTeamColor());
        robot.shootingSubsystem().setTargetPose(robot.getMatchSelection().getTeamColor());

        switch (robot.getMatchSelection().getAutos()) {
            case CLOSE_AUTO:
                if (robot.getMatchSelection().getTeamColor() == Color.RED) {
                    autoInterface = new CloseRedAuto(robot);
                } else {
                    autoInterface = new CloseBlueAuto(robot);
                }
                break;
            case FAR_AUTO:
                autoInterface = new FarAuto();
                break;
        }
    }

    @Override
    public final void loop() {
        autoInterface.update(robot);

        robot.update();
    }

    @Override
    public final void stop() {
        robot.clearActions();
        robot.getMatchSelection().setLastPose(robot.getRobotHardware().getCurrentPose());
    }
}