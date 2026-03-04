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
import org.opencv.core.Mat;

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
                        new BezierLine(new Pose(50.6, 9.200), new Pose(9.3, 24))
                )
                .setLinearHeadingInterpolation(Math.toRadians(150), Math.toRadians(255))
                .build();

        PathChain intakeFollowThrough = follower.
                pathBuilder().
                addPath(
                        new BezierLine(new Pose(9.3, 24), new Pose(9.3, 9.300))
                )
                .setLinearHeadingInterpolation(Math.toRadians(255), Math.toRadians(290))
                .addPath(
                        new BezierLine(new Pose(9.3, 9.300), new Pose(9.3, 13.300))
                )
                .setConstantHeadingInterpolation(Math.toRadians(290))
                .build();

        PathChain intakeToShooting = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(9.3, 13.300), new Pose(53, 13.600))
                )
                .setLinearHeadingInterpolation(Math.toRadians(290), Math.toRadians(165))
                .build();

        PathChain shootingToGPP = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(53, 13.6), new Pose(51, 31))
                )
                .setLinearHeadingInterpolation(Math.toRadians(165), Math.toRadians(180))
                .setBrakingStart(1.4)

                .addPath(
                        new BezierLine(new Pose(51, 31), new Pose(11.6, 35))
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .setBrakingStart(1.5)
                .build();

        PathChain GPPToShooting = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(11.6, 35), new Pose(53, 13.6))
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(165))
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
                new SleepAction(3500),

                new ChangeStateAction(RobotState.INTAKE),
                new FollowAction(shootingToGPP),
                new SleepAction(100),
                new ChangeStateAction(RobotState.IDLE),
                new SleepAction(250),

                new ChangeStateAction(RobotState.SPEED_UP),
                new FollowAction(GPPToShooting),
                new SleepAction(700),
                new ChangeStateAction(RobotState.FIRE),
                new SleepAction(3000),

                new ChangeStateAction(RobotState.INTAKE),
                new FollowAction(intakeStart1),
                new FollowAction(intakeFollowThrough),
                new SleepAction(500),
                new ChangeStateAction(RobotState.IDLE),
                new SleepAction(250),

                new ChangeStateAction(RobotState.SPEED_UP),
                new FollowAction(intakeToShooting),
                new SleepAction(700),
                new ChangeStateAction(RobotState.FIRE),
                new SleepAction(3000),

                new ChangeStateAction(RobotState.IDLE),
                new FollowAction(shootingToLeave)
        );
    }
}
