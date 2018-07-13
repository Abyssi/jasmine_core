package com.jasmine.jasmine_core.StreamFunctions.SinkFunctions;

import com.jasmine.jasmine_core.StreamFunctions.SinkFunctions.RedisKeySelectors.JNRedisKeySelector;
import com.jasmine.jasmine_core.StreamFunctions.SinkFunctions.RedisKeySelectors.JNRedisMapper;
import org.apache.flink.streaming.connectors.redis.RedisSink;
import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisConfigBase;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommand;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommandDescription;

public class JNSetRedisSinkFunction<T> extends RedisSink<T> {
    public JNSetRedisSinkFunction(FlinkJedisConfigBase flinkJedisConfigBase, JNRedisKeySelector keySelector) {
        super(flinkJedisConfigBase, new JNRedisMapper<>(keySelector, new RedisCommandDescription(RedisCommand.SET)));
    }
}
