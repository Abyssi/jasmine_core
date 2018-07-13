package com.jasmine.jasmine_core.Models;

import com.jasmine.jasmine_core.Utils.JSONSerializable;

public class JNCrossroads extends JSONSerializable {

    private String id;
    private double averageSpeed;
    private double medianVehiclesCount;
    private long timestamp;

    public JNCrossroads() {
        this("");
    }

    public JNCrossroads(String id) {
        this(id, 0d, 0d);
    }

    public JNCrossroads(String id, double averageSpeed, double medianVehiclesCount) {
        this.id = id;
        this.averageSpeed = averageSpeed;
        this.medianVehiclesCount = medianVehiclesCount;
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

    public double getAverageSpeed() {
        return this.averageSpeed;
    }

    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public double getMedianVehiclesCount() {
        return this.medianVehiclesCount;
    }

    public void setMedianVehiclesCount(double medianVehiclesCount) {
        this.medianVehiclesCount = medianVehiclesCount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean equals(JNCrossroads obj) {
        return this.getId().equals(obj.getId()) &&
                this.getAverageSpeed() == obj.getAverageSpeed() &&
                this.getMedianVehiclesCount() == obj.getMedianVehiclesCount() &&
                this.getTimestamp() == obj.getTimestamp();
    }
}
