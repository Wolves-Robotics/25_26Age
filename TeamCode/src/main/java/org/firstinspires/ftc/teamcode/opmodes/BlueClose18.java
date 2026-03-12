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

public class BlueClose18 extends BaseAuto {
    @Override
    protected Alliance setColor() {
        return Alliance.BLUE;
    }

    @Override
    protected Pose setPose() {
        return Constants.BLUE_CLOSE_INIT;
    }

    @Override
    protected void setActionList() {
        Pose shootingPose = new Pose(48.000, 86.000);

        PathChain startToShoot = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(24.00, 127.700),

                                shootingPose
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(143), Math.toRadians(180))

                .build();

        PathChain shootToSec = follower.pathBuilder().addPath(
                        new BezierCurve(
                                shootingPose,
                                new Pose(50.300, 56.606),
                                new Pose(12.000, 60.000)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(180))

                .build();

        PathChain secToShoot = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(12.000, 60.000),
                                new Pose(39.500, 66.500),
                                shootingPose
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(180))

                .build();

        PathChain openTunnel = follower.pathBuilder().addPath(
                        new BezierCurve(
                                shootingPose,
                                new Pose(35.5, 67.500),
                                new Pose(16, 69.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(170))

                .build();

        PathChain tunnelIntake = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(16, 69.000),

                                new Pose(11, 59.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(170), Math.toRadians(130))

                .build();

        PathChain tunnelToShoot = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(11, 59.000),
                                new Pose(31.4, 54.800),
                                shootingPose
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(130), Math.toRadians(180))

                .build();

        PathChain shootToThird = follower.pathBuilder().addPath(
                        new BezierCurve(
                                shootingPose,
                                new Pose(63, 37.200),
                                new Pose(12, 36.500)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(180))

                .build();

        PathChain thirdToShoot = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(12, 36.500),

                                shootingPose
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(180))

                .build();

        PathChain shootToFirst = follower.pathBuilder().addPath(
                        new BezierLine(
                                shootingPose,

                                new Pose(17, 84.500)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(180))

                .build();

        PathChain firstToShoot = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(17, 84.500),

                                shootingPose.withY(100)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(180))

                .build();


        addAction(
                new ChangeStateAction(RobotState.SPEED_UP),
                new FollowAction(startToShoot, () -> follower.getPose().getY() < 86.5),
                new SleepAction(800),
                new ChangeStateAction(RobotState.FIRE),
                new SleepAction(800),
                new ChangeStateAction(RobotState.IDLE),

                new ChangeStateAction(RobotState.INTAKE),
                new FollowAction(shootToSec, () -> follower.getPose().getX() < 13),
                new SleepAction(450),
                new ChangeStateAction(RobotState.IDLE),
                new SleepAction(250),

                new ChangeStateAction(RobotState.SPEED_UP),
                new FollowAction(secToShoot, () -> follower.getPose().getX() > 47.5),
                new SleepAction(800),
                new ChangeStateAction(RobotState.FIRE),
                new SleepAction(1000),
                new ChangeStateAction(RobotState.IDLE),

                new ChangeStateAction(RobotState.INTAKE),
                new FollowAction(openTunnel, () -> follower.getPose().getX() < 17),
                new SleepAction(400),
                new FollowAction(tunnelIntake),
                new SleepAction(1000),
                new ChangeStateAction(RobotState.IDLE),
                new SleepAction(250),

                new ChangeStateAction(RobotState.SPEED_UP),
                new FollowAction(tunnelToShoot, () -> follower.getPose().getX() > 47.5),
                new SleepAction(800),
                new ChangeStateAction(RobotState.FIRE),
                new SleepAction(1000),
                new ChangeStateAction(RobotState.IDLE),

                new ChangeStateAction(RobotState.INTAKE),
                new FollowAction(shootToThird, () -> follower.getPose().getX() < 13),
                new SleepAction(500),
                new ChangeStateAction(RobotState.IDLE),
                new SleepAction(250),

                new ChangeStateAction(RobotState.SPEED_UP),
                new FollowAction(thirdToShoot, () -> follower.getPose().getX() > 47.5),
                new SleepAction(800),
                new ChangeStateAction(RobotState.FIRE),
                new SleepAction(800),
                new ChangeStateAction(RobotState.IDLE),

                new ChangeStateAction(RobotState.INTAKE),
                new FollowAction(shootToFirst, () -> follower.getPose().getX() < 18),
                new SleepAction(500),
                new ChangeStateAction(RobotState.IDLE),
                new SleepAction(250),

                new ChangeStateAction(RobotState.SPEED_UP),
                new FollowAction(firstToShoot, () -> follower.getPose().getX() > 47.6),
                new SleepAction(800),
                new ChangeStateAction(RobotState.FIRE),
                new SleepAction(1000),
                new ChangeStateAction(RobotState.IDLE)
        );
    }
}
