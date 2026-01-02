package org.firstinspires.ftc.teamcode.utils;

import com.arcrobotics.ftclib.geometry.Twist2d;
import com.bylazar.field.FieldManager;
import com.bylazar.field.PanelsField;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

public class Drawing {
    private final FieldManager field;

    public Drawing() {
        field = PanelsField.INSTANCE.getField();
        field.setOffsets(PanelsField.INSTANCE.getPresets().getDEFAULT_FTC());
    }

    public FieldManager getField() {
        return field;
    }

    public void reset() {
        field.clearStyle();
        field.clearFill();
        field.clearOutline();
        field.getCanvas().reset();
    }

    public void update() {
        field.update();
    }

    public void drawRobot(Pose2D pose, Twist2d vel) {
        double x = pose.getX(DistanceUnit.INCH);
        double y = pose.getY(DistanceUnit.INCH);
        double heading = pose.getHeading(AngleUnit.RADIANS);

        double vx = vel.dx;
        double vy = vel.dy;

        field.setStyle("none", "white", 0.5);
        field.moveCursor(x, y);
        field.circle(7.0);

        double hx = x + 12 * Math.cos(heading);
        double hy = y + 12 * Math.sin(heading);

        field.setStyle("none", "cyan", 0.5);
        field.moveCursor(x, y);
        field.line(hx, hy);

        double vxDraw = x + vx * 0.15;
        double vyDraw = y + vy * 0.15;

        field.setStyle("none", "red", 0.5);
        field.moveCursor(x, y);
        field.line(vxDraw, vyDraw);
    }
}
