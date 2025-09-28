package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.enums.Color;
import org.firstinspires.ftc.teamcode.enums.SelectionEnum;

/*
Making this class contain an instance of itself
in order to keep its values from Auto to TeleOp

Also, the button presses will come from Robot.java
 */
public class HardwareSelection {
    // Everything with the self contained instance has to be static
    private static HardwareSelection instance = null;

    public static HardwareSelection getInstance() {
        return instance;
    }

    public static void reset() {
        instance = new HardwareSelection();
    }

    private SelectionEnum selectionEnum;
    private Color teamColor;
    private double testOffset;

    // Default values should be defined here
    public HardwareSelection() {
        selectionEnum = SelectionEnum.COLOR;

        teamColor = Color.RED;
        testOffset = 0;
    }

    // Increments what is selected
    public void incrementSelect() {
        // ordinal gets the position of the selection, making this just basic list incrementation
        selectionEnum = SelectionEnum.values()[(selectionEnum.ordinal() + 1) % SelectionEnum.values().length];
    }

    // decrements what is selected
    public void decrementSelect() {
        // same thing as the incrementation, but subtracting by one
        // and adding the length of the list to keep the number positive
        selectionEnum = SelectionEnum.values()[(selectionEnum.ordinal() - 1 + SelectionEnum.values().length) % SelectionEnum.values().length];

    }

    // increments the selected
    public void incrementSelected() {
        switch (selectionEnum) {
            case COLOR:
                // this uses the same algorithm as the previous incrementation
                teamColor = Color.values()[(teamColor.ordinal() + 1) % Color.values().length];
                break;
            case TEST_OFFSET:
                testOffset += 0.5;
                break;
        }
    }

    public void decrementSelected() {
        switch (selectionEnum) {
            case COLOR:
                // this uses the sane algorithm as the decrementation
                teamColor = Color.values()[(teamColor.ordinal() - 1 + Color.values().length) % Color.values().length];
                break;
            case TEST_OFFSET:
                testOffset -= 0.5;
            }
    }

    public void updateTele(Telemetry telemetry) {
        telemetry.addData("Selected", selectionEnum == SelectionEnum.COLOR ? "Color" :
                                                                                     "Test Offset");
        telemetry.addLine();

        telemetry.addData("Color", teamColor == Color.RED ? "Red" : "Blue");
        telemetry.addLine();

        telemetry.addData("Test Offset", testOffset);
        telemetry.addLine();
    }
}
