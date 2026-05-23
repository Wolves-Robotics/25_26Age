package org.firstinspires.ftc.teamcode.subsystems;

import android.annotation.SuppressLint;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.control.PIDFController;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Vector;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

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

    public static boolean tracking = false, prevValid = false, noLimelight = false, staticPos = false;
    private double targetTicks, power, targetDeg, integral, derivative, prevSig, prevDeg, time;

    private PIDFController degreePID;
    private TurretPID llPID;

    private ElapsedTime derivativeTime;

    public static PIDFCoefficients
            degreeCoeffs = new PIDFCoefficients(0.018, 0.00, 0.0024, 0);

    public static double s = 0.024, tickChange = 17;

    public void init(TurretStuff hardware) {
        this.hardware = hardware;

        degreePID = new PIDFController(degreeCoeffs);
        llPID   = new TurretPID(degreeCoeffs);
        integral = 0;

        derivativeTime = new ElapsedTime();
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
            LLResult result = hardware.limelight.getLatestResult();

            Vector2d robot = new Vector2d(hardware.follower.getPose().getX(), hardware.follower.getPose().getY());
            Vector2d target = new Vector2d(MatchDetails.target.x, MatchDetails.target.y);
            Vector2d apriltag = MatchDetails.aprilTag;
            Vector2d turret = robot.sub(Math.cos(h) * TURRETFROMCENTERINCH, Math.sin(h) * TURRETFROMCENTERINCH);

            if (robot.y < 45) {
                if (target.x > 72) {
                    target.add(2, 0);
                } else {
                    target.sub(2, 0);
                }
            }

            double ticks = hardware.turretPos.getAsInt();

            double theta2 = Math.atan2(target.y - turret.y, target.x - turret.x);
            double theta3 = h - theta2;

            targetTicks = (MatchDetails.zeroToForwardAngle + theta3 + (theta2-Math.PI > h ? 2*Math.PI : 0)) * TICKSPERRAD;

            targetTicks = Math.max(Math.min(targetTicks, 490), 10);

            if (staticPos) targetTicks = MatchDetails.zeroToForwardAngle * TICKSPERRAD;

            prevDeg = targetDeg;
            prevSig = Math.signum(targetDeg);

            targetDeg = Math.toDegrees((targetTicks - ticks) / TICKSPERRAD);

//                llPID.updateCoeffs(degreeCoeffs);
//                llPID.updateDegreesToTarget(degreesToTarget);
//                power = llPID.update(!prevValid);

            time = derivativeTime.seconds();
            derivativeTime.reset();

            if (!prevValid) {
                integral = 0;
                time = 0;
            }

            power = Math.signum(targetDeg) * s;
            power += targetDeg * degreeCoeffs.P;

            if (Math.signum(targetDeg) != prevSig) {
                integral = 0;
            }

            if (Math.abs(targetDeg) > 0.2) {
                integral += degreeCoeffs.I;
                power += integral * Math.signum(targetDeg);
            }

            if (time > 0.0025) {
                derivative = (targetDeg - prevDeg) / time;
                if (Math.signum(derivative) == Math.signum(targetDeg))
                    derivative = 0;
                power += derivative * degreeCoeffs.D;
            }

            // tracking from robot position
//                tickPID.setCoefficients(tickCoeffs);
//                tickPID.updateError(targetTicks - ticks);
//                power = tickPID.run();
            hardware.turretMotor.setPower(power);

            prevValid = false;
        } else {
            hardware.turretMotor.setPower(0);
        }
    }

    public void write() {
        ExternalTools.TELEMETRY.addData("Ticks", hardware.turretPos.getAsInt());
        ExternalTools.TELEMETRY.addData("Target", targetTicks);
        ExternalTools.TELEMETRY.addData("Degrees", targetDeg);
        ExternalTools.TELEMETRY.addData("Power", power);
        ExternalTools.TELEMETRY.addData("integral", integral);
        ExternalTools.TELEMETRY.addData("Derivative", derivative);
        ExternalTools.TELEMETRY.addData("Limelight", prevValid);
    }

    public void setZeroToForwardAngle() {
        MatchDetails.zeroToForwardAngle = hardware.turretPos.getAsInt() / TICKSPERRAD;
    }

    public void resetEncoder() {
        hardware.turretMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hardware.turretMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void setStaticPosition(boolean staticPos) {
        TurretSubsystem.staticPos = staticPos;
    }

    public record TurretStuff(
            DcMotorEx turretMotor,
            IntSupplier turretPos,
            Follower follower,
            Limelight3A limelight,
            Supplier<RobotState> state,
            Supplier<Vector> robotVel
    ) {}
}
