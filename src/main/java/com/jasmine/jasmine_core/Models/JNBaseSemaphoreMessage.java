package com.jasmine.jasmine_core.Models;

import com.jasmine.jasmine_core.Utils.JSONSerializable;

public class JNBaseSemaphoreMessage extends JSONSerializable {
    private String crossroadsId;
    private String semaphoreId;
    private JNCoordinates position;
    private long timestamp;
    private int vehiclesCount;
    private double averageSpeed;

    public JNBaseSemaphoreMessage() {
    }

    public JNBaseSemaphoreMessage(String crossroadsId, String semaphoreId, JNCoordinates position, long timestamp, int vehiclesCount, double averageSpeed) {
        this.crossroadsId = crossroadsId;
        this.semaphoreId = semaphoreId;
        this.position = position;
        this.timestamp = timestamp;
        this.vehiclesCount = vehiclesCount;
        this.averageSpeed = averageSpeed;
    }

    /*
        Getter and Setter
     */

    public String getCrossroadsId() {
        return this.crossroadsId;
    }

    public void setCrossroadsId(String crossroadsId) {
        this.crossroadsId = crossroadsId;
    }

    public String getSemaphoreId() {
        return this.semaphoreId;
    }

    public void setSemaphoreId(String semaphoreId) {
        this.semaphoreId = semaphoreId;
    }

    public JNCoordinates getPosition() {
        return this.position;
    }

    public void setPosition(JNCoordinates position) {
        this.position = position;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getVehiclesCount() {
        return this.vehiclesCount;
    }

    public void setVehiclesCount(int vehiclesCount) {
        this.vehiclesCount = vehiclesCount;
    }

    public double getAverageSpeed() {
        return this.averageSpeed;
    }

    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

}
