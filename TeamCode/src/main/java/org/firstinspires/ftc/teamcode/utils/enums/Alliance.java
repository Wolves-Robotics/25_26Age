package org.firstinspires.ftc.teamcode.utils.enums;

public enum Alliance {
    RED(24),
    BLUE(20);

    private int id;

    Alliance(int id) {this.id = id;}

    public int getId() {
        return id;
    }
}
