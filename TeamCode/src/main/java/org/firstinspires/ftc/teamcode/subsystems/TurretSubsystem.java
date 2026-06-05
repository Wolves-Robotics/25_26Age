package org.firstinspires.ftc.teamcode.subsystems;

import android.annotation.SuppressLint;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Vector;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utils.config.MatchDetails;
import org.firstinspires.ftc.teamcode.utils.enums.RobotState;
import org.joml.Vector2d;

import java.util.function.IntSupplier;
import java.util.function.Supplier;

@Configurable
public class TurretSubsystem {
    private TurretStuff hardware;
    private final double TURRETFROMCENTERINCH = 57 / 25.4;
    //                                           millimeters / millimeter per inch
    public static boolean tracking = false, staticPos = false;

    public void init(TurretStuff hardware) {
        this.hardware = hardware;
    }

    public void read() {

    }

    @SuppressLint("DefaultLocale")
    public void update() {
        tracking = hardware.state.get() == RobotState.SPEED_UP
                || hardware.state.get() == RobotState.FIRE;
//        tracking=true;
        if (tracking) {
            double h = hardware.follower.getHeading();
            Vector2d robot = new Vector2d(hardware.follower.getPose().getX(), hardware.follower.getPose().getY());
            Vector2d target = new Vector2d(MatchDetails.target.x, MatchDetails.target.y);
            Vector2d turret = robot.sub(Math.cos(h) * TURRETFROMCENTERINCH, Math.sin(h) * TURRETFROMCENTERINCH);

            if (robot.y < 45) {
                if (target.x > 72) {
                    target.add(2, 0);
                } else {
                    target.sub(2, 0);
                }
            }

            double theta2 = Math.atan2(target.y - turret.y, target.x - turret.x);
            double theta3 = h - theta2;

            double angle = MatchDetails.zeroToForwardAngle + theta3 + (theta2-Math.PI > h ? 2*Math.PI : 0);

            double minAngle = -(3*Math.PI)/4, maxAngle = (3*Math.PI)/4, minServoPos = 0, maxServoPos = 0.6;
            double servoPos = ((angle-minAngle)/(maxAngle-minAngle)) * (maxServoPos-minServoPos) + minServoPos;

            hardware.turret.setPosition(Math.max(Math.min(servoPos, maxServoPos), minServoPos));

        } else {
            hardware.turret.setPosition(.32);
        }
    }

    public void write() {
    }


    public void setStaticPosition(boolean staticPos) {
        TurretSubsystem.staticPos = staticPos;
    }

    public record TurretStuff(
            Servo turret,
            IntSupplier turretPos,
            Follower follower,
            Supplier<RobotState> state,
            Supplier<Vector> robotVel
    ) {}
}
