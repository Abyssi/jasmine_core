package com.jasmine.jasmine_core.Models;

public class JNCrossroadsLeaderboard extends JNLeaderboard<JNCrossroads> {
    public JNCrossroadsLeaderboard(int maxSize, ORDER order) {
        super(maxSize, order);
    }

    @Override
    protected boolean equals(JNCrossroads a, JNCrossroads b) {
        return a.getId().equals(b.getId());
    }

    @Override
    protected int compare(JNCrossroads a, JNCrossroads b) {
        //return Double.compare(a.getMedianVehiclesCount() / a.getAverageSpeed(), b.getMedianVehiclesCount() / b.getAverageSpeed());
        return Double.compare(a.getAverageSpeed(), b.getAverageSpeed());
    }
}
