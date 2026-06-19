package org.firstinspires.ftc.teamcode.utils.control;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.control.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

@Configurable
public class TurretPID {
    private PIDFCoefficients coefficients;

    private MovingAverageFilter degreeAverage;

    private ElapsedTime elapsedTime;

    private double degrees, prevDeg, power,
                   integral, proportional, derivative;

    private double degSig, prevDegSig, absDeg, prevAbsDeg;

    public static double integralMin=0.08, integralMax = 12, proportionalMin = 3,
            derivativeOffset = 1.67, proportionalLerp = 0.05;
    public static int degreeSize = 25;

    public TurretPID(PIDFCoefficients coefficients) {
        this.coefficients = coefficients;

        degreeAverage = new MovingAverageFilter(degreeSize, 0);

        elapsedTime = new ElapsedTime();
    }

    public void updateCoeffs(PIDFCoefficients coefficients) {
        this.coefficients = coefficients;
    }

    public void updateDegreesToTarget(double degrees) {
        prevDeg = this.degrees;
        prevDegSig = degSig;
        prevAbsDeg = absDeg;
        this.degrees = degrees;
        degSig = Math.signum(degrees);
        absDeg = Math.abs(degrees);
    }

    private void updateProportional() {
        proportional = lerp(proportional, Math.log10(absDeg + 1) * coefficients.P, proportionalLerp);
        if (absDeg < proportionalMin) proportional = 0;
        if (prevDegSig != degSig) proportional = 0;
    }

    private void updateIntegral() {
        integral += (1-Math.pow(1-absDeg/integralMax, 3)) * coefficients.I;
        if (absDeg < integralMin || absDeg > integralMax)
            integral = 0;
        if (prevDegSig != degSig) integral = 0;
    }

    private void updateDerivative() {
        derivative = Math.max(1 + Math.pow(-derivativeOffset * Math.abs(prevDeg - degrees)/elapsedTime.seconds()/100, 3), 0) * coefficients.D;
    }

    public double update(boolean reset) {
        degreeAverage.setWindowSize(25);
        if (reset) {
            degreeAverage.fill(Math.abs(degrees));
            proportional = 0;
            integral = 0;
            derivative = 0;
        } else {
            updateDerivative();
        }

        updateProportional();

        updateIntegral();

        power = (proportional + integral) * derivative;

        power *= degSig;

        elapsedTime.reset();
        return power;
    }

    public void write() {

    }

    public double lerp(double a, double b, double c) {
        return a + (b-a)*c;
    }
}
