package com.jasmine.jasmine_core.Models;

import java.util.ArrayList;

public class JNMobileRoute extends ArrayList<JNMobilePing> {
    private String id;
    private double averageSpeed;

    public JNMobileRoute() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    @Override
    public boolean add(JNMobilePing mobilePing) {
        for (int i = 0; i < this.size(); i++)
            if (this.get(i).getTimestamp() > mobilePing.getTimestamp()) {
                super.add(i, mobilePing);
                return true;
            }
        super.add(mobilePing);
        return true;
    }

    public void merge(JNMobileRoute mobileRoute) {
        for (JNMobilePing mobilePing : mobileRoute)
            this.add(mobilePing);
    }
}
