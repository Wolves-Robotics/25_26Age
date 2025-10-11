package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.enums.HardwareEnum;
import org.firstinspires.ftc.teamcode.hardware.Robot;

@Autonomous(preselectTeleOp = "TestTele")
public class TestAuto extends BaseAuto {
    private int index;
    private boolean initial;

    private ElapsedTime elapsedTime;

    @Override
    protected void initStuff() {
        robot.shootingSubsystem().setTarget(163);

        index = 0;
        initial = false;

        elapsedTime = new ElapsedTime();
    }

    @Override
    protected void mainLoop() {
        switch (index) {
            // move backward
            case 0:
                if (!initial) {
                    robot.getRobotHardware().setMotorPower(HardwareEnum.forwardLeft,  -1);
                    robot.getRobotHardware().setMotorPower(HardwareEnum.forwardRight, -1);
                    robot.getRobotHardware().setMotorPower(HardwareEnum.backRight,    -1);
                    robot.getRobotHardware().setMotorPower(HardwareEnum.backLeft,     -1);

                    elapsedTime.reset();
                    initial = true;
                }

                if (elapsedTime.milliseconds() > 250) {
                    robot.getRobotHardware().setMotorPower(HardwareEnum.forwardLeft,  0);
                    robot.getRobotHardware().setMotorPower(HardwareEnum.forwardRight, 0);
                    robot.getRobotHardware().setMotorPower(HardwareEnum.backRight,    0);
                    robot.getRobotHardware().setMotorPower(HardwareEnum.backLeft,     0);

                    index++;
                    initial = false;
                }
                break;

            // get flywheel spinning
            case 1:
                if (!initial) {
                    robot.getRobotHardware().setMotorPower(HardwareEnum.flywheelMotor, 0.72);

                    elapsedTime.reset();
                    initial = true;
                }

                if (elapsedTime.milliseconds() > 1500) {
                    index++;
                    initial = false;
                }
                break;

            // shoot ball 1
            case 2:
                if (!initial) {
                    robot.getRobotHardware().setMotorPower(HardwareEnum.intakeMotor, 1);

                    elapsedTime.reset();
                    initial = true;
                }

                if (elapsedTime.milliseconds() > 750) {
                    robot.getRobotHardware().setMotorPower(HardwareEnum.intakeMotor, 0);

                    index++;
                    initial = false;
                }
                break;

            // reset intake
            case 3:
                if (!initial) {
                    robot.getRobotHardware().setMotorPower(HardwareEnum.intakeMotor, -1);

                    elapsedTime.reset();
                    initial = true;
                }

                if (elapsedTime.milliseconds() > 700) {
                    robot.getRobotHardware().setMotorPower(HardwareEnum.intakeMotor, 0);

                    index++;
                    initial = false;
                }
                break;

            // shoot ball 2
            case 4:
                if (!initial) {
                    robot.getRobotHardware().setMotorPower(HardwareEnum.intakeMotor, 1);

                    elapsedTime.reset();
                    initial = true;
                }

                if (elapsedTime.milliseconds() > 1000) {
                    robot.getRobotHardware().setMotorPower(HardwareEnum.intakeMotor, 0);

                    index++;
                    initial = false;
                }
                break;

            // reset intake
            case 5:
                if (!initial) {
                    robot.getRobotHardware().setMotorPower(HardwareEnum.intakeMotor, -1);

                    elapsedTime.reset();
                    initial = true;
                }

                if (elapsedTime.milliseconds() > 600) {
                    robot.getRobotHardware().setMotorPower(HardwareEnum.intakeMotor, 0);

                    index++;
                    initial = false;
                }
                break;

            // shoot ball 3
            case 6:
                if (!initial) {
                    robot.getRobotHardware().setMotorPower(HardwareEnum.intakeMotor, 1);

                    elapsedTime.reset();
                    initial = true;
                }

                if (elapsedTime.milliseconds() > 1250) {
                    robot.getRobotHardware().setMotorPower(HardwareEnum.flywheelMotor, 0);
                    robot.getRobotHardware().setMotorPower(HardwareEnum.intakeMotor, 0);

                    index++;
                    initial = false;
                }
                break;
        }
    }
}
