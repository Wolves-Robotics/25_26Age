package org.firstinspires.ftc.teamcode.utils.control.actions;


import com.pedropathing.follower.Follower;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.Switchback;

public class FollowAction extends AutoAction {
    private Follower follower;

    public FollowAction(PathChain path) {
        super();
        follower = Switchback.getInstance().getFollower();
        setLambdas(
                () -> follower.followPath(path),
                () -> !follower.isBusy()
        );
    }
}
