package com.jasmine.jasmine_core.StreamFunctions.SinkFunctions.RedisKeySelectors;

public class JNRedisStaticKeySelector<IN> implements JNRedisKeySelector<IN> {
    private String key;

    public JNRedisStaticKeySelector(String key) {
        this.key = key;
    }

    @Override
    public String getKey(IN var1) {
        return this.key;
    }
}
