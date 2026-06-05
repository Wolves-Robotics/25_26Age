package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Vector;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utils.ExternalTools;
import org.firstinspires.ftc.teamcode.utils.config.MatchDetails;
import org.firstinspires.ftc.teamcode.utils.control.PIDFController;
import org.firstinspires.ftc.teamcode.utils.enums.RobotState;
import org.joml.Vector2d;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

@Configurable
public class FlywheelSubsystem {
    private final double TURRETFROMCENTERINCH = 57 / 25.4;

    private FlywheelStuff hardware;

    public static boolean isRunning;
    private double angle, vel, distance, p0, p1, error;

    private PIDFController flywheelPIDF;

    public static boolean pidTuning = false, velToPowerTune = false;
    public static double p = 0.004, i = 0, d = 0, f = 1, targetVel;

    public void init(FlywheelStuff hardware) {
        this.hardware = hardware;

        flywheelPIDF = new PIDFController(0, 0, 0, 0);

        isRunning = false;

        targetVel = 1000;
        angle = 69;
        p0 = 14.5;
        p1 = 45;
    }

    public void read() {

    }

    public void update() {
        isRunning = hardware.state.get() == RobotState.SPEED_UP
                || hardware.state.get() == RobotState.FIRE;
        if (isRunning) {
            double h = hardware.follower.getHeading();
            Vector2d robot = new Vector2d(hardware.follower.getPose().getX(), hardware.follower.getPose().getY());
            Vector2d turret = robot.sub(Math.cos(h) * TURRETFROMCENTERINCH, Math.sin(h) * TURRETFROMCENTERINCH);
            distance = Math.hypot(
                    MatchDetails.target.x - turret.x,
                    MatchDetails.target.y - turret.y
            );

            if (robot.y < 48) {
                angle = 53;
                hardware.hood.setPosition(0.8);
            } else if (48 < robot.y && robot.y < 97) {
                hardware.hood.setPosition(.25);

            }else {
                angle = 69;
                hardware.hood.setPosition(0.08);
            }

            double w = Math.toRadians(angle);

            double a = -9.81/2;
            double c = p0 - p1;

            vel = Math.sqrt(-(a* distance * distance)/(Math.cos(w) * Math.cos(w) * (c + distance *Math.tan(w))));
            if (-(2*c)/Math.tan(w) > distance)
                vel = 0;

            if (!pidTuning) {
                if (robot.y < 45) {
                    targetVel = 12.90487*vel+1510.69207;
                } else {
                    targetVel = (-0.313775*vel*vel*vel)+(34.18666*vel*vel)-(1187.99914*vel)+(14559.5645);
                }
            }

            error = targetVel - hardware.speed.getAsDouble();

            flywheelPIDF.setPIDF(p, i, d, 0);
            flywheelPIDF.setFeedforward(0, 0, 0);
            if (Math.abs(error) < 40) {
                hardware.controller.rumble(200);
            }
            double power = flywheelPIDF.calculate(error) + (f * velToPower(targetVel));
            if (targetVel == 0) power = 0;
            if (-(2*c)/Math.tan(w) > distance) power = 0;
            if (velToPowerTune) power = targetVel;
            hardware.motor1.setPower(power);
            hardware.motor2.setPower(power);
        } else {
            hardware.motor1.setPower(0);
            hardware.motor2.setPower(0);
        }
    }

    private double velToPower(double vel) {
        return (3.23839E-10*vel*vel*vel) - (0.00000156572*vel*vel) + (0.00287086*vel) - (1.28892);
    }

    public void write() {
        ExternalTools.TELEMETRY.addData("Velocity", vel);
        ExternalTools.TELEMETRY.addData("Distance", distance);
        ExternalTools.TELEMETRY.addData("TargetVel", targetVel);
        ExternalTools.TELEMETRY.addData("CurrentVel", targetVel - error);
        ExternalTools.TELEMETRY.addData("Error", error);
        ExternalTools.TELEMETRY.addData("pasdfisadhiofgsdga", velToPower(targetVel));
    }

    public double getDistance() {
        return distance;
    }

    public static double getTargetVel() {
        return targetVel;
    }

    public static void setTargetVel(double targetVel) {
        FlywheelSubsystem.targetVel = targetVel;
    }

    public double getError() {
        return error;
    }

    public record FlywheelStuff(
            DcMotorEx motor1,
            DcMotorEx motor2,
            DoubleSupplier speed,
            DoubleSupplier pos,
            Servo hood,
            Follower follower,
            Gamepad controller,
            Supplier<RobotState> state,
            Supplier<Vector> robotVel
    ) {}
}
