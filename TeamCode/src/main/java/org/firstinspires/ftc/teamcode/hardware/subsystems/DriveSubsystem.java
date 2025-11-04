package org.firstinspires.ftc.teamcode.hardware.subsystems;

import com.pedropathing.follower.Follower;

import org.firstinspires.ftc.teamcode.enums.HardwareEnum;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

public class DriveSubsystem {
    private RobotHardware robotHardware;

    private Follower follower;

    private boolean followingPath;

    private boolean parking;

    private double x, y, rx;

    public DriveSubsystem(RobotHardware robotHardware) {
        this.robotHardware = robotHardware;

        follower = robotHardware.getFollower();
        followingPath = false;

        parking = false;

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

    public void setParking(boolean parking) {
        this.parking = parking;

        if (parking) {
            robotHardware.setMotorBrake(HardwareEnum.FORWARD_LEFT, true);
            robotHardware.setMotorBrake(HardwareEnum.BACK_LEFT, true);
            robotHardware.setMotorBrake(HardwareEnum.FORWARD_RIGHT, true);
            robotHardware.setMotorBrake(HardwareEnum.BACK_RIGHT, true);
        } else {
            robotHardware.setMotorBrake(HardwareEnum.FORWARD_LEFT, false);
            robotHardware.setMotorBrake(HardwareEnum.BACK_LEFT, false);
            robotHardware.setMotorBrake(HardwareEnum.FORWARD_RIGHT, false);
            robotHardware.setMotorBrake(HardwareEnum.BACK_RIGHT, false);
        }
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


            double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
            double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

            rotX *= 1.1;


            double multiplier = parking ? 0.7 : 1;

            double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
            double frontLeftPower = (rotY + rotX + rx) / denominator * multiplier;
            double backLeftPower = (rotY - rotX + rx) / denominator * multiplier;
            double frontRightPower = (rotY - rotX - rx) / denominator * multiplier;
            double backRightPower = (rotY + rotX - rx) / denominator * multiplier;

            robotHardware.setMotorPower(HardwareEnum.FORWARD_LEFT, frontLeftPower);
            robotHardware.setMotorPower(HardwareEnum.BACK_LEFT, backLeftPower);
            robotHardware.setMotorPower(HardwareEnum.FORWARD_RIGHT, frontRightPower);
            robotHardware.setMotorPower(HardwareEnum.BACK_RIGHT, backRightPower);
        }
    }
}
