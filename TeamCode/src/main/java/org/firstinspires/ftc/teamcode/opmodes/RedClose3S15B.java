package org.firstinspires.ftc.teamcode.opmodes;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.utils.config.Constants;
import org.firstinspires.ftc.teamcode.utils.control.actions.ChangeStateAction;
import org.firstinspires.ftc.teamcode.utils.control.actions.FollowAction;
import org.firstinspires.ftc.teamcode.utils.control.actions.SleepAction;
import org.firstinspires.ftc.teamcode.utils.enums.Alliance;
import org.firstinspires.ftc.teamcode.utils.enums.RobotState;

public class RedClose3S15B extends BaseAuto {
    @Override
    protected Alliance setColor() {
        return Alliance.RED;
    }

    @Override
    protected Pose setPose() {
        return Constants.RED_CLOSE_INIT;
    }

    @Override
    protected void setActionList() {
        Pose shootingPose = new Pose(96.000, 86.000);

        PathChain startToShoot = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(120.000, 127.700),

                                shootingPose
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(37), Math.toRadians(0))

                .build();

        PathChain shootToSec = follower.pathBuilder().addPath(
                        new BezierCurve(
                                shootingPose,
                                new Pose(93.700, 63.606),
                                new Pose(132.000, 60.000)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(0))

                .build();

        PathChain secToShoot = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(132.000, 60.000),
                                new Pose(104.500, 66.500),
                                shootingPose
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(0))

                .build();

        PathChain openTunnel = follower.pathBuilder().addPath(
                        new BezierCurve(
                                shootingPose,
                                new Pose(104.500, 66.500),
                                new Pose(128.462, 69.741)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), 0.18162244)

                .build();

        PathChain tunnelIntake = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(128.462, 69.741),

                                new Pose(133.127, 57.175)
                        )
                ).setLinearHeadingInterpolation(0.18162244, 0.790274)

                .build();

        PathChain tunnelToShoot = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(133.127, 57.175),
                                new Pose(112.600, 54.800),
                                shootingPose
                        )
                ).setLinearHeadingInterpolation(0.790274, Math.toRadians(0))

                .build();

        PathChain shootToThird = follower.pathBuilder().addPath(
                        new BezierCurve(
                                shootingPose,
                                new Pose(81.000, 40.200),
                                new Pose(132.000, 36.500)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(0))

                .build();

        PathChain thirdToShoot = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(132.000, 36.500),

                                shootingPose
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(0))

                .build();

        PathChain shootToFirst = follower.pathBuilder().addPath(
                        new BezierLine(
                                shootingPose,

                                new Pose(127.000, 84.500)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(0))

                .build();

        PathChain firstToShoot = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(127.000, 84.500),

                                shootingPose
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(0))

                .build();

        PathChain park = follower.pathBuilder().addPath(
                        new BezierLine(
                                shootingPose,

                                shootingPose.withY(125)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(0))

                .build();


        addAction(
                new ChangeStateAction(RobotState.SPEED_UP),
                new FollowAction(startToShoot, () -> follower.getPose().getY() < 86.5),
                new SleepAction(1000),
                new ChangeStateAction(RobotState.FIRE),
                new SleepAction(800),
                new ChangeStateAction(RobotState.IDLE),

                new ChangeStateAction(RobotState.INTAKE),
                new FollowAction(shootToSec, () -> follower.getPose().getX() > 131.0),
                new SleepAction(450),
                new ChangeStateAction(RobotState.IDLE),
                new SleepAction(250),

                new ChangeStateAction(RobotState.SPEED_UP),
                new FollowAction(secToShoot, () -> follower.getPose().getX() < 96.5),
                new SleepAction(1000),
                new ChangeStateAction(RobotState.FIRE),
                new SleepAction(1000),
                new ChangeStateAction(RobotState.IDLE),

                new ChangeStateAction(RobotState.INTAKE),
                new FollowAction(openTunnel, () -> follower.getPose().getX() > 127.5),
                new SleepAction(400),
                new FollowAction(tunnelIntake),
                new SleepAction(1000),
                new ChangeStateAction(RobotState.IDLE),
                new SleepAction(250),

                new ChangeStateAction(RobotState.SPEED_UP),
                new FollowAction(tunnelToShoot, () -> follower.getPose().getX() < 96.5),
                new SleepAction(1000),
                new ChangeStateAction(RobotState.FIRE),
                new SleepAction(1000),
                new ChangeStateAction(RobotState.IDLE),

                new ChangeStateAction(RobotState.INTAKE),
                new FollowAction(shootToThird, () -> follower.getPose().getX() > 131.0),
                new SleepAction(500),
                new ChangeStateAction(RobotState.IDLE),
                new SleepAction(250),

                new ChangeStateAction(RobotState.SPEED_UP),
                new FollowAction(thirdToShoot, () -> follower.getPose().getX() < 96.5),
                new SleepAction(1000),
                new ChangeStateAction(RobotState.FIRE),
                new SleepAction(800),
                new ChangeStateAction(RobotState.IDLE),

                new ChangeStateAction(RobotState.INTAKE),
                new FollowAction(shootToFirst, () -> follower.getPose().getX() > 126.0),
                new SleepAction(500),
                new ChangeStateAction(RobotState.IDLE),
                new SleepAction(250),

                new ChangeStateAction(RobotState.SPEED_UP),
                new FollowAction(firstToShoot, () -> follower.getPose().getX() < 96.5),
                new SleepAction(1000),
                new ChangeStateAction(RobotState.FIRE),
                new SleepAction(1000),
                new ChangeStateAction(RobotState.IDLE),

                new FollowAction(park)
        );
    }
}
