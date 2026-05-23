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

public class BlueClose2Spike12Ball extends BaseAuto {
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

        PathChain SecToLever = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(12, 60),
                                new Pose(34, 61.5),
                                new Pose(17, 67)
                        )
                )
                .setLinearHeadingInterpolation(180, Math.toRadians(180))
                .build();

        PathChain secToShoot = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(17, 67),
                                new Pose(39.500, 66.500),
                                shootingPose
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(180))

                .build();

        PathChain openTunnel = follower.pathBuilder().addPath(
                        new BezierCurve(
                                shootingPose,
                                new Pose(39.5, 66.500),
                                new Pose(15.538, 69.741)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), 2.95997)

                .build();

        PathChain tunnelIntake = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(15.538, 69.741),

                                new Pose(10.873, 57.175)
                        )
                ).setLinearHeadingInterpolation(2.95997, 2.351319)

                .build();

        PathChain tunnelToShoot = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(10.873, 57.175),
                                new Pose(31.4, 54.800),
                                shootingPose
                        )
                ).setLinearHeadingInterpolation(2.351319, Math.toRadians(180))

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

                                shootingPose
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(180))

                .build();

        PathChain park = follower.pathBuilder().addPath(
                        new BezierLine(
                                shootingPose,

                                shootingPose.withY(125)
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

                new FollowAction(SecToLever),
                new SleepAction(500),

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
                new SleepAction(500),

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
                new ChangeStateAction(RobotState.IDLE),

                new FollowAction(park)
        );
    }
}
