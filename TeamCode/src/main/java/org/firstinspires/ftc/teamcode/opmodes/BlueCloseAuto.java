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
public class BlueCloseAuto extends Auto{
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
        PathChain startToShooting = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(24, 127.700), new Pose(59.1, 98.500))
                )
                .setLinearHeadingInterpolation(Math.toRadians(143), Math.toRadians(135))
                .build();

        PathChain shootingToPPG = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(59.1, 98.500),
                                new Pose(61, 91.000),
                                new Pose(17.7, 83.00)
                        )
                )
                .setTangentHeadingInterpolation()
                .build();

        PathChain PPGToLever = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(19.5, 83.600),
                                new Pose(34, 80.200),
                                new Pose(21.5, 76.500)
                        )
                )
                .setLinearHeadingInterpolation(180, Math.toRadians(180))
                .build();

        PathChain leverToShooting = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(17, 74.500),
                                new Pose(33.5, 74.500),
                                new Pose(59.1, 98.500)
                        )
                )
                .setTangentHeadingInterpolation()
                .setReversed()
                .build();

        PathChain shootingToPGP = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(59.1, 98.500), new Pose(51, 65.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(225), Math.toRadians(180))
                .setBrakingStart(1.7)

                .addPath(
                        new BezierLine(new Pose(51, 65.000), new Pose(11.6, 59.000))
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .setBrakingStart(1.7)
                .build();

        PathChain PGPToShooting = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(11.6, 59.000),
                                new Pose(26, 59.700),
                                new Pose(59.1, 98.500)
                        )
                )
                .setTangentHeadingInterpolation()
                .setReversed()
                .build();

        PathChain shootingToGPP = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(59.1, 98.500), new Pose(51, 42.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(225), Math.toRadians(180))
                .setBrakingStart(1.7)

                .addPath(
                        new BezierLine(new Pose(51, 42.000), new Pose(11.6, 39.000))
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))

                .setBrakingStart(1.7)
                .build();

        PathChain GPPToShooting = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(11.6, 35.000),
                                new Pose(29.8, 35.600),
                                new Pose(50.1, 89.700),
                                new Pose(64, 105)
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
