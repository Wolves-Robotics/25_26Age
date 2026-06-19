package org.firstinspires.ftc.teamcode.utils.control.actions;

import java.util.function.BooleanSupplier;

public class AutoAction {
    private boolean start = false;

    private Runnable run;
    private BooleanSupplier end;

    public AutoAction(Runnable run, BooleanSupplier end) {
        this.run = run;
        this.end = end;
    }

    public AutoAction() {}

    protected void setLambdas(Runnable run, BooleanSupplier end) {
        this.run = run;
        this.end = end;
    }

    public void update() {
        if (!start) {
            run.run();
            start = true;
        }
    }

    public final boolean isEnd() {
        return end.getAsBoolean();
    }
}
