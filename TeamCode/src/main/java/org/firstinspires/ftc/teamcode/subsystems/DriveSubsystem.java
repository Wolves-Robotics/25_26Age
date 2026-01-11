package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.geometry.Twist2d;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.utils.Alliance;
import org.firstinspires.ftc.teamcode.utils.ExternalTools;
import org.firstinspires.ftc.teamcode.utils.MatchDetails;
import org.joml.Vector2d;

public class DriveSubsystem {
    private final DriveStuff hardware;

    private boolean following = false;
    private Vector2d driveVector;
    private double driveRot;
    private driveTrainPower driveTrainPower;

    public DriveSubsystem(DriveStuff hardware) {
        this.hardware = hardware;
        hardware.follower().pausePathFollowing();
    }

    public void setDriveParams(Vector2d drive, double driveRot) {
        this.driveVector = drive;
        this.driveRot = driveRot;
    }

    private void manualDrivePower() {
        double heading = hardware.follower().getHeading();
        heading += MatchDetails.ALLIANCECOLOR == Alliance.BLUE ? Math.PI : 0;

        double sin = Math.sin(-heading), cos = Math.cos(-heading);
        double rotX = driveVector.x*cos -driveVector.y*sin, rotY = driveVector.x*sin + driveVector.y*cos;

        driveTrainPower = new driveTrainPower(
                rotY + rotX + driveRot,
                rotY - rotX - driveRot,
                rotY - rotX + driveRot,
                rotY + rotX - driveRot
        );

        hardware.frontLeft().setPower(driveTrainPower.fLP);
        hardware.frontRight().setPower(driveTrainPower.fRP);
        hardware.backLeft().setPower(driveTrainPower.bLP);
        hardware.backRight().setPower(driveTrainPower.bRP);
    }

    public void read() {

    }

    public void update() {
        if (following) {
            hardware.follower().update();
        } else {
            manualDrivePower();
        }
    }

    public Pose getCurrentPose() {
        return hardware.follower().getPose();
    }

    public void toggleFollowing() {
        following = !following;
        if (following) {
            hardware.follower().resumePathFollowing();
        } else {
            hardware.follower().pausePathFollowing();
        }
        setDriveParams(new Vector2d(0, 0), 0);
        manualDrivePower();
    }

    public void setPath(Path path) {
        hardware.follower().followPath(path);
    }

    public void write() {
        double  x = hardware.follower().getPose().getX(),
                y = hardware.follower().getPose().getY(),
                h = hardware.follower().getHeading();

        ExternalTools.drawRobot(
                new Pose2D(
                        DistanceUnit.INCH,
                        x,
                        y,
                        AngleUnit.RADIANS,
                        h),
                new Twist2d(0, 0, 0));

        ExternalTools.TELEMETRY.addData("X", x);
        ExternalTools.TELEMETRY.addData("Y", y);
        ExternalTools.TELEMETRY.addData("Heading", h);
    }

    private record driveTrainPower(
        double fLP,
        double fRP,
        double bLP,
        double bRP
    ) {}

    public record DriveStuff(
        DcMotorEx frontLeft,
        DcMotorEx frontRight,
        DcMotorEx backLeft,
        DcMotorEx backRight,
        Follower follower
    ) {}
}
