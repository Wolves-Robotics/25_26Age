package org.firstinspires.ftc.teamcode.hardware.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.bylazar.graph.GraphManager;
import com.bylazar.telemetry.JoinedTelemetry;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.control.PIDFController;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.enums.HardwareEnum;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

@Config
public class ShootingSubsystem {
    private RobotHardware robotHardware;

    private PIDFController pidController;
    public static double p = 0.0163, i = 0, d = 0.00175;
    public static int target = 0;

    public Pose targetPose, currentPose;

    private int position = 0;
    private double power = 0;

    private double intakePower;

    private boolean pidOn;

    private boolean intaking, outtaking, speedUp, firing, staggering;

    private ElapsedTime staggerTime;


    private boolean sensing = false;

    private double tX = 0;

    private int flywheelPosition, previousFlywheelPosition;
    private double flywheelVelocity;
    private ElapsedTime deltaTime;

    public void setTarget(int target) {
        ShootingSubsystem.target = target;
    }

    public ShootingSubsystem(RobotHardware robotHardware) {
        this.robotHardware = robotHardware;

        pidController = new PIDFController(new PIDFCoefficients(p, i, d, 0));

        targetPose = new Pose(142, 137.5);
        currentPose = robotHardware.getFollower().getPose();

        pidOn = true;

        intakePower = 1;

        intaking  = false;
        outtaking = false;
        speedUp   = false;
        firing    = false;

        staggering = true;
        staggerTime = new ElapsedTime();

        flywheelPosition = robotHardware.getMotorPosition(HardwareEnum.FLYWHEEL_MOTOR);
        previousFlywheelPosition = flywheelPosition;

        flywheelVelocity = 0;

        deltaTime = new ElapsedTime();
    }

    public void setPidOn(boolean pidOn) {
        this.pidOn = pidOn;

        robotHardware.setMotorPower(HardwareEnum.TURRET_MOTOR, 0);
    }

    public void setIntakeSpeed(double power) {
        robotHardware.setMotorPower(HardwareEnum.INTAKE_MOTOR, power);
    }

    public void toggleIntake() {
        if (intakePower == 1) {
            intakePower = 0.8;
        } else {
            intakePower = 1;
        }
    }

    public void setIntaking(double power) {
        if (power > 0.3) {
            intaking = true;
            robotHardware.setMotorPower(HardwareEnum.INTAKE_MOTOR, power*intakePower);
            robotHardware.setMotorPower(HardwareEnum.FLYWHEEL_MOTOR, -0.75);
        } else {
            if (intaking == true) {
                robotHardware.setMotorPower(HardwareEnum.INTAKE_MOTOR, 0);
                robotHardware.setMotorPower(HardwareEnum.FLYWHEEL_MOTOR, 0);
            }
            intaking = false;
        }
    }

    public boolean isIntaking() {
        return intaking;
    }

    public void setOuttaking(boolean outtaking) {
        this.outtaking = outtaking;

        if (outtaking) {
            robotHardware.setMotorPower(HardwareEnum.INTAKE_MOTOR, -1);
            robotHardware.setMotorPower(HardwareEnum.FLYWHEEL_MOTOR, -1);
        } else {
            robotHardware.setMotorPower(HardwareEnum.INTAKE_MOTOR, 0);
            robotHardware.setMotorPower(HardwareEnum.FLYWHEEL_MOTOR, 0);
        }
    }

    public boolean isOuttaking() {
        return outtaking;
    }

    public void setSpeedUp(boolean speedUp) {
        setSpeedUp(speedUp, 0);
    }

    public void setSpeedUp(boolean speedUp, double power) {
        this.speedUp = speedUp && !intaking;
        this.firing = firing && speedUp;

        if (speedUp) {
            robotHardware.setMotorPower(HardwareEnum.FLYWHEEL_MOTOR, power);
            robotHardware.setMotorPower(HardwareEnum.FLYWHEEL_MOTOR2, power);
        } else {
            robotHardware.setMotorPower(HardwareEnum.FLYWHEEL_MOTOR, 0);
            robotHardware.setMotorPower(HardwareEnum.FLYWHEEL_MOTOR2, 0);
            robotHardware.setMotorPower(HardwareEnum.INTAKE_MOTOR, 0);
        }
    }

