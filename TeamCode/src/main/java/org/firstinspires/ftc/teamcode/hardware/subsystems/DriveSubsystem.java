package org.firstinspires.ftc.teamcode.hardware.subsystems;

import com.pedropathing.follower.Follower;

import org.firstinspires.ftc.teamcode.enums.HardwareEnum;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

public class DriveSubsystem {
    private RobotHardware robotHardware;

    private Follower follower;

    private boolean followingPath;

    private double x, y, rx;

    public DriveSubsystem(RobotHardware robotHardware) {
        this.robotHardware = robotHardware;

        follower = robotHardware.getFollower();
        followingPath = false;

        x  = 0;
        y  = 0;
        rx = 0;
    }

    public void following(boolean followingPath) {
        this.followingPath = followingPath;
        if (this.followingPath) {
            follower.resumePathFollowing();
        } else {
            follower.pausePathFollowing();
        }
    }

    public boolean isFollowingPath() {
        return followingPath;
    }

    public void setDriveCoefficients(double x, double y, double rx) {
        this.x  = x;
        this.y  = -y;
        this.rx = rx;
    }

    public void update() {
        if (followingPath) {

        } else {
            double botHeading = robotHardware.getImuYaw();

            // Rotate the movement direction counter to the bot's rotation
            double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
            double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

            rotX *= 1.1;

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
            double frontLeftPower = (rotY + rotX + rx) / denominator;
            double backLeftPower = (rotY - rotX + rx) / denominator;
            double frontRightPower = (rotY - rotX - rx) / denominator;
            double backRightPower = (rotY + rotX - rx) / denominator;

            robotHardware.setMotorPower(HardwareEnum.forwardLeft, frontLeftPower);
            robotHardware.setMotorPower(HardwareEnum.backLeft, backLeftPower);
            robotHardware.setMotorPower(HardwareEnum.forwardRight, frontRightPower);
            robotHardware.setMotorPower(HardwareEnum.backRight, backRightPower);
        }
    }
}
