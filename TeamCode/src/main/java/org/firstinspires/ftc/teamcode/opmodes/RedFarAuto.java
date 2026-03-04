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
public class RedFarAuto extends Auto {
    @Override
    protected Alliance setColor() {
        return Alliance.RED;
    }

    @Override
    protected Pose setPose() {
        return Constants.RED_FAR_INIT;
    }

    @Override
    protected void setActionList() {

        PathChain intakeStart1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(93.400, 9.200), new Pose(134.700, 24))
                )
                .setLinearHeadingInterpolation(Math.toRadians(30), Math.toRadians(-75))
                .build();

        PathChain intakeFollowThrough = follower.
                pathBuilder().
                addPath(
                        new BezierLine(new Pose(134.700, 24), new Pose(134.700, 9.300))
                )
                .setLinearHeadingInterpolation(Math.toRadians(-75), Math.toRadians(-120))
                .addPath(
                        new BezierLine(new Pose(134.700, 9.300), new Pose(134.700, 10.300))
                )
                .setConstantHeadingInterpolation(Math.toRadians(-120))
                .addPath(
                        new BezierLine(new Pose(134.700, 10.300), new Pose(134.700, 9.300))
                )
                .setConstantHeadingInterpolation(Math.toRadians(-120))
                .build();

        PathChain intakeToShooting = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(134.700, 9.300), new Pose(91.000, 13.600))
                )
                .setLinearHeadingInterpolation(Math.toRadians(-120), Math.toRadians(15))
                .build();

        PathChain shootingToGPP = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(91, 13.6), new Pose(93.000, 31))
                )
                .setLinearHeadingInterpolation(Math.toRadians(15), Math.toRadians(0))
                .setBrakingStart(1.4)

                .addPath(
                        new BezierLine(new Pose(93.000, 31), new Pose(132.40, 35))
                )
                .setConstantHeadingInterpolation(0)
                .setBrakingStart(1.5)
                .build();

        PathChain GPPToShooting = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(132.4, 35), new Pose(91, 13.6))
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(15))
                .build();

        PathChain shootingToLeave = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(91.000, 13.600), new Pose(107.000, 13.600))
                )
                .setLinearHeadingInterpolation(Math.toRadians(15), Math.toRadians(0))
                .build();

        addAction(
                new ChangeStateAction(RobotState.FIRE),
                new SleepAction(2000),

                new ChangeStateAction(RobotState.INTAKE),
                new FollowAction(shootingToGPP),
                new SleepAction(100),
                new ChangeStateAction(RobotState.IDLE),
                new SleepAction(250),

                new ChangeStateAction(RobotState.SPEED_UP),
                new FollowAction(GPPToShooting),
                new SleepAction(700),
                new ChangeStateAction(RobotState.FIRE),
                new SleepAction(1800),

                new ChangeStateAction(RobotState.INTAKE),
                new FollowAction(intakeStart1),
                new FollowAction(intakeFollowThrough),
                new SleepAction(1000),
                new ChangeStateAction(RobotState.IDLE),
                new SleepAction(250),

                new ChangeStateAction(RobotState.SPEED_UP),
                new FollowAction(intakeToShooting),
                new SleepAction(700),
                new ChangeStateAction(RobotState.FIRE),
                new SleepAction(1800),

                new ChangeStateAction(RobotState.IDLE),
                new FollowAction(shootingToLeave)
        );
    }
}
