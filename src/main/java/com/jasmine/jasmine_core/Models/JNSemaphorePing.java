package com.jasmine.jasmine_core.Models;

public class JNSemaphorePing {
    private String crossroadsId;
    private String semaphoreId;
    private double speed;
    private long timestamp;

    public JNSemaphorePing() {
    }

    public JNSemaphorePing(String crossroadsId, String semaphoreId, double speed, long timestamp) {
        this.crossroadsId = crossroadsId;
        this.semaphoreId = semaphoreId;
        this.speed = speed;
        this.timestamp = timestamp;
    }

    public String getCrossroadsId() {
        return crossroadsId;
    }

    public void setCrossroadsId(String crossroadsId) {
        this.crossroadsId = crossroadsId;
    }

    public String getSemaphoreId() {
        return semaphoreId;
    }

    public void setSemaphoreId(String semaphoreId) {
        this.semaphoreId = semaphoreId;
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
