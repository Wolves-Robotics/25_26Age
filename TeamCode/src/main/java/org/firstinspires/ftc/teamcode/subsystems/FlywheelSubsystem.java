package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.control.PIDFController;
import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utils.ExternalTools;
import org.firstinspires.ftc.teamcode.utils.config.MatchDetails;
import org.firstinspires.ftc.teamcode.utils.enums.RobotState;
import org.joml.Vector2d;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

@Configurable
public class FlywheelSubsystem {
    private final double TURRETFROMCENTERINCH = 57 / 25.4;


    private FlywheelStuff hardware;

    private boolean isRunning;
    private double targetVel, angle, vel, d, p0, p1;

    private PIDFController flywheelPIDF;

    public static boolean tuning = false;
    public static PIDFCoefficients flywheelCoefs = new PIDFCoefficients(0.0017, 0, 0, 1);

    public void init(FlywheelStuff hardware) {
        this.hardware = hardware;

        flywheelPIDF = new PIDFController(flywheelCoefs);

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
            d = Math.hypot(
                    MatchDetails.target.x - turret.x,
                    MatchDetails.target.y - turret.y
            );

            if (d < 70) {
                angle = 69;
                hardware.hood.setPosition(0);
            } else if (d < 115) {
                angle = 61;
                hardware.hood.setPosition(0.5);
            } else {
                angle = 53;
                hardware.hood.setPosition(1);
            }
            double w = Math.toRadians(angle);

            double a = -9.81/2;
            double c = p0 - p1;

            vel = Math.sqrt(-(a*d*d)/(Math.cos(w) * Math.cos(w) * (c + d*Math.tan(w))));
            if (-(2*c)/Math.tan(w) > d)
                vel = 0;

            if (!tuning)
                targetVel = velToMotor(vel);

            flywheelPIDF.setCoefficients(flywheelCoefs);
            flywheelPIDF.updateError(targetVel - hardware.speed.getAsDouble());
            if (Math.abs(flywheelPIDF.getError()) < 100) {
                hardware.controller.rumble(200);
            }
            double power = flywheelPIDF.run() + velToPower(targetVel);
            if (targetVel == 0) power = 0;
            if (-(2*c)/Math.tan(w) > d) power = 0;
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
        return (41.905*vel) + 125.98797 + (d > 115 ? 120:0);
    }

    public void write(){
        ExternalTools.TELEMETRY.addData("Velocity", vel);
        ExternalTools.TELEMETRY.addData("Distance", d);
        ExternalTools.TELEMETRY.addData("TargetVel", targetVel);
    }

    public double getD() {
        return d;
    }

    public double getTargetVel() {
        return targetVel;
    }

    public void setTargetVel(double targetVel) {
        this.targetVel = targetVel;
    }

    public double getError() {
        return flywheelPIDF.getError();
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
