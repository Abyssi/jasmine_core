package com.jasmine.jasmine_core.Models;

import com.jasmine.jasmine_core.Utils.JSONSerializable;

public class JNCoordinates extends JSONSerializable {

    public double latitude;
    public double longitude;

    public JNCoordinates() {
    }

    public JNCoordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /*
        Getter and Setter
     */

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double distance(JNCoordinates coordinates) {
        return Math.sqrt(Math.abs(Math.pow(this.getLatitude() - coordinates.getLatitude(), 2) + Math.pow(this.getLongitude() - coordinates.getLongitude(), 2)));
    }
}
