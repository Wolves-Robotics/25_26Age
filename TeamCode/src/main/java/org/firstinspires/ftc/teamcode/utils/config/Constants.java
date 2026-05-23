package org.firstinspires.ftc.teamcode.utils.config;

import com.pedropathing.geometry.Pose;

import org.joml.Vector2d;

public class Constants {
    public static final Pose RED_CLOSE_INIT  = new Pose(96, 136.91339, Math.toRadians(270));
    public static final Pose RED_FAR_INIT    = new Pose(96, 7.08661  , Math.toRadians(90));
    public static final Pose BLUE_CLOSE_INIT = new Pose(48, 136.91339, Math.toRadians(270));
    public static final Pose BLUE_FAR_INIT   = new Pose(48, 7.08661  , Math.toRadians(90));

    public static final Pose RED_RESET_POS   = new Pose(5.885827  , 7.08661, Math.toRadians(90));
    public static final Pose BLUE_RESET_POS  = new Pose(138.114173, 7.08661, Math.toRadians(90));

    public static final Vector2d RED_APRILTAG  = new Vector2d(130.34, 130.38);
    public static final Vector2d BLUE_APRILTAG = new Vector2d(13.66 , 130.38);

    public static final Vector2d RED_TARGET_POS =  new Vector2d(139, 138);
    public static final Vector2d BLUE_TARGET_POS = new Vector2d(5  , 138);
}
