package org.firstinspires.ftc.teamcode.utils;

import java.util.ArrayDeque;
import java.util.Queue;

public class MovingAverageFilter {
    private final int windowSize;
    private final Queue<Double> window = new ArrayDeque<>();
    private double sum = 0.0;

    public MovingAverageFilter(int windowSize) {
        this.windowSize = windowSize;
    }

    public double update(double input) {
        window.add(input);
        sum += input;

        if (window.size() > windowSize) {
            sum -= window.remove();
        }

        return sum / window.size();
    }

    public double get() {
        return sum / window.size();
    }

    public void reset() {

    }
}