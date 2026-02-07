package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.follower.Follower;
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
    private double targetVel, angle, vel, distance, p0, p1, error;

    private PIDFController flywheelPIDF;

    public static boolean tuning = false;
    public static double p = 0.0014, i = 0.1, d = 0, f = 0, v = 0.000385, s = 0.095;

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
                hardware.hood.setPosition(1);
            } else {
                angle = 69;
                hardware.hood.setPosition(0);
            }

            double w = Math.toRadians(angle);

            double a = -9.81/2;
            double c = p0 - p1;

            vel = Math.sqrt(-(a* distance * distance)/(Math.cos(w) * Math.cos(w) * (c + distance *Math.tan(w))));
            if (-(2*c)/Math.tan(w) > distance)
                vel = 0;

            if (!tuning) {
                if (robot.y < 45) {
                    targetVel = 73.17952*vel-893.76537;
                } else {
                    targetVel = 0.0877833*Math.pow(vel, 3) - 7.9942*Math.pow(vel, 2) + 275.82606*vel - 2020.93191;
                }
            }

            error = targetVel - hardware.speed.getAsDouble();

            flywheelPIDF.setPIDF(p, i, d, f);
            flywheelPIDF.setFeedforward(v, 0, s);
            if (Math.abs(error) < 40) {
                hardware.controller.rumble(200);
            }
            double power = flywheelPIDF.calculate(error, targetVel, 0);
            if (targetVel == 0) power = 0;
            if (-(2*c)/Math.tan(w) > distance) power = 0;
            hardware.motor1.setPower(power);
            hardware.motor2.setPower(power);
        } else {
            hardware.motor1.setPower(0);
            hardware.motor2.setPower(0);
        }
    }

    private double velToPower(double vel) {
        return (1.37002E-7*vel*vel) - (0.0000599941*vel) + (0.385831);
    }

    private double velToMotor(double vel) {
        return (41.905*vel) + 125.98797 + (distance > 115 ? 120:0);
    }

    public void write(){
        ExternalTools.TELEMETRY.addData("Velocity", vel);
        ExternalTools.TELEMETRY.addData("Distance", distance);
        ExternalTools.TELEMETRY.addData("CurrentVel", hardware.speed.getAsDouble());
        ExternalTools.TELEMETRY.addData("TargetVel", targetVel);
    }

    public double getDistance() {
        return distance;
    }

    public double getTargetVel() {
        return targetVel;
    }

    public void setTargetVel(double targetVel) {
        this.targetVel = targetVel;
    }

    public double getError() {
        return error;
    }

    public record FlywheelStuff(
            DcMotorEx motor1,
            DcMotorEx motor2,
            DoubleSupplier speed,
            Servo hood,
            Follower follower,
            Gamepad controller,
            Supplier<RobotState> state
    ) {}
}
