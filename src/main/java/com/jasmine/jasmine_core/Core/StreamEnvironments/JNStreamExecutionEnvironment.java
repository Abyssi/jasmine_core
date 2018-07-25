package com.jasmine.jasmine_core.Core.StreamEnvironments;

import com.jasmine.jasmine_core.Utils.FlinkParameters;
import org.apache.flink.runtime.state.StateBackend;
import org.apache.flink.runtime.state.memory.MemoryStateBackend;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class JNStreamExecutionEnvironment {

    public static StreamExecutionEnvironment getExecutionEnvironment() {
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();

        //configure environment
        environment.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        environment.setParallelism(FlinkParameters.getParameters().getInt("flink.parallelism", 4));
        if (!FlinkParameters.getParameters().getBoolean("flink.operation-chaining.enabled", false))
            environment.disableOperatorChaining();

        if (FlinkParameters.getParameters().getInt("flink.checkpoint.millis", 0) != 0) {
            environment.enableCheckpointing(FlinkParameters.getParameters().getInt("flink.checkpoint.millis", 60000));
            environment.getCheckpointConfig().setMaxConcurrentCheckpoints(1);
            environment.getCheckpointConfig().setFailOnCheckpointingErrors(false);
        }
        if (FlinkParameters.getParameters().getInt("flink.latency.millis", 0) != 0)
            environment.getConfig().setLatencyTrackingInterval(FlinkParameters.getParameters().getInt("flink.latency.millis", 5));

        if (FlinkParameters.getParameters().getInt("flink.memory-state-size", 0) != 0)
            environment.setStateBackend((StateBackend) new MemoryStateBackend(FlinkParameters.getParameters().getInt("flink.memory-state-size", 5242880)));

        if (FlinkParameters.getParameters().getBoolean("flink.snapshot-compression.enabled", false))
            environment.getConfig().setUseSnapshotCompression(true);

        return environment;
    }
}
