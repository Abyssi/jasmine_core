package com.jasmine.jasmine_core.Models;

public class JNMobile {

    private String id;
    private JNCoordinates position;
    private double speed;
    private JNSemaphore nearestSemaphore;

    public JNMobile() {
    }

    public JNMobile(String id, JNCoordinates coordinates, double speed, JNSemaphore nearestSemaphore) {
        this.id = id;
        this.position = coordinates;
        this.speed = speed;
        this.nearestSemaphore = nearestSemaphore;
    }

    /*
        Getter and Setter
     */

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

    public double getSpeed() {
        return this.speed;
    }

    public void setSpeed(double greenDuration) {
        this.speed = speed;
    }

    public JNSemaphore getNearestSemaphore() {
        return this.nearestSemaphore;
    }

    public void setNearestSemaphore(JNSemaphore nearestSemaphore) {
        this.nearestSemaphore = nearestSemaphore;
    }
}
