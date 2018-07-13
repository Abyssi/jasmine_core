package com.jasmine.jasmine_core.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JNSemaphoreRoute extends ArrayList<JNSemaphorePing> {
    private String id;
    private double congestion;
    private int vehiclesCount;

    public JNSemaphoreRoute() {
        super();
    }

    public JNSemaphoreRoute(List<JNSemaphorePing> semaphorePings) {
        super(semaphorePings);

        this.id = this.computeId();
        this.congestion = this.computeCongestion();
    }

    public String computeId() {
        return this.stream().map(semaphorePing -> semaphorePing.getCrossroadsId() + "_" + semaphorePing.getSemaphoreId()).collect(Collectors.joining("-"));
    }

    public double computeCongestion() {
        double congestion = 0;
        for (JNSemaphorePing semaphorePing : this)
            congestion += (this.getVehiclesCount() / semaphorePing.getSpeed());
        return congestion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getCongestion() {
        return congestion;
    }

    public void setCongestion(double congestion) {
        this.congestion = congestion;
    }

    public int getVehiclesCount() {
        return vehiclesCount;
    }

    public void setVehiclesCount(int vehiclesCount) {
        this.vehiclesCount = vehiclesCount;
    }

    @Override
    public boolean add(JNSemaphorePing semaphorePing) {
        for (int i = 0; i < this.size(); i++)
            if (this.get(i).getTimestamp() > semaphorePing.getTimestamp()) {
                super.add(i, semaphorePing);
                return true;
            }
        super.add(semaphorePing);
        return true;
    }

    public void merge(JNSemaphoreRoute semaphoreRoute) {
        for (JNSemaphorePing semaphorePing : semaphoreRoute)
            this.add(semaphorePing);
    }

    public JNSemaphorePing getLast() {
        return this.size() > 0 ? this.get(this.size() - 1) : null;
    }


}