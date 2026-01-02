package org.firstinspires.ftc.teamcode.subsystems.subsystemHardware;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.utils.Drawing;

public record DriveStuff(
    DcMotorEx frontLeft,
    DcMotorEx frontRight,
    DcMotorEx backLeft,
    DcMotorEx backRight,
    Follower follower,
    Drawing drawing
) {}
