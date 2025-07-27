package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
@TeleOp(name ="MotorTest",group = "Linear Opmode")
public class motorTEST extends OpMode{
    private DcMotor FLDrive = null;
    private DcMotor FRDrive = null;
    private DcMotor BLDrive = null;
    private DcMotor BRDrive = null;
    @Override
    public void init(){


        FLDrive = hardwareMap.get(DcMotor.class,"frontLeft");
        FRDrive = hardwareMap.get(DcMotor.class,"frontRight");
        BLDrive = hardwareMap.get(DcMotor.class,"backLeft");
        BRDrive = hardwareMap.get(DcMotor.class,"backRight");
    }
    public void loop(){
        //lowk not smart
         if (gamepad1.x) {
            FLDrive.setPower(0.5);
        }
        else if (gamepad1.y) {
            FRDrive.setPower(0.5);
        }
        else if (gamepad1.a) {
            BRDrive.setPower(0.5);
        }
        else if (gamepad1.b) {
            BLDrive.setPower(0.5);
        }else{
             FLDrive.setPower(0);
             FRDrive.setPower(0);
             BRDrive.setPower(0);
             BLDrive.setPower(0);
         }
        /*
        X - Front left
        Y - Front Right
        A - Back Right
        B - Back left
         */
    }
}
