package org.firstinspires.ftc.teamcode;

public enum Motors {
    FRONTLEFTMOTOR("frontleftmotor"),
    FRONTRIGHTMOTOR("frontrightmotor"),
    BACKLEFTMOTOR("backleftmotor"),
    BACKRIGHTMOTOR("backrightmotor"),
    SHOOTERTOP("shooter1"),
    SHOOTERBOTTOM("shooter2"),
    TURRET("turretturner"),
    INTAKE("intake");
    final String motor;

    Motors (String motor){this.motor = motor;}
}
