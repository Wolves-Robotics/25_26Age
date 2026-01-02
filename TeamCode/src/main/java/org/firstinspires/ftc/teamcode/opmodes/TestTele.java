package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Switchback;


@TeleOp
public class TestTele extends OpMode {
    Switchback switchback;

    @Override
    public void init() {
        switchback = new Switchback();
        switchback.init(this);

//        servo = hardwareMap.servo.get("prk");
//        servo.setPosition(0.1);
    }

    @Override
    public void init_loop() {
//        if (gamepad1.aWasPressed()) {
//            servo.setPosition(0.5);
//        } else if (gamepad1.aWasReleased()) {
//            servo.setPosition(0.1);
//        }
    }

    @Override
    public void loop() {
        switchback.read();

//        drawing.drawRobot(
//                new Pose2D(
//                        DistanceUnit.INCH,
//                        gamepad1.left_stick_x*72,
//                        -gamepad1.left_stick_y*72,
//                        AngleUnit.RADIANS,
//                        Math.atan2(
//                                -gamepad1.right_stick_y,
//                                gamepad1.right_stick_x)
//                ),
//                new Twist2d(
//                        (gamepad1.left_trigger-0.5)*60,
//                        (gamepad1.right_trigger-0.5)*60,
//                        0)
//        );

        switchback.update();

        switchback.write();
    }
}
