package org.firstinspires.ftc.teamcode.utils;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.geometry.Twist2d;
import com.bylazar.field.FieldManager;
import com.bylazar.field.PanelsField;
import com.bylazar.telemetry.PanelsTelemetry;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExternalTools {
    public static final Logger LOGGER = LoggerFactory.getLogger("Wolves Robotics");
    public static final FieldManager FIELD = PanelsField.INSTANCE.getField();
    public static final MultipleTelemetry TELEMETRY = new MultipleTelemetry(PanelsTelemetry.INSTANCE.getFtcTelemetry());

    public static void initialize(Telemetry telemetry) {
        FIELD.setOffsets(PanelsField.INSTANCE.getPresets().getPEDRO_PATHING());
        TELEMETRY.addTelemetry(telemetry);
    }

    public static void write() {
        FIELD.update();
        TELEMETRY.update();
    }

    public static void fieldReset() {
        FIELD.clearStyle();
        FIELD.clearFill();
        FIELD.clearOutline();
        FIELD.getCanvas().reset();
    }

    public static void drawRobot(Pose2D pose, Twist2d vel) {
        double x = pose.getX(DistanceUnit.INCH);
        double y = pose.getY(DistanceUnit.INCH);
        double heading = pose.getHeading(AngleUnit.RADIANS);

        double vx = vel.dx;
        double vy = vel.dy;

        FIELD.setStyle("none", "white", 0.5);
        FIELD.moveCursor(x, y);
        FIELD.circle(7.0);

        double hx = x + 12 * Math.cos(heading);
        double hy = y + 12 * Math.sin(heading);

        FIELD.setStyle("none", "cyan", 0.5);
        FIELD.moveCursor(x, y);
        FIELD.line(hx, hy);

        double vxDraw = x + vx * 0.15;
        double vyDraw = y + vy * 0.15;

        FIELD.setStyle("none", "red", 0.5);
        FIELD.moveCursor(x, y);
        FIELD.line(vxDraw, vyDraw);
    }
}
