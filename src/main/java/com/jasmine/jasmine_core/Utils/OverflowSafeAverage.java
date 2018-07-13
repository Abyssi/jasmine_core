package com.jasmine.jasmine_core.Utils;

public class OverflowSafeAverage {
    private double average;
    private long count;

    public void add(double value) {
        this.average = (this.average * this.count + value) / ++count;
    }

    public void merge(OverflowSafeAverage overflowSafeAverage) {
        this.average = (this.average * this.count + overflowSafeAverage.average) / (this.count += overflowSafeAverage.count);
    }

    public double getAverage() {
        return this.average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
