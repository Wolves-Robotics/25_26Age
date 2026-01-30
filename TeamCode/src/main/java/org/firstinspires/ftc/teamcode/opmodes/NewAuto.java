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

@Autonomous
public class NewAuto extends Auto {
    @Override
    public Alliance setColor() {
        return Alliance.RED;
    }

    @Override
    protected Pose setPose() {
        return Constants.RED_FAR_INIT;
    }

    @Override
    protected void setActionList() {
        PathChain leave = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(96, 7.08661), new Pose(115, 15))
                )
                .setConstantHeadingInterpolation(Math.toRadians(90))
                .build();

        addAction(
                new ChangeStateAction(RobotState.FIRE),
                new SleepAction(5000),
                new ChangeStateAction(RobotState.IDLE),
                new FollowAction(leave)
        );
    }
}
