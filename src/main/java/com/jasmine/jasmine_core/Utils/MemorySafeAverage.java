package com.jasmine.jasmine_core.Utils;

public class MemorySafeAverage {
    private double sum;
    private long count;

    public MemorySafeAverage() {
    }

    public MemorySafeAverage(double initial) {
        this.sum = initial;
        this.count = 1;
    }

    public void add(double value) {
        this.sum += value;
        this.count++;
    }

    public void merge(MemorySafeAverage memorySafeAverage) {
        this.sum += memorySafeAverage.sum;
        this.count += memorySafeAverage.count;
    }

    public double getSum() {
        return sum;
    }

    public double getAverage() {
        return sum / count;
    }

    public long getCount() {
        return count;
    }
}
