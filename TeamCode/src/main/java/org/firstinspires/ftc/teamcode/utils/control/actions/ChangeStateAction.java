package org.firstinspires.ftc.teamcode.utils.control.actions;

import org.firstinspires.ftc.teamcode.Switchback;
import org.firstinspires.ftc.teamcode.utils.enums.RobotState;

public class ChangeStateAction extends AutoAction {
    public ChangeStateAction(RobotState state) {
        super(
                () -> {
                    if (state == RobotState.FIRE) Switchback.getInstance().changeState(RobotState.SPEED_UP);
                    Switchback.getInstance().changeState(state);
                    },
                () -> true
        );
    }
}
