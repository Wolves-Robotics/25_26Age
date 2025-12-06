package org.firstinspires.ftc.teamcode.hardware.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.bylazar.graph.GraphManager;
import com.bylazar.telemetry.JoinedTelemetry;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.control.PIDFController;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.enums.Color;
import org.firstinspires.ftc.teamcode.enums.HardwareEnum;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

@Config
public class ShootingSubsystem {
    private RobotHardware robotHardware;

    private PIDFController pidController, secondaryTurretPid, primaryVeloPid, secondaryVeloPid;
    public static double p = 0.008, i = 0, d = 0.0006, stp = 0.01, sti = 0.03, std = 0.0002, pp = 0.01, pd = 0.0004, sp = 0.001, sd = 0;
    public static double tSwitch = 30, vSwitch = 100, targetVelo = 0, testPower = 0, intakePower = 0, turretOffset = 2.34146, relativeAngle = 0, shootingAngle = 0;
    public static int target = 0, rawTarget = 0;

    public Pose targetPose, currentPose;

    private int position = 0;
    private double power = 0, velocity = 0, veloPower = 0, distance = 0;

    public static boolean turretPidOn, flywheelPidOOn;

    private boolean intaking, outtaking, speedUp, firing;

    private ElapsedTime firingTime;

    public void setTarget(int target) {
        ShootingSubsystem.target = target;
    }

    public ShootingSubsystem(RobotHardware robotHardware) {
        this.robotHardware = robotHardware;

        pidController = new PIDFController(new PIDFCoefficients(p, i, d, 0));
        secondaryTurretPid = new PIDFController(new PIDFCoefficients(stp, sti, std, 0));
        primaryVeloPid = new PIDFController(new PIDFCoefficients(pp, 0, pd, 0));
        secondaryVeloPid = new PIDFController(new PIDFCoefficients(sp, 0, sd, 0));

        targetPose = new Pose(142, 135);
        currentPose = robotHardware.getFollower().getPose();

        turretPidOn = false;
        flywheelPidOOn = false;

        intaking  = false;
        outtaking = false;
        speedUp   = false;
        firing    = false;

        firingTime = new ElapsedTime();
    }

    public void setTargetPose(Color teamColor) {
        if (teamColor == Color.RED) {
            targetPose = new Pose(143, 135);
        } else {
            targetPose = new Pose(1, 135);
        }
    }

    public void setTargetVelo(double velo) {
        targetVelo = velo;
    }

    public void setTurretPidOn(boolean turretPidOn) {
        this.turretPidOn = turretPidOn;

        robotHardware.setMotorPower(HardwareEnum.TURRET_MOTOR, 0);
    }

    public void setIntakeSpeed(double power) {
        robotHardware.setMotorPower(HardwareEnum.INTAKE_MOTOR, power);
    }

    public void setIntaking(boolean intaking) {
        this.intaking = intaking;

        if (this.intaking) {
            setOuttaking(false);
            setSpeedUp(false);
            turretPidOn = false;
            flywheelPidOOn = false;

            robotHardware.setMotorPower(HardwareEnum.INTAKE_MOTOR, 1);
        } else {
            flywheelPidOOn = false;
            robotHardware.setMotorPower(HardwareEnum.INTAKE_MOTOR, 0);
        }
    }

    public boolean isIntaking() {
        return intaking;
    }

    public void setOuttaking(boolean outtaking) {
        this.outtaking = outtaking;

        if (this.outtaking) {
            setIntaking(false);
            setSpeedUp(false);
            turretPidOn = false;
            flywheelPidOOn = true;

            robotHardware.setMotorPower(HardwareEnum.INTAKE_MOTOR, -1);
        } else {
            flywheelPidOOn = false;
            robotHardware.setMotorPower(HardwareEnum.INTAKE_MOTOR, 0);
        }
    }

    public boolean isOuttaking() {
        return outtaking;
    }

    public void setSpeedUp(boolean speedUp) {
        setSpeedUp(speedUp, 0);
    }

    public void setSpeedUp(boolean speedUp, double velo) {
        this.speedUp = speedUp && !intaking;

        if (this.speedUp) {
            turretPidOn = true;
            flywheelPidOOn = true;

            setTargetVelo(velo);
        } else if (!speedUp) {
            setFiring(false);
            turretPidOn = false;
            flywheelPidOOn = false;

            setTargetVelo(velo);
        }
    }

    public boolean isSpeedUp() {
        return speedUp;
    }

