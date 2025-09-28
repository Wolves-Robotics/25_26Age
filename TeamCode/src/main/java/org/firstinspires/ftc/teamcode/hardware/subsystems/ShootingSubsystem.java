package org.firstinspires.ftc.teamcode.hardware.subsystems;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.graph.GraphManager;
import com.bylazar.telemetry.JoinedTelemetry;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.control.PIDFController;

import org.firstinspires.ftc.teamcode.enums.HardwareEnum;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

@Configurable
public class ShootingSubsystem {
    private RobotHardware robotHardware;

    private PIDFController pidController;
    private static double p = 0, i = 0, d = 0
            ;
    private static int target = 0;

    private int position = 0;
    private double power = 0;

    public void setTarget(int target) {
        ShootingSubsystem.target = target;
    }

    public ShootingSubsystem(RobotHardware robotHardware) {
        this.robotHardware = robotHardware;

        pidController = new PIDFController(new PIDFCoefficients(p, i, d, 0));
    }

    public void update() {
        pidController.setCoefficients(new PIDFCoefficients(p, i, d, 0));

        position = robotHardware.getMotorPosition(HardwareEnum.turretMotor);
        pidController.setTargetPosition(target);
        power = pidController.run();
        robotHardware.setMotorPower(HardwareEnum.turretMotor, target);
    }

    public void updatePanel(JoinedTelemetry joinedTelemetry, GraphManager manager) {
        joinedTelemetry.addData("Position", position);
        joinedTelemetry.addData("Power", power);

        manager.addData("Position", position);
        manager.addData("Target", target);
    }
}
