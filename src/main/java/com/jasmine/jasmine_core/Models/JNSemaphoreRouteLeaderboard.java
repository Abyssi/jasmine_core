package com.jasmine.jasmine_core.Models;

public class JNSemaphoreRouteLeaderboard extends JNLeaderboard<JNSemaphoreRoute> {

    public JNSemaphoreRouteLeaderboard(int maxSize, ORDER order) {
        super(maxSize, order);
    }

    @Override
    protected boolean equals(JNSemaphoreRoute a, JNSemaphoreRoute b) {
        if (a.getId() == null)
            a.setId(a.computeId());
        if (b.getId() == null)
            b.setId(b.computeId());

        return a.getId().equals(b.getId());
    }

    @Override
    protected int compare(JNSemaphoreRoute a, JNSemaphoreRoute b) {
        if (a.getCongestion() == 0)
            a.setCongestion(a.computeCongestion());
        if (b.getCongestion() == 0)
            b.setCongestion(b.computeCongestion());

        return Double.compare(a.getCongestion(), b.getCongestion());
    }
}
