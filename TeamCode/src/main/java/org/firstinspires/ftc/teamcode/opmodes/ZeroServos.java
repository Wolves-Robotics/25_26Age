package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class ZeroServos extends OpMode{
    private  Servo Turret;
    private  Servo Hood;
    private  Servo Latch;
    private Servo intakelift;


    @Override
    public void init(){
        //CLASS
        Turret = hardwareMap.get(Servo.class, "turret");
        Hood = hardwareMap.get(Servo.class, "hoodServo");
        Latch = hardwareMap.get(Servo.class, "latch");
        intakelift = hardwareMap.get(Servo.class, "intakeLift");

        //DIRECTIONS
        Hood.setDirection(Servo.Direction.REVERSE);
        intakelift.setDirection(Servo.Direction.REVERSE);

        //SERVO ZEROS
        Turret.setPosition(0);
        Latch.setPosition(0);
        Hood.setPosition(0);
        intakelift.setPosition(0);

    }
    @Override
    public void init_loop(){

    }
    @Override
    public void loop(){}

}
