/**
 * Might or might not work.... idk requires testing i guess....
 */

package org.firstinspires.ftc.teamcode.utils.control;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.follower.Follower;

import org.firstinspires.ftc.teamcode.subsystems.FlywheelSubsystem;
import org.firstinspires.ftc.teamcode.utils.ExternalTools;
import org.firstinspires.ftc.teamcode.utils.config.MatchDetails;

@Configurable
public class ShootingWhileMoving {

    // servo's physical travel time to reach its target (seconds). *not sure if this needs rotation or can be static value
    // I guess it strongly depends on mI i guess...
    public static double T_SERVO_SETTLE = 0.05;

    // Turret pivot offset from robot center along heading axis
    private static final double TURRET_OFFSET_INCHES = 57.0 / 25.4;

    private final Follower follower;

    // latency
    private long   lastTimeNs      = 0;
    private double measuredLoopDt  = 0.01; // seconds updated every loop

    /*
        phi: raw static angle *rad
        vPerp: lat vel to goal
        vPar: radi vel to goal
        v_p: hori projectile speed
     */
    private double turretAngleDeg, leadAngleDeg, phi, vPerp, vPar, v_p = 0;

    public ShootingWhileMoving(Follower follower) {
        this.follower = follower;
    }

    /*
        Do sum physics lab shi with ts to get horizontal velocity or smth
        Different hood positions requires their own regression due to differnt ejection angles.
     */
    private double projectileSpeed(double targetVel, double robotY) {
        // insert regression for all positions of shooter pzl

        if (follower.getPose().getY() < 48) {

            return 0.0;
        } else if (48 < follower.getPose().getY() && follower.getPose().getY() < 97) {

            return 0.0;
        }   else {

            return 0.0;
        }
    }

    public double getEffectiveDistance() {
        if (v_p < 1e-3) return 0.0;

        double vTowardGoal = Math.sqrt(Math.max(0.0, v_p * v_p - vPerp * vPerp)) - vPar;

        if (vTowardGoal < 1e-3) return 0.0;

        double rawDistance = Math.hypot(
                MatchDetails.target.x - (follower.getPose().getX() + Math.cos(follower.getHeading()) * TURRET_OFFSET_INCHES),
                MatchDetails.target.y - (follower.getPose().getY() + Math.sin(follower.getHeading()) * TURRET_OFFSET_INCHES)
        );

        return rawDistance * (v_p / vTowardGoal);
    }


    public void update() {

        // latency chec
        long nowNs = System.nanoTime();
        if (lastTimeNs != 0) {
            measuredLoopDt = (nowNs - lastTimeNs) / 1.0e9;
            measuredLoopDt = Math.max(measuredLoopDt, 1e-4); // clamp
        }
        lastTimeNs = nowNs;

        // Total latency
        double t_pipeline = measuredLoopDt + T_SERVO_SETTLE;

        double robotX  = follower.getPose().getX();
        double robotY  = follower.getPose().getY();
        double heading = follower.getHeading();

        double vx = follower.getVelocity().getXComponent();  // field-frame
        double vy = follower.getVelocity().getYComponent();


        double ax = follower.getAcceleration().getXComponent();
        double ay = follower.getAcceleration().getYComponent();

        // turret angle offset with center of rotation of robo
        double turretX = robotX + Math.cos(heading) * TURRET_OFFSET_INCHES;
        double turretY = robotY + Math.sin(heading) * TURRET_OFFSET_INCHES;

        // Raw angle
        double dx = MatchDetails.target.x - turretX;
        double dy = MatchDetails.target.y - turretY;
        phi = Math.atan2(dy, dx); // field-frame, radians

        // Predicted velocity upon
        double Vx = vx + ax * t_pipeline;
        double Vy = vy + ay * t_pipeline;

        // rot matrix for thing that mabye shoots correctly this time?
        vPar  =  Vx * Math.cos(phi) + Vy * Math.sin(phi);
        vPerp = -Vx * Math.sin(phi) + Vy * Math.cos(phi);

        // HZ projectile speed
        v_p = projectileSpeed(FlywheelSubsystem.getTargetVel(), robotY);

        // shiiii extra angle moving???
        double leadAngleRad = 0;
        if (v_p > 1e-3) { //when spin = true
            double sinAlpha = vPerp / v_p;
            sinAlpha    = Math.max(-1.0, Math.min(1.0, sinAlpha)); // clamp
            leadAngleRad = Math.asin(sinAlpha);
        }
        leadAngleDeg = Math.toDegrees(leadAngleRad);

        // also added heading on there
        double tauRad  = phi - leadAngleRad - heading;
        turretAngleDeg = Math.toDegrees(tauRad);
    }

    // ── TELEMETRY ──────────────────────────────────────────────────────────
    public void write() {
        ExternalTools.TELEMETRY.addData("[SWM] Turret Angle (deg)",  turretAngleDeg);
        ExternalTools.TELEMETRY.addData("[SWM] Lead Angle   (deg)",  leadAngleDeg);
        ExternalTools.TELEMETRY.addData("[SWM] LOS Angle    (deg)",  Math.toDegrees(phi));
        ExternalTools.TELEMETRY.addData("[SWM] v_perp (in/s)",       vPerp);
        ExternalTools.TELEMETRY.addData("[SWM] v_par  (in/s)",       vPar);
        ExternalTools.TELEMETRY.addData("[SWM] v_p    (in/s)",       v_p);
        ExternalTools.TELEMETRY.addData("[SWM] Loop dt (ms)",        measuredLoopDt * 1000.0);
        ExternalTools.TELEMETRY.addData("[SWM] t_pipeline (ms)",
                (measuredLoopDt + T_SERVO_SETTLE) * 1000.0);
    }

    // ── GETTERS ────────────────────────────────────────────────────────────

    // actual implementation into turret
    public double getTurretAngleDeg() { return turretAngleDeg; }

    // testing of work
    public double getLeadAngleDeg()   { return leadAngleDeg; }

    // standard aiming angle
    public double getRawLOSDeg()      { return Math.toDegrees(phi); }
}