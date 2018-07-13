package com.jasmine.jasmine_core.StreamFunctions.KeySelectors;

import com.jasmine.jasmine_core.Models.JNSemaphoreRoute;
import org.apache.flink.api.java.functions.KeySelector;

public class JNSemaphoreRouteIdKeySelector implements KeySelector<JNSemaphoreRoute, String> {
    private static final long serialVersionUID = 1L;

    @Override
    public String getKey(JNSemaphoreRoute semaphoreRoute) {
        if (semaphoreRoute.getId() == null)
            semaphoreRoute.setId(semaphoreRoute.computeId());
        return semaphoreRoute.getId();
    }
}
