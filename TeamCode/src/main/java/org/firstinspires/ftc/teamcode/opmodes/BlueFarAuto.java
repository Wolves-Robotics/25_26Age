package org.firstinspires.ftc.teamcode.opmodes;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Switchback;
import org.firstinspires.ftc.teamcode.utils.config.Constants;
import org.firstinspires.ftc.teamcode.utils.enums.Alliance;
import org.firstinspires.ftc.teamcode.utils.ExternalTools;
import org.firstinspires.ftc.teamcode.utils.config.MatchDetails;
import org.firstinspires.ftc.teamcode.utils.enums.RobotState;

@Autonomous
public class BlueFarAuto extends OpMode {
    private Switchback switchback;

    private PathChain bhhh;

    private boolean started = false, finished = false;
    private int index = 0;
    private ElapsedTime elapsedTime;

    @Override
    public void init() {
        MatchDetails.ResetDetails();

        MatchDetails.ALLIANCECOLOR = Alliance.RED;

        switchback = Switchback.getInstance();
        switchback.init(this);

        switchback.setPose(Constants.BLUE_FAR_INIT);

        switchback.getDriveSub().startFollowing();


        elapsedTime = new ElapsedTime();

        Follower follower = switchback.getFollower();

        bhhh = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(48, 7.08661), new Pose(33, 8))
                )
                .setConstantHeadingInterpolation(Math.toRadians(90))
                .build();
    }

    @Override
    public void init_loop() {
        switchback.read();

        if (gamepad1.aWasPressed()) {
            switchback.getTurretSub().resetEncoder();
        }

        if (gamepad1.bWasPressed()) {
            switchback.getTurretSub().setZeroToForwardAngle();
        }

        ExternalTools.TELEMETRY.addData("Angle", MatchDetails.ZeroToForwardAngle);

        switchback.write();
    }

    @Override
    public void loop() {
        switchback.read();


        switch (index) {
            case 0:
                if (!started) {
                    switchback.changeState(RobotState.FIRE);
                    started = true;
                }

                if (!switchback.getFollower().isBusy() && !finished) {
                    finished = true;
                    elapsedTime.reset();
                }

                if (finished && elapsedTime.milliseconds() > 5000) {
                    switchback.changeState(RobotState.IDLE);
                    index++;
                    started = false;
                    finished = false;
                }
                break;
            case 1:
                if (!started) {
                    switchback.getFollower().followPath(bhhh);
                    started = true;
                }

                if (!switchback.getFollower().isBusy() && !finished) {
                    finished = true;
                    elapsedTime.reset();
                }

                if (finished && elapsedTime.milliseconds() > 500) {
                    index++;
                    started = false;
                    finished = false;
                }
                break;
        }

        switchback.update();

        switchback.write();
    }

    @Override
    public void stop() {
        switchback.setFinalPose();
    }
}
