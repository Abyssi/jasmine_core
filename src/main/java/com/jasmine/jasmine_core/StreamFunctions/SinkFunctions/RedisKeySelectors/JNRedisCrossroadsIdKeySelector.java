package com.jasmine.jasmine_core.StreamFunctions.SinkFunctions.RedisKeySelectors;

import com.jasmine.jasmine_core.Models.JNCrossroads;

public class JNRedisCrossroadsIdKeySelector implements JNRedisKeySelector<JNCrossroads> {
    @Override
    public String getKey(JNCrossroads crossroads) {
        return crossroads.getId();
    }
}
