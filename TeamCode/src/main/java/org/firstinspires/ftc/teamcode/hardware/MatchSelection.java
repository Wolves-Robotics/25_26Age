package org.firstinspires.ftc.teamcode.hardware;

import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.enums.Autos;
import org.firstinspires.ftc.teamcode.enums.Color;
import org.firstinspires.ftc.teamcode.enums.MatchEnum;
import org.firstinspires.ftc.teamcode.enums.TeleOps;

/*
Making this class contain an instance of itself
in order to keep its values from Auto to TeleOp

Also, the button presses will come from Robot.java
 */
public class MatchSelection {
    // Everything with the self contained instance has to be static
    private static MatchSelection instance = null;

    public static MatchSelection getInstance() {
        return instance;
    }

    public static void reset() {
        instance = new MatchSelection();
    }

    private MatchEnum matchEnum;
    private Autos autos;
    private TeleOps teleOps;
    private Color teamColor;
    private Pose lastPose;

    private double turretOffset;

    // Default values should be defined here
    public MatchSelection() {
        matchEnum = MatchEnum.AUTO;
        autos = Autos.CLOSE_AUTO;
        teleOps = TeleOps.COMPETITION;
        teamColor = Color.RED;

        lastPose = new Pose();

        turretOffset = 0;
    }

    public void setLastPose(Pose lastPose) {
        this.lastPose = lastPose;
    }

    public Pose getLastPose() {
        return lastPose;
    }

    public void setTurretOffset(double ticks) {
        turretOffset = ticks/147.05917;
    }

    public double getTurretOffset() {
        return turretOffset;
    }

    // Increments what is selected
    public void incrementSelect() {
        // ordinal gets the position of the selection, making this just basic list incrementation
        matchEnum = MatchEnum.values()[(matchEnum.ordinal() + 1) % MatchEnum.values().length];
    }

    // decrements what is selected
    public void decrementSelect() {
        // same thing as the incrementation, but subtracting by one
        // and adding the length of the list to keep the number positive
        matchEnum = MatchEnum.values()[(matchEnum.ordinal() - 1 + MatchEnum.values().length) % MatchEnum.values().length];

    }

    // increments the selected
    public void incrementSelected() {
        switch (matchEnum) {
            case AUTO:
                autos = Autos.values()[(autos.ordinal() + 1) % Autos.values().length];
                break;
            case TELEOP:
                teleOps = TeleOps.values()[(teleOps.ordinal() + 1) % TeleOps.values().length];
                break;
            case COLOR:
                teamColor = Color.values()[(teamColor.ordinal() + 1) % Color.values().length];
                break;
        }
    }

    public void decrementSelected() {
        switch (matchEnum) {
            case AUTO:
                autos = Autos.values()[(autos.ordinal() - 1 + Autos.values().length) % Autos.values().length];
                break;
            case TELEOP:
                teleOps = TeleOps.values()[(teleOps.ordinal() - 1 + TeleOps.values().length) % TeleOps.values().length];
                break;
            case COLOR:
                teamColor = Color.values()[(teamColor.ordinal() - 1 + Color.values().length) % Color.values().length];
                break;
            }
    }

    public Color getTeamColor() {
        return teamColor;
    }

    public Autos getAutos() {
        return autos;
    }

    public TeleOps getTeleOps() {
        return teleOps;
    }

    public void updateTele(Telemetry telemetry) {
        telemetry.addData("Selected", matchEnum == MatchEnum.AUTO ? "Auto":
                                              matchEnum == MatchEnum.TELEOP ? "TeleOp":
                                              matchEnum == MatchEnum.COLOR  ? "Color":
                                                                              "null");
        telemetry.addLine();

        telemetry.addData("Auto", autos == Autos.CLOSE_AUTO ? "Close Auto" : "Far Auto");

        telemetry.addData("TeleOp", teleOps == TeleOps.COMPETITION ? "Competition" : "Testing");

        telemetry.addData("Color", teamColor == Color.RED ? "Red" : "Blue");

        telemetry.addLine();
    }
}
