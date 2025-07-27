package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "straferTele", group = "Linear Opmode")
public class TeleOP extends OpMode {
    private DcMotor FLDrive = null;
    private DcMotor FRDrive = null;
    private DcMotor BLDrive = null;
    private DcMotor BRDrive = null;

    private ElapsedTime jonkintime;

    private boolean brake = false;
    @Override
    public void init(){
        telemetry.addData("Squidward:","start diggin in yo butt twin");

        FLDrive = hardwareMap.get(DcMotor.class,"frontLeft");
        FRDrive = hardwareMap.get(DcMotor.class,"frontRight");
        BLDrive = hardwareMap.get(DcMotor.class,"backLeft");
        BRDrive = hardwareMap.get(DcMotor.class,"backRight");

        jonkintime = new ElapsedTime();

        FLDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        BLDrive.setDirection(DcMotorSimple.Direction.REVERSE);
    }
// Front left back left == reverse
    @Override
    public void init_loop(){

    }
    @Override
    public void start(){

    }
    @Override
    public void loop() {

        double frontleftPower;
        double frontrightPower;
        double backleftPower;
        double backrightPower;
        double denominator;


        if (gamepad1.x && jonkintime.milliseconds() >= 500) {
            jonkintime.reset();
            if(!brake){
                FLDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                FRDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                BRDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                BLDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                brake = true;
            }else{

                FLDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                FRDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                BRDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                BLDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                brake = false;
            }

        }


            double drive = -gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;
            double strafe = gamepad1.left_stick_x * 1.1;
            denominator = Math.max(Math.abs(drive) + Math.abs(strafe) + Math.abs(turn), 1);
            frontleftPower = (drive + turn + strafe) / denominator;
            frontrightPower = (drive - turn - strafe) / denominator;
            backleftPower = (drive + turn - strafe) / denominator;
            backrightPower = (drive - turn + strafe) / denominator;


            FLDrive.setPower(frontleftPower);
            FRDrive.setPower(frontrightPower);
            BRDrive.setPower(backrightPower);
            BLDrive.setPower(backleftPower);

            telemetry.addData("ZPBrake", brake);
            telemetry.update();
    }
    @Override
    public void stop(){
        telemetry.clear();
        telemetry.addData("Squidward","stop digging in yo butt twin");
    }
}
