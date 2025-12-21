package org.firstinspires.ftc.teamcode.auto;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.Robot;

public class OPENBlue implements AutoInterface {
    private int index = 0;
    private PathChain startToShooting, shootingToPPG, PPGToLever, leverToShooting;

    private boolean started = false, finished = false;
    private ElapsedTime elapsedTime;

    public OPENBlue(Robot robot) {
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
                                new Pose(19.5, 83.00)
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
                                new Pose(22, 76.500)
                        )
                )
                .setLinearHeadingInterpolation(180, Math.toRadians(180))
                .build();

        leverToShooting = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(22, 76.500),
                                new Pose(33.5, 74.500),
                                new Pose(59.1, 120.500)
                        )
                )
                .setTangentHeadingInterpolation()
                .setReversed()
                .build();
    }

    @Override
    public void update(Robot robot) {
        switch (index) {
            case 0:
                if (!started) {
                    elapsedTime.reset();
                    started = true;
                }

                if (started && elapsedTime.milliseconds() > 3000) {
                    index++;
                    started = false;
                    finished = false;
                }
                break;
            case 1:
                if (!started) {
                    robot.getRobotHardware().getFollower().followPath(startToShooting);
                    robot.shootingSubsystem().setSpeedUp(true);
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
            case 2:
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
            case 3:
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
            case 4:
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
            case 5:
                if (!started) {
                    robot.getRobotHardware().getFollower().followPath(leverToShooting);
                    robot.shootingSubsystem().setSpeedUp(true);
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
            case 6:
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
        }
    }
}
