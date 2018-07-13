package com.jasmine.jasmine_core.Connectors;

import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisPoolConfig;

public class JNSingleRedisConnector {
    private FlinkJedisPoolConfig config;

    public JNSingleRedisConnector(String host, int port) {
        this.config = new FlinkJedisPoolConfig.Builder().setHost(host).setPort(port).build();
    }

    public FlinkJedisPoolConfig getConfig() {
        return config;
    }

}
