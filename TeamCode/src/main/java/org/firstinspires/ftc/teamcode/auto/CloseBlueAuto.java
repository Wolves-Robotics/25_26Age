package org.firstinspires.ftc.teamcode.auto;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.Robot;

public class CloseBlueAuto implements AutoInterface {
    private int index = 0;
    private PathChain startToShooting, shootingToPPG, PPGToShooting,
                                       PPGToLever, leverToShooting,
                                       shootingToPGP, PGPToShooting,
                                       shootingToGPP, GPPToShooting,
                                       ahhhhhhh;

    private boolean started = false, finished = false;
    private ElapsedTime elapsedTime;

    public CloseBlueAuto(Robot robot) {
        elapsedTime = new ElapsedTime();

        Follower follower = robot.getRobotHardware().getFollower();

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
                                new Pose(19, 83.600)
                        )
                )
                .setTangentHeadingInterpolation()
                .build();

        PPGToShooting = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(19.5, 83.600), new Pose(59.1, 98.500))
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
                .build();

        PPGToLever = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(19.5, 83.600),
                                new Pose(34, 80.200),
                                new Pose(17, 76.500)
                        )
                )
                .setLinearHeadingInterpolation(180, Math.toRadians(90))
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
                        new BezierLine(new Pose(51, 65.000), new Pose(9.5, 59.000))
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .setBrakingStart(1.7)
                .build();

        PGPToShooting = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(9.5, 59.000),
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
                        new BezierLine(new Pose(59.1, 98.500), new Pose(51, 40.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(225), Math.toRadians(180))
                .setBrakingStart(1.7)

                .addPath(
                        new BezierLine(new Pose(51, 40.000), new Pose(9.5, 35.000))
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))

                .setBrakingStart(1.7)
                .build();

        GPPToShooting = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(9.5, 35.000),
                                new Pose(29.8, 35.600),
                                new Pose(50.1, 89.700),
                                new Pose(59.1, 98.500)
                        )
                )
                .setTangentHeadingInterpolation()
                .setReversed()
                .build();

        ahhhhhhh = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(59.1, 98.500), new Pose(59.1, 105.000))
                )
                .setConstantHeadingInterpolation(Math.toRadians(225))
                .build();
    }

    @Override
    public void update(Robot robot) {
        switch (index) {
            case 0:
                if (!started) {
                    robot.getRobotHardware().getFollower().followPath(startToShooting);
                    robot.shootingSubsystem().setSpeedUp(true, 1640);
                    started = true;

                }

                if (!robot.getRobotHardware().getFollower().isBusy() && !finished) {
                    finished = true;
                    elapsedTime.reset();
                }

                if (finished && elapsedTime.milliseconds() > 200) {
                    index++;
                    started = false;
                    finished = false;
                }
                break;
            case 1:
                if (!started) {
                    robot.shootingSubsystem().setFiring(true);
                    started = true;

                }

                if (!robot.getRobotHardware().getFollower().isBusy() && !finished) {
                    finished = true;
                    elapsedTime.reset();
                }

                if (finished && elapsedTime.milliseconds() > 1000) {
                    robot.shootingSubsystem().setSpeedUp(false);
                    index++;
                    started = false;
                    finished = false;
                }
                break;
            case 2:
                if (!started) {
                    robot.getRobotHardware().getFollower().followPath(shootingToPPG);
                    robot.shootingSubsystem().setIntaking(true);
                    started = true;
                }

                if (!robot.getRobotHardware().getFollower().isBusy() && !finished) {
                    finished = true;
                    elapsedTime.reset();
                }

                if (finished && elapsedTime.milliseconds() > 200) {
                    robot.shootingSubsystem().setIntaking(false);
                    index++;
                    started = false;
                    finished = false;
                }
                break;
            case 3:
                if (!started) {
                    robot.getRobotHardware().getFollower().followPath(PPGToLever);
                    started = true;
                }

                if (!robot.getRobotHardware().getFollower().isBusy() && !finished) {
                    finished = true;
                    elapsedTime.reset();
                }

                if (finished && elapsedTime.milliseconds() > 200) {
                    index++;
                    started = false;
                    finished = false;
                }
                break;
            case 4:
                if (!started) {
                    robot.getRobotHardware().getFollower().followPath(leverToShooting);
                    robot.shootingSubsystem().setSpeedUp(true, 1640);
                    started = true;
                }

                if (!robot.getRobotHardware().getFollower().isBusy() && !finished) {
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
                    robot.shootingSubsystem().setFiring(true);
                    started = true;
                }

                if (!robot.getRobotHardware().getFollower().isBusy() && !finished) {
                    finished = true;
                    elapsedTime.reset();
                }

                if (finished && elapsedTime.milliseconds() > 1000) {
                    robot.shootingSubsystem().setSpeedUp(false);
                    index++;
                    started = false;
                    finished = false;
                }
                break;
            case 6:
                if (!started) {
                    robot.getRobotHardware().getFollower().followPath(shootingToPGP);
                    robot.shootingSubsystem().setIntaking(true);
                    started = true;
                }

                if (!robot.getRobotHardware().getFollower().isBusy() && !finished) {
                    finished = true;
                    elapsedTime.reset();
                }

                if (finished && elapsedTime.milliseconds() > 200) {
                    robot.shootingSubsystem().setIntaking(false);
                    index++;
                    started = false;
                    finished = false;
                }
                break;
            case 7:
                if (!started) {
                    robot.getRobotHardware().getFollower().followPath(PGPToShooting);
                    robot.shootingSubsystem().setSpeedUp(true, 1640);
                    started = true;
                }

                if (!robot.getRobotHardware().getFollower().isBusy() && !finished) {
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
                    robot.shootingSubsystem().setFiring(true);
                    started = true;
                }

                if (!robot.getRobotHardware().getFollower().isBusy() && !finished) {
                    finished = true;
                    elapsedTime.reset();
                }

                if (finished && elapsedTime.milliseconds() > 1000) {
                    robot.shootingSubsystem().setSpeedUp(false);
                    index++;
                    started = false;
                    finished = false;
                }
                break;
            case 9:
                if (!started) {
                    robot.getRobotHardware().getFollower().followPath(shootingToGPP);
                    robot.shootingSubsystem().setIntaking(true);
                    started = true;
                }

                if (!robot.getRobotHardware().getFollower().isBusy() && !finished) {
                    finished = true;
                    elapsedTime.reset();
                }

                if (finished && elapsedTime.milliseconds() > 200) {
                    robot.shootingSubsystem().setIntaking(false);
                    index++;
                    started = false;
                    finished = false;
                }
                break;
            case 10:
                if (!started) {
                    robot.getRobotHardware().getFollower().followPath(GPPToShooting);
                    robot.shootingSubsystem().setSpeedUp(true, 1640);
                    started = true;
                }

                if (!robot.getRobotHardware().getFollower().isBusy() && !finished) {
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
                    robot.shootingSubsystem().setFiring(true);
                    started = true;
                }

                if (!robot.getRobotHardware().getFollower().isBusy() && !finished) {
                    finished = true;
                    elapsedTime.reset();
                }

                if (finished && elapsedTime.milliseconds() > 1000) {
                    robot.shootingSubsystem().setSpeedUp(false);
                    index++;
                    started = false;
                    finished = false;
                }
                break;
            case 12:
                if (!started) {
                    robot.getRobotHardware().getFollower().followPath(ahhhhhhh);
                    started = true;
                }

                if (!robot.getRobotHardware().getFollower().isBusy() && !finished) {
                    finished = true;
                    elapsedTime.reset();
                }

                if (finished && elapsedTime.milliseconds() >= 0) {
                    index++;
                    started = false;
                    finished = false;
                }
                break;
        }
    }
}
