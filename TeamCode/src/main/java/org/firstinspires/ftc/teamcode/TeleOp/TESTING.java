package org.firstinspires.ftc.teamcode.TeleOp;


import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.control.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.teamcode.CoreSystems.TESTCLASS;



@Configurable
@TeleOp(group = "nig", name = "TestingTelAviv")
public class TESTING extends OpMode {

    TESTCLASS testing;
    private PIDFController tickPID;
    public static PIDFCoefficients tickCoeffs;

    public static double targetPos;
    @Override
    public void init(){
        testing = new TESTCLASS();
        tickCoeffs = new PIDFCoefficients(0.015,0,0,0);
        tickPID = new PIDFController();
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

    }
}
