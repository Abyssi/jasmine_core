package com.jasmine.jasmine_core.StreamFunctions.SinkFunctions.RedisKeySelectors;

import com.jasmine.jasmine_core.Models.JNSemaphoreRoute;

public class JNRedisSemaphoreRouteIdKeySelector implements JNRedisKeySelector<JNSemaphoreRoute> {
    @Override
    public String getKey(JNSemaphoreRoute semaphoreRoute) {
        return semaphoreRoute.getId();
    }
}
