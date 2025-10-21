package org.firstinspires.ftc.teamcode.hardware;

import com.bylazar.graph.GraphManager;
import com.bylazar.graph.PanelsGraph;
import com.bylazar.telemetry.JoinedTelemetry;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.enums.HardwareEnum;
import org.firstinspires.ftc.teamcode.hardware.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.hardware.subsystems.ShootingSubsystem;
import org.firstinspires.ftc.teamcode.utils.Action;

import java.util.ArrayList;
import java.util.function.BooleanSupplier;

public class Robot {
    private Gamepad gamepad1;
    private Gamepad gamepad2;

    private boolean initLoop = true, auto;

    private JoinedTelemetry telemetry;
    private GraphManager manager;

    private RobotHardware     robotHardware;
    private DriveSubsystem    driveSubsystem;
    private ShootingSubsystem shootingSubsystem;

    private MatchSelection matchSelection;

    private ArrayList<Action> actions;

    public Robot(Gamepad gamepad1, Gamepad gamepad2, JoinedTelemetry telemetry, HardwareMap hardwareMap) {
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;

        this.telemetry = telemetry;
        manager = PanelsGraph.INSTANCE.getManager();

        robotHardware     = new RobotHardware(hardwareMap);
        driveSubsystem    = new DriveSubsystem(robotHardware);
        shootingSubsystem = new ShootingSubsystem(robotHardware);

        actions = new ArrayList<>();

        auto = false;
    }

    public void initAuto() {
        MatchSelection.reset();
        matchSelection = MatchSelection.getInstance();

        auto = true;
    }

    public void initTeleop() {
        matchSelection = MatchSelection.getInstance();

        auto = false;
    }

    public void initHardwareSelection() {
        addAction(() -> gamepad1.dpad_up,    () -> matchSelection.decrementSelect());
        addAction(() -> gamepad1.dpad_down,  () -> matchSelection.incrementSelect());
        addAction(() -> gamepad1.dpad_right, () -> matchSelection.incrementSelected());
        addAction(() -> gamepad1.dpad_left,  () -> matchSelection.decrementSelected());
    }

    public void startMainLoop() {
        initLoop = false;
        clearActions();
    }

    public void setDrivingActions() {
        addAction(() -> true,
                  () -> driveSubsystem.setDriveCoefficients(
                                            gamepad1.left_stick_x,
                                            gamepad1.left_stick_y,
                                            gamepad1.right_stick_x),
                   true);
        addAction(() -> gamepad1.options, () -> robotHardware.resetYaw());
    }

    public void addAction(BooleanSupplier booleanSupplier, Runnable pressRunnable) {
        addAction(new Action(booleanSupplier, pressRunnable));
    }

    public void addAction(BooleanSupplier booleanSupplier, Runnable pressRunnable, boolean hold) {
        addAction(new Action(booleanSupplier, pressRunnable, hold));
    }

    public void addAction(BooleanSupplier booleanSupplier, Runnable pressRunnable, Runnable releaseRunnable) {
        addAction(new Action(booleanSupplier, pressRunnable, releaseRunnable));
    }

    public void addAction(BooleanSupplier booleanSupplier, Runnable pressRunnable, boolean hold, Runnable releaseRunnable) {
        addAction(new Action(booleanSupplier, pressRunnable, hold, releaseRunnable));
    }

    public void addAction(Action a) {
        actions.add(a);
    }

    public void clearActions() {
        actions.clear();
    }

    public RobotHardware getRobotHardware() {
        return robotHardware;
    }

    public MatchSelection getMatchSelection() {
        return matchSelection;
    }

    public DriveSubsystem driveSubsystem() {
        return driveSubsystem;
    }

    public ShootingSubsystem shootingSubsystem() {
        return shootingSubsystem;
    }

    public void update() {
        robotHardware.update();

        //
        for (Action a: actions) {
            a.update();
        }

        // telemety
        if (initLoop) {
            matchSelection.updateTele(telemetry);
        } else {
            if (!auto) {
                driveSubsystem.update();
            }
            shootingSubsystem.update();

            shootingSubsystem.updatePanel(telemetry, manager);
        }

        telemetry.addData("encoder", robotHardware.getTurretAngle());

        telemetry.update();
        manager.update();
    }


}
