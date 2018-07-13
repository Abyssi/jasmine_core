package com.jasmine.jasmine_core.StreamFunctions.SinkFunctions.RedisKeySelectors;

import com.jasmine.jasmine_core.Models.JNDamagedSemaphore;

public class JNRedisDamagedSemaphoreCompoundIdKeySelector implements JNRedisKeySelector<JNDamagedSemaphore> {
    @Override
    public String getKey(JNDamagedSemaphore semaphore) {
        return String.format("%s-%s", semaphore.getCrossroadsId(), semaphore.getId());
    }
}
