package org.firstinspires.ftc.teamcode.opmodes;

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
                .setLinearHeadingInterpolation(Math.toRadians(-75), Math.toRadians(-110))
                .addPath(
                        new BezierLine(new Pose(134.700, 9.300), new Pose(134.700, 13.300))
                )
                .setConstantHeadingInterpolation(Math.toRadians(-110))
                .build();

        PathChain intakeToShooting = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(133.700, 13.300), new Pose(91.000, 13.600))
                )
                .setLinearHeadingInterpolation(Math.toRadians(-110), Math.toRadians(15))
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
                new SleepAction(5000),

                new ChangeStateAction(RobotState.INTAKE),
                new FollowAction(intakeStart1),
                new FollowAction(intakeFollowThrough),
                new SleepAction(500),

                new ChangeStateAction(RobotState.SPEED_UP),
                new FollowAction(intakeToShooting),
                new SleepAction(700),
                new ChangeStateAction(RobotState.FIRE),
                new SleepAction(5000),

                new ChangeStateAction(RobotState.INTAKE),
                new FollowAction(intakeStart1),
                new FollowAction(intakeFollowThrough),
                new SleepAction(500),

                new ChangeStateAction(RobotState.SPEED_UP),
                new FollowAction(intakeToShooting),
                new SleepAction(700),
                new ChangeStateAction(RobotState.FIRE),
                new SleepAction(5000),

                new ChangeStateAction(RobotState.IDLE),
                new FollowAction(shootingToLeave)
        );
    }
}
