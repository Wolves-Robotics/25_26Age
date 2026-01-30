package org.firstinspires.ftc.teamcode.opmodes;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Switchback;
import org.firstinspires.ftc.teamcode.utils.ExternalTools;
import org.firstinspires.ftc.teamcode.utils.config.MatchDetails;
import org.firstinspires.ftc.teamcode.utils.enums.RobotState;
import org.joml.Vector2d;

@Configurable
@TeleOp
public class TestTele extends OpMode {
    Switchback switchback;

    public static boolean whyyyyy = false, idekman = false;

    private Servo hood;
    private CRServo park;

    @Override
    public void init() {
        switchback = Switchback.getInstance();
        switchback.init(this);

        switchback.setPose(MatchDetails.PoseAtStop);

        switchback.getDriveSub().stopFollowing();

        hood = hardwareMap.get(Servo.class, "hoodServo");
        hood.setDirection(Servo.Direction.REVERSE);
        hood.setPosition(0);

        park = hardwareMap.get(CRServo.class, "park");
    }

    @Override
    public void init_loop() {
        switchback.read();

        if (gamepad1.aWasPressed()) {
            switchback.getTurretSub().resetEncoder();
        }

        if (gamepad1.bWasPressed()) {
            switchback.getTurretSub().setZeroToForwardAngle();
        }

        ExternalTools.TELEMETRY.addData("Angle", MatchDetails.ZeroToForwardAngle);

        switchback.write();
    }

    @Override
    public void loop() {
        switchback.getDriveSub().setDriveParams(
                new Vector2d(
                        gamepad1.left_stick_x,
                        -gamepad1.left_stick_y
                ),
                gamepad1.right_stick_x
        );

        switchback.read();


//        if (gamepad1.aWasPressed()) {
//            switchback.getDriveSub().toggleFollowing();
//
//            switchback.getDriveSub().setPath(
//                    new Path(new BezierLine(
//                            switchback.getDriveSub().getCurrentPose(),
//                            switchback.getDriveSub().getCurrentPose().plus(
//                                    new Pose(10, 20)
//                            )
//                    ))
//            );
//        }


        if (gamepad1.right_trigger > 0.75 && !whyyyyy) {
            switchback.changeState(RobotState.INTAKE);
            whyyyyy = true;
        } else if (gamepad1.right_trigger < 0.75 && whyyyyy) {
            switchback.changeState(RobotState.IDLE);
            whyyyyy = false;
        }

        if (gamepad1.xWasPressed()) {
            switchback.changeState(RobotState.OUTTAKE);
        } else if (gamepad1.xWasReleased()) {
            switchback.changeState(RobotState.IDLE);
        }

        if (gamepad1.leftStickButtonWasPressed()) {
            switchback.switchSpeedUp();
        }

        if (gamepad1.rightBumperWasPressed()) {
            switchback.changeState(RobotState.FIRE);
        } else if (gamepad1.rightBumperWasReleased()) {
            switchback.changeState(RobotState.SPEED_UP);
        }
        
        if (gamepad1.aWasPressed()) {
            switchback.getDriveSub().startPark();
        } else if (gamepad1.aWasReleased()) {
            switchback.getDriveSub().stopPark();
        }

        if (gamepad1.shareWasPressed()) {
            switchback.resetPose();
        }


//        if (gamepad1.dpadUpWasPressed()) {
//            park.setPower(-1);
//        } else if (gamepad1.dpadDownWasPressed()) {
//            park.setPower(1);
//        } else if (gamepad1.dpadUpWasReleased() || gamepad1.dpadDownWasReleased()){
//            park.setPower(0);
//        }


//        if (gamepad1.dpadRightWasPressed()) {
//            switchback.getFlywheelSub().setTargetVel(switchback.getFlywheelSub().getTargetVel() + 20);
//        }
//
//        if (gamepad1.dpadLeftWasPressed()) {
//            switchback.getFlywheelSub().setTargetVel(switchback.getFlywheelSub().getTargetVel() - 20);
//        }

        switchback.update();

        switchback.write();
    }
}
