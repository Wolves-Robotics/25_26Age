package org.firstinspires.ftc.teamcode.hardware.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.bylazar.graph.GraphManager;
import com.bylazar.telemetry.JoinedTelemetry;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.control.PIDFController;

import org.firstinspires.ftc.teamcode.enums.HardwareEnum;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

@Config
public class ShootingSubsystem {
    private RobotHardware robotHardware;

    private PIDFController pidController;
    public static double p = 0.0163, i = 0, d = 0.00175;
    public static int target = 0;

    private int position = 0;
    private double power = 0;

    private boolean pidOn;

    private boolean intaking, outtaking, speedUp, firing;

    public void setTarget(int target) {
        ShootingSubsystem.target = target;
    }

    public ShootingSubsystem(RobotHardware robotHardware) {
        this.robotHardware = robotHardware;

        pidController = new PIDFController(new PIDFCoefficients(p, i, d, 0));

        pidOn = true;

        intaking  = false;
        outtaking = false;
        speedUp   = false;
        firing    = false;
    }

    public void setPidOn(boolean pidOn) {
        this.pidOn = pidOn;

        robotHardware.setMotorPower(HardwareEnum.turretMotor, 0);
    }

    public void setIntakeSpeed(double power) {
        robotHardware.setMotorPower(HardwareEnum.intakeMotor, power);
    }

    public void setIntaking() {
        setIntaking(!intaking);
    }

    public void setIntaking(boolean intaking) {
        this.intaking = intaking;

        if (intaking) {
            robotHardware.setMotorPower(HardwareEnum.intakeMotor, 1);
            robotHardware.setMotorPower(HardwareEnum.flywheelMotor, -0.67);
        } else {
            robotHardware.setMotorPower(HardwareEnum.intakeMotor, 0);
            robotHardware.setMotorPower(HardwareEnum.flywheelMotor, 0);
        }
    }

    public boolean isIntaking() {
        return intaking;
    }

    public void setOuttaking(boolean outtaking) {
        this.outtaking = outtaking;

        if (outtaking) {
            robotHardware.setMotorPower(HardwareEnum.intakeMotor, -1);
        } else {
            robotHardware.setMotorPower(HardwareEnum.intakeMotor, 0);
            setIntaking(intaking);
        }
    }

    public boolean isOuttaking() {
        return outtaking;
    }

    public void setSpeedUp(boolean speedUp) {
        this.speedUp = speedUp && !intaking;
        this.firing = firing && speedUp;

        if (speedUp) {
            robotHardware.setMotorPower(HardwareEnum.flywheelMotor, 0.8);
        } else {
            robotHardware.setMotorPower(HardwareEnum.flywheelMotor, 0);
        }
    }

    public boolean isSpeedUp() {
        return speedUp;
    }

    public void setFiring(boolean firing) {
        this.firing = firing && speedUp;

        if (firing) {
            robotHardware.setMotorPower(HardwareEnum.intakeMotor, 1);
        } else {
            robotHardware.setMotorPower(HardwareEnum.intakeMotor, 0);
        }
    }

    public boolean isFiring() {
        return firing;
    }

    public void setTurretPower(double power) {
        if (!pidOn) {
            robotHardware.setMotorPower(HardwareEnum.turretMotor, power * 0.4);
        }
    }

    public void update() {
        if (pidOn) {
            pidController.setCoefficients(new PIDFCoefficients(p, i, d, 0));

            position = robotHardware.getMotorPosition(HardwareEnum.turretMotor);
            pidController.updatePosition(position);
            pidController.setTargetPosition(target);
            power = pidController.run();
            robotHardware.setMotorPower(HardwareEnum.turretMotor, power);
        }

    }

    public void updatePanel(JoinedTelemetry joinedTelemetry, GraphManager manager) {
        joinedTelemetry.addData("Position", position);
        joinedTelemetry.addData("Power", power);
        joinedTelemetry.addData("Target", target);

        manager.addData("Position", position);
        manager.addData("Target", target);
    }
}
