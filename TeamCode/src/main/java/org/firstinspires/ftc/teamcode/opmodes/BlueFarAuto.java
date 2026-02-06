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
public class BlueFarAuto extends Auto {
    @Override
    public Alliance setColor() {
        return Alliance.BLUE;
    }

    @Override
    protected Pose setPose() {
        return Constants.BLUE_FAR_INIT;
    }

    @Override
    protected void setActionList() {
        PathChain intakeStart1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(50.6, 9.200), new Pose(10.3, 22.500))
                )
                .setLinearHeadingInterpolation(Math.toRadians(150), Math.toRadians(240))
                .build();

        PathChain intakeFollowThrough = follower.
                pathBuilder().
                addPath(
                        new BezierLine(new Pose(10.3, 22.500), new Pose(7.3, 8.300))
                )
                .setLinearHeadingInterpolation(Math.toRadians(240), Math.toRadians(270))
                .build();

        PathChain intakeToShooting = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(7.3, 8.300), new Pose(53, 13.600))
                )
                .setLinearHeadingInterpolation(Math.toRadians(270), Math.toRadians(165))
                .build();

        PathChain shootingToLeave = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(53, 13.600), new Pose(37, 13.600))
                )
                .setLinearHeadingInterpolation(Math.toRadians(165), Math.toRadians(180))
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

                new ChangeStateAction(RobotState.IDLE),
                new FollowAction(shootingToLeave)
        );
    }
}
