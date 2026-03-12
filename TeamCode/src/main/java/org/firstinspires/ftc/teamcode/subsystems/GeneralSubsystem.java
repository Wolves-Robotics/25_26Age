package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utils.enums.RobotState;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class GeneralSubsystem {
    private GeneralStuff hardware;

    private RobotState robotState = RobotState.IDLE;

    public void init(GeneralStuff hardware) {
        this.hardware = hardware;
    }

    public void read() {

    }

    public void update() {
        if (robotState == RobotState.FIRE) {
            if (!hardware.farShot().getAsBoolean()){
                hardware.intake.setPower(1);
            } else if (Math.abs(hardware.velocityError.getAsDouble()) < 400) {
                hardware.intake.setPower(0.8);
            } else {
                hardware.intake.setPower(0);
            }
        }
    }

    public void write() {

    }

    public void changeState(RobotState robotState) {
        switch (robotState) {
            case IDLE -> {
                hardware.intake.setPower(0);
                hardware.latch.setPosition(0);
            }
            case INTAKE -> {
                if (this.robotState == RobotState.SPEED_UP || this.robotState == RobotState.FIRE) {
                    robotState = this.robotState;
                } else {
                    hardware.intake.setPower(1);
                    hardware.latch.setPosition(0);
                }
            }
            case OUTTAKE -> {
                hardware.intake.setPower(-1);
                hardware.latch.setPosition(0);
            }
            case SPEED_UP -> {
                hardware.intake.setPower(0);
                hardware.latch.setPosition(0.9);
            }
            case FIRE -> {
                if (this.robotState != RobotState.SPEED_UP) {
                    robotState = this.robotState;
                }
            }
        }
        this.robotState = robotState;
    }

    public RobotState getRobotState() {
        return robotState;
    }

    public record GeneralStuff(
            DcMotorEx intake,
            Servo latch,
            BooleanSupplier cameraTracking,
            BooleanSupplier farShot,
            DoubleSupplier tickError,
            DoubleSupplier degreeError,
            DoubleSupplier velocityError,
            DoubleSupplier distance
    ) {}
}
