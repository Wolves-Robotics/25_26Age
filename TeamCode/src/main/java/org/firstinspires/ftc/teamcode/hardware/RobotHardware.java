package org.firstinspires.ftc.teamcode.hardware;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.ImuOrientationOnRobot;

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

    private final IMU imu;

//    private final Limelight3A limelight;

    private final ArrayList<Motor> motorArrayList;
    private final ArrayList<Servo> servoArrayList;

    public RobotHardware(HardwareMap hardwareMap) {

        allHubs = hardwareMap.getAll(LynxModule.class);
        for (LynxModule hub: allHubs)
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);

        follower = Constants.createFollower(hardwareMap);
        follower.setPose(new Pose(0, 0, 0));

        imu = hardwareMap.get(IMU.class, "imu");
        imu.initialize(new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD
        )));

//        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        motorArrayList = new ArrayList<>();
        motorArrayList.add(new Motor("frontLeft",    true,  false, false, hardwareMap));
        motorArrayList.add(new Motor("frontRight",   false, false, false, hardwareMap));
        motorArrayList.add(new Motor("backLeft",     true,  false, false, hardwareMap));
        motorArrayList.add(new Motor("backRight",    false, false, false, hardwareMap));
        motorArrayList.add(new Motor("turretMotor",  true,  true,  true,  hardwareMap));
        motorArrayList.add(new Motor("flywheelMotor",true,  false, true,  hardwareMap));
        motorArrayList.add(new Motor("intakeMotor",  true,  true,  true,  hardwareMap));


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
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
    }

    public void resetYaw() {
        imu.resetYaw();
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