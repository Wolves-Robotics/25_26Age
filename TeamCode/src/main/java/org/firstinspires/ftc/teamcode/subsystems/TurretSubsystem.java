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



}
