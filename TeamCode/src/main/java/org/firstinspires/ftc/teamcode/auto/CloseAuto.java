package org.firstinspires.ftc.teamcode.auto;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

public class CloseAuto implements AutoInterface {
    private int index = 0;
    private PathChain startToShooting, shootingToPPG, PPGToShooting;

    private boolean started = false, finished = false;
    private ElapsedTime elapsedTime;

    public CloseAuto(Robot robot) {
        elapsedTime = new ElapsedTime();

        Follower follower = robot.getRobotHardware().getFollower();

        startToShooting = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(120.000, 127.700), new Pose(84.900, 98.500))
                )
                .setLinearHeadingInterpolation(Math.toRadians(37), Math.toRadians(45))
                .build();

        shootingToPPG = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(84.900, 98.500),
                                new Pose(83.000, 91.000),
                                new Pose(124.500, 83.600)
                        )
                )
                .setTangentHeadingInterpolation()
                .build();

        PPGToShooting = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(124.500, 83.600), new Pose(84.900, 98.500))
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(45))
                .build();
    }

    @Override
    public void update(Robot robot) {
        switch (index) {
            case 0:
                if (!started) {
                    robot.getRobotHardware().getFollower().followPath(startToShooting);
                    robot.shootingSubsystem().setSpeedUp(true, 0.72);
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
            case 1:
                if (!started) {
                    robot.shootingSubsystem().setFiring(true);
                    elapsedTime.reset();
                    started = true;
                    Motor test = new Motor();
                    Motor.RunMode test2 = Motor.RunMode.VelocityControl;
                }

                if (elapsedTime.milliseconds() > 2500) {
                    robot.shootingSubsystem().setSpeedUp(false);
                    started = false;
                    index++;
                }

                break;
            case 2:
                if (!started) {
                    robot.getRobotHardware().getFollower().followPath(shootingToPPG);
                    robot.shootingSubsystem().setIntaking(0.7);
                    started = true;
                }
                break;
        }
    }
}
