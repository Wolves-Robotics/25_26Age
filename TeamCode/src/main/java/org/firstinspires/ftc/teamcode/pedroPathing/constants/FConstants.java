package org.firstinspires.ftc.teamcode.pedroPathing.constants;

import com.pedropathing.localization.Localizers;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.util.CustomFilteredPIDFCoefficients;
import com.pedropathing.util.CustomPIDFCoefficients;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class FConstants {
    static {
        FollowerConstants.localizers = Localizers.PINPOINT;

        FollowerConstants.leftFrontMotorName = "frontLeft";
        FollowerConstants.leftRearMotorName = "backLeft";
        FollowerConstants.rightFrontMotorName = "frontRight";
        FollowerConstants.rightRearMotorName = "backRight";

        FollowerConstants.leftFrontMotorDirection = DcMotorSimple.Direction.REVERSE;
        FollowerConstants.leftRearMotorDirection = DcMotorSimple.Direction.REVERSE;
        FollowerConstants.rightFrontMotorDirection = DcMotorSimple.Direction.FORWARD;
        FollowerConstants.rightRearMotorDirection = DcMotorSimple.Direction.FORWARD;

        FollowerConstants.mass = 5.22;

        FollowerConstants.xMovement = 103.20861456143605;
        FollowerConstants.yMovement = 89.69585062420276;

        FollowerConstants.forwardZeroPowerAcceleration = -31.876025293633337;
        FollowerConstants.lateralZeroPowerAcceleration = -48.6702217546242;

        FollowerConstants.translationalPIDFCoefficients.setCoefficients(0.15,0,0.008,0);
        FollowerConstants.useSecondaryTranslationalPID = true;
        FollowerConstants.secondaryTranslationalPIDFCoefficients.setCoefficients(0.055,0,0.03,0); // Not being used, @see useSecondaryTranslationalPID

        FollowerConstants.headingPIDFCoefficients.setCoefficients(1.7,0,0.07,0);
        FollowerConstants.useSecondaryHeadingPID = true;
        FollowerConstants.secondaryHeadingPIDFCoefficients.setCoefficients(0.7,0,0.1,0); // Not being used, @see useSecondaryHeadingPID

        FollowerConstants.drivePIDFCoefficients.setCoefficients(0.01,0,0,0.8,0);
        FollowerConstants.useSecondaryDrivePID = false;
        FollowerConstants.secondaryDrivePIDFCoefficients.setCoefficients(0.1,0,0,0.6,0); // Not being used, @see useSecondaryDrivePID

        FollowerConstants.zeroPowerAccelerationMultiplier = 4;
        FollowerConstants.centripetalScaling = 0.0002;

        FollowerConstants.pathEndTimeoutConstraint = 500;
        FollowerConstants.pathEndTValueConstraint = 0.995;
        FollowerConstants.pathEndVelocityConstraint = 0.1;
        FollowerConstants.pathEndTranslationalConstraint = 0.1;
        FollowerConstants.pathEndHeadingConstraint = 0.007;
    }
}
