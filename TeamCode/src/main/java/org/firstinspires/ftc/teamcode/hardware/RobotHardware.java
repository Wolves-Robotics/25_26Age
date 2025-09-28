package org.firstinspires.ftc.teamcode.hardware;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.ImuParameters;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.enums.HardwareEnum;
import org.firstinspires.ftc.teamcode.hardware.singleSystems.Motor;
import org.firstinspires.ftc.teamcode.hardware.singleSystems.Servo;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.utils.GoBildaPinpointDriver;

import java.util.ArrayList;
import java.util.List;

public class RobotHardware {
    List<LynxModule> allHubs;

    private final Follower follower;
    private double yawOffset;

//    private final Limelight3A limelight;

    private final ArrayList<Motor> motorArrayList;
    private final ArrayList<Servo> servoArrayList;

    public RobotHardware(HardwareMap hardwareMap) {

        allHubs = hardwareMap.getAll(LynxModule.class);
        for (LynxModule hub: allHubs)
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);

        follower = Constants.createFollower(hardwareMap);
        follower.setPose(new Pose(0, 0, 0));
        yawOffset = 0;

//        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        motorArrayList = new ArrayList<>();
        motorArrayList.add(new Motor("frontLeft",    true,  true,  false, hardwareMap));
        motorArrayList.add(new Motor("frontRight",   false, true,  false, hardwareMap));
        motorArrayList.add(new Motor("backLeft",     true,  true,  false, hardwareMap));
        motorArrayList.add(new Motor("backRight",    false, true,  false, hardwareMap));
        motorArrayList.add(new Motor("turretMotor",  false, true,  true,  hardwareMap));
        motorArrayList.add(new Motor("flywheelMotor",true,  false, true,  hardwareMap));
        motorArrayList.add(new Motor("intakeMotor",  false, false, true,  hardwareMap));


        servoArrayList = new ArrayList<>();
        servoArrayList.add(new Servo("hoodServo", hardwareMap));
    }

    private Motor getMotor(HardwareEnum hardwareEnum) {
        return motorArrayList.get(hardwareEnum.ordinal());
    }

    public void setMotorPower(HardwareEnum hardwareEnum, double power) {
        getMotor(hardwareEnum).setPower(power);
    }

    public int getMotorPosition(HardwareEnum hardwareEnum) {
        return getMotor(hardwareEnum).getPosition();
    }

    private Servo getServo(HardwareEnum hardwareEnum) {
        return servoArrayList.get(hardwareEnum.ordinal() - HardwareEnum.hoodServo.ordinal());
    }

    public void setServoPosition(HardwareEnum hardwareEnum, double position) {
        getServo(hardwareEnum).setPosition(position);
    }

    public double getImuYaw() {
        return (getRawYaw() + Math.PI + yawOffset) % 2*Math.PI;
    }

    public double getRawYaw() {
        return follower.getHeading();
    }

    public double getYawOffset() {
        return yawOffset;
    }

    public void resetYaw() {
        yawOffset = follower.getHeading();
    }

    public Follower getFollower() {
        return follower;
    }

    public void update() {
        for (LynxModule hub: allHubs)
            hub.clearBulkCache();

        follower.update();

        for (Motor m: motorArrayList)
            m.update();

        for (Servo s: servoArrayList)
            s.update();
    }
}