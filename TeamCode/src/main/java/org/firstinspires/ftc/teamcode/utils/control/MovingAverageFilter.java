package org.firstinspires.ftc.teamcode.utils.control;

import java.util.ArrayDeque;
import java.util.Queue;

public class MovingAverageFilter {
    private int windowSize;
    private Queue<Double> window = new ArrayDeque<>();
    private double sum = 0.0;

    public MovingAverageFilter(int windowSize, double initialValue) {
        this.windowSize = windowSize;
        fill(initialValue);
    }

    public void fill(double initialValue) {
        sum = initialValue * windowSize;
        window = new ArrayDeque<>();
        for (int i = 0; i < windowSize; i++)
            window.add(initialValue);
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

    public void setWindowSize(int windowSize) {
        this.windowSize = windowSize;
        while (window.size() > windowSize) {
            sum -= window.remove();
        }
    }
}