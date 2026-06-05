package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.pedroPathing.PedroConstants;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.FlywheelSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.GeneralSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.TurretSubsystem;
import org.firstinspires.ftc.teamcode.utils.config.Constants;
import org.firstinspires.ftc.teamcode.utils.enums.Alliance;
import org.firstinspires.ftc.teamcode.utils.config.MatchDetails;
import org.firstinspires.ftc.teamcode.utils.control.MovingAverageFilter;
import org.firstinspires.ftc.teamcode.utils.ExternalTools;
import org.firstinspires.ftc.teamcode.utils.enums.RobotState;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import java.util.List;

public class Switchback {
    private static Switchback instance;

    public static Switchback getInstance() {
        if (instance == null) {
            instance = new Switchback();
        }
        return instance;
    }

    private HardwareMap hardware;
    private MovingAverageFilter averageVoltage, averageHz;
    private ElapsedTime loopTimer;

    private List<LynxModule> lynxModules;

    private Follower follower;

    private DriveSubsystem driveSubsystem;
    private TurretSubsystem turretSubsystem;
    private FlywheelSubsystem flywheelSubsystem;
    private GeneralSubsystem generalSubsystem;


    public void init(OpMode opMode) {
        ExternalTools.initialize(opMode.telemetry);
        ExternalTools.LOGGER.info("Initialized");

        hardware = opMode.hardwareMap;

        lynxModules = hardware.getAll(LynxModule.class);
        for (LynxModule module : lynxModules)
            module.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);

        averageVoltage = new MovingAverageFilter(200, 12.5);
        averageHz      = new MovingAverageFilter(200, 100);

        loopTimer = new ElapsedTime();

        follower = PedroConstants.createFollower(hardware);

        if (MatchDetails.ALLIANCECOLOR == Alliance.RED) {
            MatchDetails.target = Constants.RED_TARGET_POS;
            MatchDetails.aprilTag = Constants.RED_APRILTAG;
        } else {
            MatchDetails.target = Constants.BLUE_TARGET_POS;
            MatchDetails.aprilTag = Constants.BLUE_APRILTAG;
        }

        //HARDWARE CONTAINER
        DcMotorEx fL = initMotor("frontLeft", DcMotorSimple.Direction.REVERSE);
        DcMotorEx fR = initMotor("frontRight", DcMotorSimple.Direction.FORWARD);
        DcMotorEx bL = initMotor("backLeft", DcMotorSimple.Direction.REVERSE);
        DcMotorEx bR = initMotor("backRight", DcMotorSimple.Direction.FORWARD);
       //Shooter + Intake Class
        DcMotorEx flywheelMotor1 = initMotor("flywheelMotor",  DcMotorSimple.Direction.REVERSE);
        DcMotorEx flywheelMotor2 = initMotor("flywheelMotor2", DcMotorSimple.Direction.FORWARD);
        DcMotorEx intake = initMotor("intakeMotor", DcMotorSimple.Direction.REVERSE);
        DcMotorEx intake2 = initMotor("intake2", DcMotorSimple.Direction.FORWARD);

        Servo turret = hardware.get(Servo.class, "turret");
        turret.setPosition(0);

        Servo hood = hardware.get(Servo.class, "hoodServo");
        hood.setDirection(Servo.Direction.REVERSE);
        hood.setPosition(0);

        Servo intakeLift = hardware.get(Servo.class, "intakeLift");
        intakeLift.setDirection(Servo.Direction.REVERSE);
        intakeLift.setPosition(0);

        Servo latch = hardware.get(Servo.class, "latch");
        latch.setPosition(0);

        //END HARDWARE CONTAINER
        driveSubsystem = new DriveSubsystem(
                new DriveSubsystem.DriveStuff(
                    fL,
                    fR,
                    bL,
                    bR,
                    follower
                ));

        generalSubsystem = new GeneralSubsystem();
        turretSubsystem = new TurretSubsystem();
        flywheelSubsystem = new FlywheelSubsystem();

        generalSubsystem.init(
                new GeneralSubsystem.GeneralStuff(
                        intake,
                        intake2,
                        latch,
                        intakeLift,
                        ()-> false,
                        () -> follower.getPose().getY() < 45,
                        () -> 0.,
                        () -> 0.,
                        flywheelSubsystem::getError,
                        flywheelSubsystem::getDistance
                ));

//        turretSubsystem.init(
//                new TurretSubsystem.TurretStuff(
//                        turretMotor,
//                        () -> -turretMotor.getCurrentPosition(),
//                        follower,
//                        limelight,
//                        generalSubsystem::getRobotState,
//                        follower::getVelocity
//                ));

        flywheelSubsystem.init(
                new FlywheelSubsystem.FlywheelStuff(
                        flywheelMotor1,
                        flywheelMotor2,
                        fL::getVelocity,
                        fL::getCurrentPosition, // encoder on bL port idk man
                        hood,
                        follower,
                        opMode.gamepad1,
                        generalSubsystem::getRobotState,
                        follower::getVelocity
                ));
    }

    public void setPose(Pose pose) {
        follower.setPose(pose);
        follower.updatePose();
    }

    public void resetPose() {
        if (MatchDetails.ALLIANCECOLOR == Alliance.RED) {
            follower.setPose(Constants.RED_RESET_POS);
        } else {
            follower.setPose(Constants.BLUE_RESET_POS);
        }
    }

    public void setFinalPose() {
        MatchDetails.poseAtStop = follower.getPose();
    }

    public Follower getFollower() {
        return follower;
    }

    public void read() {
        loopTimer.reset();

        for (LynxModule module : lynxModules)
            module.clearBulkCache();

        ExternalTools.fieldReset();
        averageVoltage.update(hardware.voltageSensor.iterator().next().getVoltage());

        follower.updatePose();

        driveSubsystem.read();
//        turretSubsystem.read();
        flywheelSubsystem.read();
        generalSubsystem.read();
    }

    public void update() {
        driveSubsystem.update();
//        turretSubsystem.update();
        flywheelSubsystem.update();
        generalSubsystem.update();
    }

    public void write() {
        driveSubsystem.write();
//        turretSubsystem.write();
        flywheelSubsystem.write();
        generalSubsystem.write();

        ExternalTools.TELEMETRY.addData("Average Loop Hz", averageHz.update(1000/loopTimer.milliseconds()));
        ExternalTools.write();
    }

    public void changeState(RobotState robotState) {
        generalSubsystem.changeState(robotState);
    }

    public void switchSpeedUp() {
        if (generalSubsystem.getRobotState() == RobotState.SPEED_UP) {
            generalSubsystem.changeState(RobotState.IDLE);
        } else {
            generalSubsystem.changeState(RobotState.SPEED_UP);
        }
    }

    public DriveSubsystem getDriveSub() {
        return driveSubsystem;
    }

    public TurretSubsystem getTurretSub() {
        return turretSubsystem;
    }

    public FlywheelSubsystem getFlywheelSub() {
        return flywheelSubsystem;
    }

    public GeneralSubsystem getGeneralSubsystem() {
        return generalSubsystem;
    }

    private DcMotorEx initMotor(String name, DcMotorSimple.Direction dir) {
        DcMotorEx motor = hardware.get(DcMotorEx.class, name);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motor.setDirection(dir);
        return motor;
    }

}
