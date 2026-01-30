package org.firstinspires.ftc.teamcode.utils.config;

import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.teamcode.utils.enums.Alliance;
import org.firstinspires.ftc.teamcode.utils.enums.Pattern;
import org.joml.Vector2d;

public class MatchDetails {
    // Match Variables
    public static Alliance ALLIANCECOLOR;
    public static Pattern PATTERN;

    // Robot Variables
    public static Pose PoseAtStop;
    public static Vector2d target;
    public static Vector2d aprilTag;
    public static double ZeroToForwardAngle;

    public static void ResetDetails() {
        ALLIANCECOLOR = null;
        PATTERN = null;

        PoseAtStop = null;
        target = null;
        aprilTag = null;
        ZeroToForwardAngle = 0;
    }
}
