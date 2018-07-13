package com.jasmine.jasmine_core.Models;

import java.util.List;

public class JNSemaphore {

    private String id;
    private String crossroadsId;

    private JNCoordinates position;
    private int greenDuration;
    private int vehiclesCount;
    private double averageSpeed;
    private List<JNLightBulb> lightBulbs;

    public JNSemaphore() {
    }

    public JNSemaphore(String crossroadsId, String id, JNCoordinates position, int greenDuration, int vehiclesCount, double averageSpeed, List<JNLightBulb> lightBulbs) {
        this.crossroadsId = crossroadsId;
        this.id = id;
        this.position = position;
        this.greenDuration = greenDuration;
        this.vehiclesCount = vehiclesCount;
        this.averageSpeed = averageSpeed;
        this.lightBulbs = lightBulbs;
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

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public JNCoordinates getPosition() {
        return this.position;
    }

    public void setPosition(JNCoordinates position) {
        this.position = position;
    }

    public int getGreenDuration() {
        return this.greenDuration;
    }

    public void setGreenDuration(int greenDuration) {
        this.greenDuration = greenDuration;
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

    public List<JNLightBulb> getLightBulbs() {
        return this.lightBulbs;
    }

    public void setLightBulbs(List<JNLightBulb> lightBulbs) {
        this.lightBulbs = lightBulbs;
    }
}
