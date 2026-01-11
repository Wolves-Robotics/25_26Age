package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.utils.MovingAverageFilter;
import org.firstinspires.ftc.teamcode.utils.ExternalTools;

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


    public void init(OpMode opMode) {
        ExternalTools.initialize(opMode.telemetry);
        ExternalTools.LOGGER.info("Initialized");

        hardware = opMode.hardwareMap;

        lynxModules = opMode.hardwareMap.getAll(LynxModule.class);
        for (LynxModule module : lynxModules)
            module.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);

        averageVoltage = new MovingAverageFilter(200);
        averageHz      = new MovingAverageFilter(200);

        loopTimer = new ElapsedTime();

        follower = Constants.createFollower(hardware);
        follower.setPose(new Pose(72, 72, 0));

        DcMotorEx fL = initMotor("frontLeft", DcMotorSimple.Direction.REVERSE);
        DcMotorEx fR = initMotor("frontRight", DcMotorSimple.Direction.FORWARD);
        DcMotorEx bL = initMotor("backLeft", DcMotorSimple.Direction.REVERSE);
        DcMotorEx bR = initMotor("backRight", DcMotorSimple.Direction.FORWARD);


        driveSubsystem = new DriveSubsystem(
                new DriveSubsystem.DriveStuff(
                    fL,
                    fR,
                    bL,
                    bR,
                    follower
                )
        );

    }

    public void read() {
        loopTimer.reset();

        for (LynxModule module : lynxModules)
            module.clearBulkCache();

        ExternalTools.fieldReset();
        averageVoltage.update(hardware.voltageSensor.iterator().next().getVoltage());

        driveSubsystem.read();
    }

    public void update() {
        follower.updatePose();
        driveSubsystem.update();

    }

    public void write() {
        driveSubsystem.write();

        ExternalTools.TELEMETRY.addData("Average Loop Hz", averageHz.update(1000/ loopTimer.milliseconds()));
        ExternalTools.write();
    }

    public DriveSubsystem getDriveSub() {
        return driveSubsystem;
    }

    private DcMotorEx initMotor(String name, DcMotorSimple.Direction dir) {
        DcMotorEx motor = hardware.get(DcMotorEx.class, name);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motor.setDirection(dir);
        return motor;
    }
}
