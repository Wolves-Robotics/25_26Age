package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Twist2d;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.subsystems.subsystemHardware.DriveStuff;

public class DriveSubsystem {
    private final DriveStuff hardware;

    private boolean following = false;
    private Pose2d drive;

    public DriveSubsystem(DriveStuff hardware) {
        this.hardware = hardware;
    }

    public void setDriveVector(Pose2d drive) {
        this.drive = drive;
    }

    private void manualDrivePower() {
        double heading = (hardware.follower().getHeading()) % (2*Math.PI);


    }

    public void read() {

    }

    public void update() {
        if (following) {

        } else {
            manualDrivePower();
        }
        hardware.drawing().drawRobot(
                new Pose2D(
                        DistanceUnit.INCH,
                        hardware.follower().getPose().getX(),
                        hardware.follower().getPose().getY(),
                        AngleUnit.RADIANS,
                        hardware.follower().getHeading()),
                new Twist2d(0, 0, 0));
    }

    public void write() {

    }
}
