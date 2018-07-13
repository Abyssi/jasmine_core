package com.jasmine.jasmine_core.Connectors;

import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisClusterConfig;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.List;

public class JNClusterRedisConnector {
    private FlinkJedisClusterConfig config;

    public JNClusterRedisConnector(List<InetSocketAddress> nodes) {
        this.config = new FlinkJedisClusterConfig.Builder().setNodes(new HashSet<>(nodes)).build();
    }

    public FlinkJedisClusterConfig getConfig() {
        return config;
    }
}
