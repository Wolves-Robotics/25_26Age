package org.firstinspires.ftc.teamcode.utils.control.actions;

import com.qualcomm.robotcore.util.ElapsedTime;

public class SleepAction extends AutoAction {
    private ElapsedTime elapsedTime;

    public SleepAction (double milliseconds) {
        super();
        setLambdas(
                () -> elapsedTime = new ElapsedTime(),
                () -> elapsedTime.milliseconds() >= milliseconds
        );
    }
}
