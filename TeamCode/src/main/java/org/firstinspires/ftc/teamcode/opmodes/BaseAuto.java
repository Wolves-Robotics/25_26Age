package org.firstinspires.ftc.teamcode.opmodes;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Switchback;
import org.firstinspires.ftc.teamcode.utils.control.actions.AutoAction;
import org.firstinspires.ftc.teamcode.utils.enums.Alliance;
import org.firstinspires.ftc.teamcode.utils.ExternalTools;
import org.firstinspires.ftc.teamcode.utils.config.MatchDetails;

import java.util.ArrayList;
import java.util.Collections;

public abstract class BaseAuto {
    protected Switchback switchback;

    protected Follower follower;

    private ArrayList<AutoAction> actionList;
    private int actionIndex;

    public final void init(OpMode opMode) {
        actionList = new ArrayList<>();
        actionIndex = 0;

        MatchDetails.ResetDetails();
        MatchDetails.ALLIANCECOLOR = setColor();

        switchback = Switchback.getInstance();
        switchback.init(opMode);

        switchback.setPose(setPose());

        follower = switchback.getFollower();

        setActionList();
    }

    public final void start() {
        switchback.getDriveSub().startFollowing();
    }

    public final void loop() {
        switchback.read();

        AutoAction temp;
        do {
            if (actionList.size() != actionIndex) {
                temp = actionList.get(actionIndex);
                temp.update();
                if (temp.isEnd())
                    actionIndex++;
            } else {
                break;
            }
        } while (temp.isEnd());

        switchback.update();

        switchback.write();
    }

    public final void stop() {
        switchback.setFinalPose();
    }

    protected final void addAction(AutoAction... actions) {
        Collections.addAll(actionList, actions);
    }

    abstract protected Alliance setColor();

    abstract protected Pose setPose();

    abstract protected void setActionList();
}
