package com.jasmine.jasmine_core.Models;

import com.jasmine.jasmine_core.Utils.JSONSerializable;

public class JNMobilePing extends JSONSerializable {
    private JNCoordinates coordinates;
    private double speed;
    private long timestamp;

    public JNMobilePing() {
    }

    public JNMobilePing(JNCoordinates coordinates, double speed, long timestamp) {
        this.coordinates = coordinates;
        this.speed = speed;
        this.timestamp = timestamp;
    }

    public JNCoordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(JNCoordinates coordinates) {
        this.coordinates = coordinates;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
