package org.firstinspires.ftc.teamcode.opmodes;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.utils.config.Constants;
import org.firstinspires.ftc.teamcode.utils.control.actions.ChangeStateAction;
import org.firstinspires.ftc.teamcode.utils.control.actions.FollowAction;
import org.firstinspires.ftc.teamcode.utils.control.actions.SleepAction;
import org.firstinspires.ftc.teamcode.utils.enums.Alliance;
import org.firstinspires.ftc.teamcode.utils.enums.RobotState;

@Autonomous(preselectTeleOp = "TestTele")
public class RedCloseAuto extends Auto {
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
        PathChain startToShooting = follower
            .pathBuilder()
            .addPath(
                    new BezierLine(new Pose(120, 127.700), new Pose(84.900, 98.500))
            )
            .setLinearHeadingInterpolation(Math.toRadians(37), Math.toRadians(45))
            .build();

        PathChain shootingToPPG = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(84.900, 98.500),
                                new Pose(83.000, 91.000),
                                new Pose(126.30, 83.600)
                        )
                )
                .setTangentHeadingInterpolation()
                .build();

        PathChain PPGToLever = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(124.500, 83.600),
                                new Pose(110.800, 80.200),
                                new Pose(122.500, 76.500)
                        )
                )
                .setLinearHeadingInterpolation(0, Math.toRadians(0))
                .build();

        PathChain leverToShooting = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(127.000, 74.500),
                                new Pose(110.500, 74.500),
                                new Pose(84.900, 98.500)
                        )
                )
                .setTangentHeadingInterpolation()
                .setReversed()
                .build();

        PathChain shootingToPGP = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(84.900, 98.500), new Pose(93.000, 65.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(-45), Math.toRadians(0))
                .setBrakingStart(1.7)

                .addPath(
                        new BezierLine(new Pose(93.000, 65.000), new Pose(132.40, 59.000))
                )
                .setConstantHeadingInterpolation(0)
                .setBrakingStart(1.8)
                .build();

        PathChain PGPToShooting = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(132.400, 59.000),
                                new Pose(118.000, 59.700),
                                new Pose(84.900, 98.500)
                        )
                )
                .setTangentHeadingInterpolation()
                .setReversed()
                .build();

        PathChain shootingToGPP = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(84.900, 98.500), new Pose(93.000, 42.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(-45), Math.toRadians(0))
                .setBrakingStart(1.7)

                .addPath(
                        new BezierLine(new Pose(93.000, 42.000), new Pose(132.40, 38))
                )
                .setConstantHeadingInterpolation(0)
                .setBrakingStart(1.8)
                .build();

        PathChain GPPToShooting = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(134.500, 35.000),
                                new Pose(114.200, 35.600),
                                new Pose(93.900, 89.700),
                                new Pose(80, 105)
                        )
                )
                .setTangentHeadingInterpolation()
                .setReversed()
                .build();

        addAction(
                new ChangeStateAction(RobotState.SPEED_UP),
                new FollowAction(startToShooting),
                new SleepAction(700),
                new ChangeStateAction(RobotState.FIRE),
                new SleepAction(1800),

                new ChangeStateAction(RobotState.INTAKE),
                new FollowAction(shootingToPPG),
                new SleepAction(100),
                new ChangeStateAction(RobotState.IDLE),

                new FollowAction(PPGToLever),
                new SleepAction(100),

                new ChangeStateAction(RobotState.SPEED_UP),
                new FollowAction(leverToShooting),
                new SleepAction(700),
                new ChangeStateAction(RobotState.FIRE),
                new SleepAction(1800),

                new ChangeStateAction(RobotState.INTAKE),
                new FollowAction(shootingToPGP),
                new SleepAction(100),
                new ChangeStateAction(RobotState.IDLE),

                new ChangeStateAction(RobotState.SPEED_UP),
                new FollowAction(PGPToShooting),
                new SleepAction(700),
                new ChangeStateAction(RobotState.FIRE),
                new SleepAction(1800),

                new ChangeStateAction(RobotState.INTAKE),
                new FollowAction(shootingToGPP),
                new SleepAction(100),
                new ChangeStateAction(RobotState.IDLE),

                new ChangeStateAction(RobotState.SPEED_UP),
                new FollowAction(GPPToShooting),
                new SleepAction(700),
                new ChangeStateAction(RobotState.FIRE),
                new SleepAction(1800),

                new ChangeStateAction(RobotState.IDLE)
        );
    }
}
