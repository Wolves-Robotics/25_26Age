package org.firstinspires.ftc.teamcode.opmodes;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Switchback;
import org.firstinspires.ftc.teamcode.utils.Alliance;
import org.firstinspires.ftc.teamcode.utils.MatchDetails;
import org.joml.Vector2d;


@TeleOp
public class TestTele extends OpMode {
    Switchback switchback;

    @Override
    public void init() {
        MatchDetails.ALLIANCECOLOR = Alliance.RED;

        switchback = new Switchback();
        switchback.init(this);
    }

    @Override
    public void init_loop() {
        switchback.write();
    }

    @Override
    public void loop() {
        switchback.read();

        switchback.getDriveSub().setDriveParams(
                new Vector2d(
                        gamepad1.left_stick_x,
                        -gamepad1.left_stick_y
                ),
                gamepad1.right_stick_x
        );

        if (gamepad1.aWasPressed()) {
            switchback.getDriveSub().toggleFollowing();

            switchback.getDriveSub().setPath(
                    new Path(new BezierLine(
                            switchback.getDriveSub().getCurrentPose(),
                            switchback.getDriveSub().getCurrentPose().plus(
                                    new Pose(10, 20)
                            )
                    ))
            );
        }


        switchback.update();

        switchback.write();
    }
}