    public void setFiring(boolean firing) {
        this.firing = firing && speedUp;

        if (this.firing) {
            turretPidOn = true;
            flywheelPidOOn = true;

            firingTime.reset();
            robotHardware.setServoPosition(HardwareEnum.LATCH_SERVO, 1);
        } else if (!firing) {
            robotHardware.setServoPosition(HardwareEnum.LATCH_SERVO, 0.16);
            robotHardware.setMotorPower(HardwareEnum.INTAKE_MOTOR, 0);
        }
    }

    public boolean isFiring() {
        return firing;
    }

    private void updateTurretPid() {
        if (turretPidOn) {
            currentPose = robotHardware.getFollower().getPose();

            relativeAngle = (Math.atan2(
                    (targetPose.getY() - currentPose.getY()),
                    (targetPose.getX() - currentPose.getX()))
                    - currentPose.getHeading() + 2 * Math.PI) % (2 * Math.PI);
            shootingAngle = (-relativeAngle + turretOffset + 2 * Math.PI) % (2 * Math.PI);

            target = Math.max(Math.min(Math.toIntExact(Math.round(147.05917 * shootingAngle)), 675), 15);

            pidController.setCoefficients(new PIDFCoefficients(p, i, d, 0));
            secondaryTurretPid.setCoefficients(new PIDFCoefficients(stp, sti, std, 0));

            position = robotHardware.getMotorPosition(HardwareEnum.TURRET_MOTOR);

            if (Math.abs(target - position) > tSwitch) {
                pidController.updateError(target - position);
                power = pidController.run();
            } else {
                secondaryTurretPid.updateError(target - position);
                power = secondaryTurretPid.run();
            }
            robotHardware.setMotorPower(HardwareEnum.TURRET_MOTOR, power);
        } else {
            robotHardware.setMotorPower(HardwareEnum.TURRET_MOTOR, 0);
        }
    }

    private void updateFlywheelPid() {
        if (flywheelPidOOn) {
            currentPose = robotHardware.getFollower().getPose();

            distance = Math.sqrt(Math.pow(targetPose.getX() - currentPose.getX(), 2) + Math.pow(targetPose.getY() - currentPose.getY(), 2));

            targetVelo = (9.4 * distance) + 950;

            primaryVeloPid.setCoefficients(new PIDFCoefficients(pp, 0, pd, 0));
            secondaryVeloPid.setCoefficients(new PIDFCoefficients(sp, 0, sd, 0));

            velocity = robotHardware.getMotorVelocity(HardwareEnum.BACK_LEFT);

            if (Math.abs(targetVelo - velocity) > vSwitch) {
                primaryVeloPid.updateError(targetVelo - velocity);
                veloPower = primaryVeloPid.run();
            } else {
                secondaryVeloPid.updateError(targetVelo - velocity);
                veloPower = secondaryVeloPid.run();
            }

            veloPower += calcVeloToPow(targetVelo);

            if (targetVelo == 0) {
                veloPower = 0;
            }

            robotHardware.setMotorPower(HardwareEnum.FLYWHEEL_MOTOR,  veloPower);
            robotHardware.setMotorPower(HardwareEnum.FLYWHEEL_MOTOR2, veloPower);
        } else {
            robotHardware.setMotorPower(HardwareEnum.FLYWHEEL_MOTOR,  0);
            robotHardware.setMotorPower(HardwareEnum.FLYWHEEL_MOTOR2, 0);
        }
    }

    public double calcVeloToPow(double v) {
        return ((1.03369 * Math.pow(10, -10)) * Math.pow(v, 3))
                - ((5.14346 * Math.pow(10, -7)) * Math.pow(v, 2))
                + (0.00119053 * v)
                - (0.375915);
    }

    public void update() {

        if (intaking) {
            
        }
        
        if (outtaking) {
            
        }
        
        if (speedUp) {
            
        }
        
        if (firing) {
            if (firingTime.milliseconds() > 250) {
                robotHardware.setMotorPower(HardwareEnum.INTAKE_MOTOR, 1);
            }
        }

        updateTurretPid();
        updateFlywheelPid();

    }

    public void updatePanel(JoinedTelemetry joinedTelemetry, GraphManager manager) {
        joinedTelemetry.addData("Distance", distance);

        joinedTelemetry.addData("Position", position);
        joinedTelemetry.addData("Power", power);
        joinedTelemetry.addData("Target", target);

        joinedTelemetry.addData("Relative Angle", relativeAngle);
        joinedTelemetry.addData("Shooting Angle", shootingAngle);

        joinedTelemetry.addData("Flywheel Velocity", velocity);
        joinedTelemetry.addData("Flywheel Power",    veloPower);
        joinedTelemetry.addData("Flywheel Target",   targetVelo);

        manager.addData("Position", position);
        manager.addData("Target", target);
    }
}
