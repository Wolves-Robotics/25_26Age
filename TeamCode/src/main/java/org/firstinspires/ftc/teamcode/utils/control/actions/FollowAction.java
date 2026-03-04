package org.firstinspires.ftc.teamcode.utils.control.actions;


import com.pedropathing.follower.Follower;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Switchback;

import java.util.function.BooleanSupplier;

public class FollowAction extends AutoAction {
    private Follower follower;
    private ElapsedTime elapsedTime;

    public FollowAction(PathChain path) {
        super();
        follower = Switchback.getInstance().getFollower();
        setLambdas(
                () -> {
                    follower.followPath(path);
                    elapsedTime = new ElapsedTime();},
                () -> !follower.isBusy() || elapsedTime.seconds() > 4
        );
    }

    public FollowAction(PathChain path, BooleanSupplier end) {
        super();
        follower = Switchback.getInstance().getFollower();
        setLambdas(
                () -> {
                    follower.followPath(path);
                    elapsedTime = new ElapsedTime();},
                () -> !follower.isBusy() || elapsedTime.seconds() > 4 || end.getAsBoolean()
        );
    }
}