    public boolean isSpeedUp() {
        return speedUp;
    }

    public void setFiring(boolean firing) {
        this.firing = firing && speedUp;
        staggering = true;

//        if (firing) {
//            robotHardware.setMotorPower(HardwareEnum.INTAKE_MOTOR, 1);
//        } else {
//            robotHardware.setMotorPower(HardwareEnum.INTAKE_MOTOR, 0);
//        }
    }

    public boolean isFiring() {
        return firing;
    }

    public void setTurretPower(double power) {
        if (!pidOn) {
            robotHardware.setMotorPower(HardwareEnum.TURRET_MOTOR, power * 0.4);
        }
    }

    public void update() {
        double seconds = deltaTime.seconds();

        if (pidOn) {
            currentPose = robotHardware.getFollower().getPose();

            double relativeAngle = Math.atan2(
                    (targetPose.getY() - currentPose.getY()),
                    (targetPose.getX() - currentPose.getX()));
            double shootingAngle = (relativeAngle - currentPose.getHeading() + Math.PI)
                    % (2 * Math.PI);

            target = Math.toIntExact(Math.round((-55.51324 * shootingAngle) + 351));


            pidController.setCoefficients(new PIDFCoefficients(p, i, d, 0));

            position = robotHardware.getMotorPosition(HardwareEnum.TURRET_MOTOR);
            pidController.updatePosition(position);
            pidController.setTargetPosition(target);
            power = pidController.run();
            robotHardware.setMotorPower(HardwareEnum.TURRET_MOTOR, power);

//            LLResult result = robotHardware.getLLResult();
//            sensing = result != null && result.isValid();
//            if (sensing) {
//
//                robotHardware.setMotorPower(HardwareEnum.TURRET_MOTOR, result.getTx() * 0.014);
//
//                limelightController.setCoefficients(new PIDFCoefficients(lP, lI, lD, 0));
//;
//                limelightController.updatePosition(0);
//                tX = result.getTx();
//                limelightController.setTargetPosition(tX);
//                power = limelightController.run();
//                robotHardware.setMotorPower(HardwareEnum.TURRET_MOTOR, power);
//
//                pidController.run();
//
//            } else {
//                pidController.setCoefficients(new PIDFCoefficients(p, i, d, 0));
//
//                position = robotHardware.getMotorPosition(HardwareEnum.TURRET_MOTOR);
//                pidController.updatePosition(position);
//                pidController.setTargetPosition(target);
//                power = pidController.run();
//                robotHardware.setMotorPower(HardwareEnum.TURRET_MOTOR, power);
//
//                limelightController.run();
//                robotHardware.setMotorPower(HardwareEnum.TURRET_MOTOR, 0);
//
//            }

            if (speedUp) {
                previousFlywheelPosition = flywheelPosition;
                flywheelPosition = robotHardware.getMotorPosition(HardwareEnum.FLYWHEEL_MOTOR);

                flywheelVelocity = (double) (flywheelPosition - previousFlywheelPosition) / seconds;

            }
            deltaTime.reset();
        }

        if (firing) {
            if (staggering && staggerTime.milliseconds() > 250) {
                robotHardware.setMotorPower(HardwareEnum.INTAKE_MOTOR, 1);
                staggerTime.reset();
                staggering = false;
            } else if (!staggering && staggerTime.milliseconds() > 150) {
                robotHardware.setMotorPower(HardwareEnum.INTAKE_MOTOR, 0);
                staggerTime.reset();
                staggering = true;
            }
        }

    }

    public void updatePanel(JoinedTelemetry joinedTelemetry, GraphManager manager) {
        joinedTelemetry.addData("Position", position);
        joinedTelemetry.addData("Power", power);
        joinedTelemetry.addData("Target", target);

        joinedTelemetry.addData("sensing", sensing);
        joinedTelemetry.addData("Tx", tX);

        joinedTelemetry.addData("Flywheel Velocity", flywheelVelocity);
        joinedTelemetry.addData("Position", flywheelPosition);
        joinedTelemetry.addData("Prev Position", previousFlywheelPosition);

        manager.addData("Position", position);
        manager.addData("Target", target);
    }
}
