package org.firstinspires.ftc.teamcode.opmodes;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
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
public class BlueCloseAuto extends OpMode {
    private Switchback switchback;

    private PathChain startToShooting, shootingToPPG,
            PPGToLever, leverToShooting,
            shootingToPGP, PGPToShooting,
            shootingToGPP, GPPToShooting;

    private boolean started = false, finished = false;
    private int index = 0;
    private ElapsedTime elapsedTime;

    @Override
    public void init() {
        MatchDetails.ResetDetails();

        MatchDetails.ALLIANCECOLOR = Alliance.BLUE;

        switchback = Switchback.getInstance();
        switchback.init(this);

        switchback.setPose(Constants.BLUE_CLOSE_INIT);

        switchback.getDriveSub().startFollowing();

        elapsedTime = new ElapsedTime();

        Follower follower = switchback.getFollower();

        startToShooting = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(24, 127.700), new Pose(59.1, 98.500))
                )
                .setLinearHeadingInterpolation(Math.toRadians(143), Math.toRadians(135))
                .build();

        shootingToPPG = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(59.1, 98.500),
                                new Pose(61, 91.000),
                                new Pose(17.7, 83.00)
                        )
                )
                .setTangentHeadingInterpolation()
                .build();

        PPGToLever = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(19.5, 83.600),
                                new Pose(34, 80.200),
                                new Pose(21.5, 76.500)
                        )
                )
                .setLinearHeadingInterpolation(180, Math.toRadians(180))
                .build();

        leverToShooting = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(17, 74.500),
                                new Pose(33.5, 74.500),
                                new Pose(59.1, 98.500)
                        )
                )
                .setTangentHeadingInterpolation()
                .setReversed()
                .build();

        shootingToPGP = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(59.1, 98.500), new Pose(51, 65.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(225), Math.toRadians(180))
                .setBrakingStart(1.7)

                .addPath(
                        new BezierLine(new Pose(51, 65.000), new Pose(11.6, 59.000))
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .setBrakingStart(1.7)
                .build();

        PGPToShooting = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(11.6, 59.000),
                                new Pose(26, 59.700),
                                new Pose(59.1, 98.500)
                        )
                )
                .setTangentHeadingInterpolation()
                .setReversed()
                .build();

        shootingToGPP = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(59.1, 98.500), new Pose(51, 42.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(225), Math.toRadians(180))
                .setBrakingStart(1.7)

                .addPath(
                        new BezierLine(new Pose(51, 42.000), new Pose(11.6, 39.000))
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))

                .setBrakingStart(1.7)
                .build();

        GPPToShooting = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(11.6, 35.000),
                                new Pose(29.8, 35.600),
                                new Pose(50.1, 89.700),
                                new Pose(64, 105)
                        )
                )
                .setTangentHeadingInterpolation()
                .setReversed()
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
                    switchback.getFollower().followPath(startToShooting);
                    switchback.changeState(RobotState.SPEED_UP);
                    started = true;

                }

                if (!switchback.getFollower().isBusy() && !finished) {
                    finished = true;
                    elapsedTime.reset();
                }

                if (finished && elapsedTime.milliseconds() > 700) {
                    index++;
                    started = false;
                    finished = false;
                }
                break;
            case 1:
                if (!started) {
                    switchback.changeState(RobotState.FIRE);
                    started = true;

                }

                if (!switchback.getFollower().isBusy() && !finished) {
                    finished = true;
                    elapsedTime.reset();
                }

                if (finished && elapsedTime.milliseconds() > 1800) {
                    switchback.changeState(RobotState.IDLE);
                    index++;
                    started = false;
                    finished = false;
                }
                break;
            case 2:
                if (!started) {
                    switchback.getFollower().followPath(shootingToPPG);
                    switchback.changeState(RobotState.INTAKE);
                    started = true;
                }

                if (!switchback.getFollower().isBusy() && !finished) {
                    finished = true;
                    elapsedTime.reset();
                }

                if (finished && elapsedTime.milliseconds() > 100) {
                    switchback.changeState(RobotState.IDLE);
                    index++;
                    started = false;
                    finished = false;
                }
                break;
            case 3:
                if (!started) {
                    switchback.getFollower().followPath(PPGToLever);
                    started = true;
                }

                if (!switchback.getFollower().isBusy() && !finished) {
                    finished = true;
                    elapsedTime.reset();
                }

                if (finished && elapsedTime.milliseconds() > 100) {
                    index++;
                    started = false;
                    finished = false;
                }
                break;
            case 4:
                if (!started) {
                    switchback.getFollower().followPath(leverToShooting);
                    switchback.changeState(RobotState.SPEED_UP);
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
            case 5:
                if (!started) {
                    switchback.changeState(RobotState.FIRE);
                    started = true;
                }

                if (!switchback.getFollower().isBusy() && !finished) {
                    finished = true;
                    elapsedTime.reset();
                }

                if (finished && elapsedTime.milliseconds() > 1500) {
                    switchback.changeState(RobotState.IDLE);
                    index++;
                    started = false;
                    finished = false;
                }
                break;
            case 6:
                if (!started) {
                    switchback.getFollower().followPath(shootingToPGP);
                    switchback.changeState(RobotState.INTAKE);
                    started = true;
                }

                if (!switchback.getFollower().isBusy() && !finished) {
                    finished = true;
                    elapsedTime.reset();
                }

                if (finished && elapsedTime.milliseconds() > 100) {
                    switchback.changeState(RobotState.IDLE);
                    index++;
                    started = false;
                    finished = false;
                }
                break;
            case 7:
                if (!started) {
                    switchback.getFollower().followPath(PGPToShooting);
                    switchback.changeState(RobotState.SPEED_UP);
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
            case 8:
                if (!started) {
                    switchback.changeState(RobotState.FIRE);
                    started = true;
                }

                if (!switchback.getFollower().isBusy() && !finished) {
                    finished = true;
                    elapsedTime.reset();
                }

                if (finished && elapsedTime.milliseconds() > 1500) {
                    switchback.changeState(RobotState.IDLE);
                    index++;
                    started = false;
                    finished = false;
                }
                break;
            case 9:
                if (!started) {
                    switchback.getFollower().followPath(shootingToGPP);
                    switchback.changeState(RobotState.INTAKE);
                    started = true;
                }

                if (!switchback.getFollower().isBusy() && !finished) {
                    finished = true;
                    elapsedTime.reset();
                }

                if (finished && elapsedTime.milliseconds() > 100) {
                    switchback.changeState(RobotState.IDLE);
                    index++;
                    started = false;
                    finished = false;
                }
                break;
            case 10:
                if (!started) {
                    switchback.getFollower().followPath(GPPToShooting);
                    switchback.changeState(RobotState.SPEED_UP);
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
            case 11:
                if (!started) {
                    switchback.changeState(RobotState.FIRE);
                    started = true;
                }

                if (!switchback.getFollower().isBusy() && !finished) {
                    finished = true;
                    elapsedTime.reset();
                }

                if (finished && elapsedTime.milliseconds() > 1800) {
                    switchback.changeState(RobotState.IDLE);
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
