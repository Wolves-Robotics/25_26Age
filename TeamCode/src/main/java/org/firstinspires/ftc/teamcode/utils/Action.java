package org.firstinspires.ftc.teamcode.utils;

import java.util.function.BooleanSupplier;

public class Action {
    private BooleanSupplier start;
    private Runnable pressAction;
    private boolean hold;
    private Runnable releaseAction;
    private boolean pressed = false;

    public Action(BooleanSupplier start, Runnable pressAction) {
        this(start, pressAction, false);
    }

    public Action(BooleanSupplier start, Runnable pressAction, boolean hold) {
        this(start, pressAction, hold, null);
    }

    public Action(BooleanSupplier start, Runnable pressAction, Runnable releaseAction) {
        this(start, pressAction, false, releaseAction);
    }

    public Action(BooleanSupplier start, Runnable pressAction, boolean hold, Runnable releaseAction) {
        this.start = start;

        this.pressAction = pressAction;

        this.hold = hold;

        this.releaseAction = releaseAction;
    }

    public void update() {
        if (start.getAsBoolean() && (!pressed || hold)) {
            pressAction.run();
            pressed = true;
        }

        if (!start.getAsBoolean() && pressed) {
            if (releaseAction != null) {
                releaseAction.run();
            }
            pressed = false;
        }
    }
}
