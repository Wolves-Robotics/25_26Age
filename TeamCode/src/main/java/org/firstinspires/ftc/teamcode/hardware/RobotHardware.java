package org.firstinspires.ftc.teamcode.hardware;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.enums.Color;
import org.firstinspires.ftc.teamcode.enums.HardwareEnum;
import org.firstinspires.ftc.teamcode.hardware.singleSystems.Motor;
import org.firstinspires.ftc.teamcode.hardware.singleSystems.Servo;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import java.util.ArrayList;
import java.util.List;

public class RobotHardware {
    List<LynxModule> allHubs;

    private final Follower follower;

    private final Limelight3A limelight;

    private final ArrayList<Motor> motorArrayList;
    private final ArrayList<Servo> servoArrayList;

    public RobotHardware(HardwareMap hardwareMap) {

        allHubs = hardwareMap.getAll(LynxModule.class);
        for (LynxModule hub: allHubs)
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);

        follower = Constants.createFollower(hardwareMap);
        follower.setPose(new Pose(121.1, 129.2, Math.toRadians(36)));

        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(0);
        limelight.start();

        motorArrayList = new ArrayList<>();
        motorArrayList.add(new Motor("frontLeft",     true,  false, false, hardwareMap));
        motorArrayList.add(new Motor("frontRight",    false, false, false, hardwareMap));
        motorArrayList.add(new Motor("backLeft",      true,  false, false, hardwareMap));
        motorArrayList.add(new Motor("backRight",     false, false, false, hardwareMap));
        motorArrayList.add(new Motor("turretMotor",   true,  true,  true,  hardwareMap));
        motorArrayList.add(new Motor("flywheelMotor", false, false, true,  hardwareMap));
        motorArrayList.add(new Motor("flywheelMotor2",true,  false, true,  hardwareMap));
        motorArrayList.add(new Motor("intakeMotor",   true,  true,  true,  hardwareMap));


        servoArrayList = new ArrayList<>();
        servoArrayList.add(new Servo("hoodServo", hardwareMap));
        servoArrayList.add(new Servo("latch", hardwareMap));
        servoArrayList.add(new Servo("light", hardwareMap));
    }

    public void resetFollowerPose(Color teamColor) {
        if (teamColor == Color.RED) {
            follower.setPose(new Pose(121.1, 129.2, Math.toRadians(36)));
        } else {
            follower.setPose(new Pose(22.9, 129.2, Math.toRadians(144)));
        }
    }


    public void resetFollowerPose(Pose pose) {
        follower.setPose(pose);
    }

    public Pose getCurrentPose() {
        return follower.getPose();
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

    public double getMotorVelocity(HardwareEnum hardwareEnum) {
        return getMotor(hardwareEnum).getVelocity();
    }

    public String getMotorName(HardwareEnum hardwareEnum) {
        return getMotor(hardwareEnum).getName();
    }

    public void setMotorBrake(HardwareEnum hardwareEnum, boolean brake) {
        getMotor(hardwareEnum).setBrake(brake);
    }

    private Servo getServo(HardwareEnum hardwareEnum) {
        return servoArrayList.get(hardwareEnum.ordinal() - HardwareEnum.HOOD_SERVO.ordinal());
    }

    public void setServoPosition(HardwareEnum hardwareEnum, double position) {
        getServo(hardwareEnum).setPosition(position);
    }

    public double getYaw() {
        return follower.getHeading();
    }

    public Follower getFollower() {
        return follower;
    }

    public LLResult getLLResult() {
        return limelight.getLatestResult();
    }

    public void setPipeLine(int index) {
        limelight.pipelineSwitch(index);
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