package org.firstinspires.ftc.teamcode;

import com.bylazar.telemetry.JoinedTelemetry;
import com.bylazar.telemetry.PanelsTelemetry;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.utils.Drawing;
import org.firstinspires.ftc.teamcode.utils.MovingAverageFilter;
import org.firstinspires.ftc.teamcode.utils.WolvesLogger;

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
    private Telemetry telemetry;
    private Drawing drawing;
    private MovingAverageFilter averageVoltage, averageHz;
    private ElapsedTime loopTimes;

    private List<LynxModule> lynxModules;


    public void init(OpMode opMode) {
        WolvesLogger.LOGGER.info("Initialized");

        hardware = opMode.hardwareMap;
        telemetry = new JoinedTelemetry(PanelsTelemetry.INSTANCE.getFtcTelemetry(), opMode.telemetry);

        drawing = new Drawing();
        lynxModules = opMode.hardwareMap.getAll(LynxModule.class);
        for (LynxModule module : lynxModules)
            module.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);

        averageVoltage = new MovingAverageFilter(200);
        averageHz      = new MovingAverageFilter(200);

        loopTimes = new ElapsedTime();

        DcMotorEx intake = initMotor("intakeMotor", DcMotorSimple.Direction.REVERSE);

    }

    public void read() {
        loopTimes.reset();

        for (LynxModule module : lynxModules)
            module.clearBulkCache();

        drawing.reset();
        averageVoltage.update(hardware.voltageSensor.iterator().next().getVoltage());

    }

    public void update() {

        drawing.update();
    }

    public void write() {

        telemetry.addData("Average Loop Hz", averageHz.update(1000/ loopTimes.milliseconds()));
        telemetry.update();
    }

    private DcMotorEx initMotor(String name, DcMotorSimple.Direction dir) {
        DcMotorEx motor = hardware.get(DcMotorEx.class, name);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motor.setDirection(dir);
        return motor;
    }
}
