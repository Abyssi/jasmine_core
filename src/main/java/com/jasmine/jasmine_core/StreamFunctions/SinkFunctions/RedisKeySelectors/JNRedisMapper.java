package com.jasmine.jasmine_core.StreamFunctions.SinkFunctions.RedisKeySelectors;

import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommandDescription;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisMapper;

public class JNRedisMapper<T> implements RedisMapper<T> {
    private JNRedisKeySelector keySelector;
    private RedisCommandDescription commandDescription;

    public JNRedisMapper(JNRedisKeySelector keySelector, RedisCommandDescription commandDescription) {
        this.keySelector = keySelector;
        this.commandDescription = commandDescription;
    }

    @Override
    public RedisCommandDescription getCommandDescription() {
        return this.commandDescription;
    }

    @Override
    public String getKeyFromData(T t) {
        return this.keySelector.getKey(t);
    }

    @Override
    public String getValueFromData(T t) {
        return t.toString();
    }
}
