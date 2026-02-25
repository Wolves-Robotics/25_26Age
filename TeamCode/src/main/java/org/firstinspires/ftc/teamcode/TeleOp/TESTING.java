package org.firstinspires.ftc.teamcode.TeleOp;


import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.control.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.pedropathing.control.PIDFCoefficients;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.CoreSystems.RobotHardware;
import org.firstinspires.ftc.teamcode.CoreSystems.TESTCLASS;



@Configurable
@TeleOp(group = "nig", name = "TestingTelAviv")
public class TESTING extends OpMode {

    TESTCLASS testing;

    private PIDFController tickPID;
    public static  PIDFCoefficients tickCoeffs;
    private double power;
    public static double targetPos;
    public static double p,i,d,f;
    private RobotHardware RBHW;
    @Override
    public void init(){
        RBHW = new RobotHardware(hardwareMap);
        testing = new TESTCLASS();
        tickCoeffs = new PIDFCoefficients(p,i,d,f);
        tickPID = new PIDFController(tickCoeffs);


         /*
            tickCoeffs   = new PIDFCoefficients(0.015, 0, 0, 0),
            degreeCoeffs = new PIDFCoefficients(0.015, 0.001, 0, 0);
          */

         /*
                * tickPID.setCoefficients(tickCoeffs);
                tickPID.updateError(targetTicks - ticks);
                power = tickPID.run();
                hardware.turretMotor.setPower(power);
*/
    }
    @Override
    public void loop(){

        tickPID.setCoefficients(tickCoeffs);
        tickPID.updateError(targetPos-testing.turret.getCurrentPosition());
        power = tickPID.run();
        testing.turret.setPower(power);

        double error = targetPos-testing.turret.getCurrentPosition();

        telemetry.addData("Error: ",error);
        telemetry.update();
    }
}
