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
public class Close18BallRED extends BaseAuto {
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
                                shootingPose)

                ).setLinearHeadingInterpolation(Math.toRadians(37), Math.toRadians(0))

                .build();


    }
}
