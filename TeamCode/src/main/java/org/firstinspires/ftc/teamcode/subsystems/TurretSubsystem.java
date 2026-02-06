package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.control.PIDFController;
import com.pedropathing.follower.Follower;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.utils.ExternalTools;
import org.firstinspires.ftc.teamcode.utils.config.MatchDetails;
import org.firstinspires.ftc.teamcode.utils.control.TurretPID;
import org.firstinspires.ftc.teamcode.utils.enums.RobotState;
import org.joml.Vector2d;

import java.util.function.IntSupplier;
import java.util.function.Supplier;

@Configurable
public class TurretSubsystem {
    private TurretStuff hardware;
    //                                 Motor TPR * Gear Ratio /  Rad per Rev
    private final double TICKSPERRAD = ((13.7*28) * 2.40426)  / (2*Math.PI);
    //                                 millimeters / millimeter per inch
    private final double TURRETFROMCENTERINCH = 57 / 25.4;
    //                                           millimeters / millimeter per inch
    private final double LIMELIGHTFROMTURRETCENTER = 144.514 / 25.4;

    public static boolean tracking = false, prevValid = false;
    private double targetTicks, power, targetDeg;

    private PIDFController tickPID, degreePID;
    private TurretPID llPID;

    public static PIDFCoefficients
            tickCoeffs   = new PIDFCoefficients(0.015, 0, 0, 0),
            degreeCoeffs = new PIDFCoefficients(0.020, 0, 0.0000002, 0);

    public void init(TurretStuff hardware) {
        this.hardware = hardware;

        tickPID = new PIDFController(tickCoeffs);
        degreePID = new PIDFController(degreeCoeffs);
        llPID   = new TurretPID(degreeCoeffs);
    }

    public void read() {

    }

    public void update() {
        tracking = hardware.state.get() == RobotState.SPEED_UP
                || hardware.state.get() == RobotState.FIRE;
        if (tracking) {
            double h = hardware.follower.getHeading();
            LLResult result = hardware.limelight.getLatestResult();

            Vector2d robot = new Vector2d(hardware.follower.getPose().getX(), hardware.follower.getPose().getY());
            Vector2d target = MatchDetails.target;
            Vector2d apriltag = MatchDetails.aprilTag;
            Vector2d turret = robot.sub(Math.cos(h) * TURRETFROMCENTERINCH, Math.sin(h) * TURRETFROMCENTERINCH);

            double ticks = hardware.turretPos.getAsInt();

            if (!result.isValid()) {
                if (prevValid)
                    tickPID.reset();

                // tracking from robot position
                double theta2 = Math.atan2(target.y - turret.y, target.x - turret.x);
                double theta3 = h - theta2;

                targetTicks = (MatchDetails.zeroToForwardAngle + theta3 + (theta2-Math.PI > h ? 2*Math.PI : 0)) * TICKSPERRAD;

                targetTicks = Math.max(Math.min(targetTicks, 490), 10);

                tickPID.setCoefficients(tickCoeffs);
                tickPID.updateError(targetTicks - ticks);
                power = tickPID.run();
                hardware.turretMotor.setPower(power);

                prevValid = false;
            } else {
                if (!prevValid)
                    degreePID.reset();
                // tracking from limelight
                double tx = result.getTx();

                double theta1 = Math.atan2(target.y - turret.y, target.x - turret.x);
                double theta2 = Math.atan2(apriltag.y - turret.y, apriltag.x - turret.x);
                double theta3 = Math.toDegrees(theta2 - theta1);

                double theta4 = Math.toRadians(180 - tx);
                double l1 = Math.hypot(apriltag.y - turret.y, apriltag.x - turret.x);
                double v = Math.max(Math.min(LIMELIGHTFROMTURRETCENTER * Math.sin(theta4) / l1, 1), -1);
                double theta5 = Math.asin(v);
                double theta6 = 180 - Math.toDegrees(theta4 + theta5);

                targetDeg = theta3 + theta6;

//                llPID.updateCoeffs(degreeCoeffs);
//                llPID.updateDegreesToTarget(degreesToTarget);
//                power = llPID.update(!prevValid);

                degreePID.setCoefficients(degreeCoeffs);
                degreePID.updateError(targetDeg);
                power = degreePID.run();

                if (ticks > 490) {
                    power = Math.min(power, 0);
                } else if (ticks < 10) {
                    power = Math.max(power, 0);
                }
                hardware.turretMotor.setPower(power);

                prevValid = true;
            }
        } else {
            hardware.turretMotor.setPower(0);
        }
    }

    public void write() {
        ExternalTools.TELEMETRY.addData("Ticks", hardware.turretPos.getAsInt());
        ExternalTools.TELEMETRY.addData("Target", targetTicks);
        ExternalTools.TELEMETRY.addData("Degrees", targetDeg);
        ExternalTools.TELEMETRY.addData("Power", power);
        ExternalTools.TELEMETRY.addData("Limelight", prevValid);
    }

    public void setZeroToForwardAngle() {
        MatchDetails.zeroToForwardAngle = hardware.turretPos.getAsInt() / TICKSPERRAD;
    }

    public void resetEncoder() {
        hardware.turretMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hardware.turretMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public record TurretStuff(
            DcMotorEx turretMotor,
            IntSupplier turretPos,
            Follower follower,
            Limelight3A limelight,
            Supplier<RobotState> state
    ) {}
}
