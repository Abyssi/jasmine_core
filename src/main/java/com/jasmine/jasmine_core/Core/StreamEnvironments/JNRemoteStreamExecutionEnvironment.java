package com.jasmine.jasmine_core.Core.StreamEnvironments;

import com.jasmine.jasmine_core.Utils.FlinkParameters;
import org.apache.flink.runtime.state.StateBackend;
import org.apache.flink.runtime.state.memory.MemoryStateBackend;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.environment.RemoteStreamEnvironment;

public class JNRemoteStreamExecutionEnvironment extends RemoteStreamEnvironment {

    public JNRemoteStreamExecutionEnvironment() {
        super("www.jasmine.cf", 32023);

        //configure environment
        this.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        this.setParallelism(FlinkParameters.getParameters().getInt("flink.parallelism", 4));
        if (!FlinkParameters.getParameters().getBoolean("flink.operation-chaining.enabled", false))
            this.disableOperatorChaining();

        if (FlinkParameters.getParameters().getInt("flink.checkpoint.millis", 0) != 0)
            this.enableCheckpointing(FlinkParameters.getParameters().getInt("flink.checkpoint.millis", 60000));

        if (FlinkParameters.getParameters().getInt("flink.latency.millis", 0) != 0)
            this.getConfig().setLatencyTrackingInterval(FlinkParameters.getParameters().getInt("flink.latency.millis", 5));

        if (FlinkParameters.getParameters().getInt("flink.memory-state-size", 0) != 0)
            this.setStateBackend((StateBackend) new MemoryStateBackend(FlinkParameters.getParameters().getInt("flink.memory-state-size", 5242880)));

        if (FlinkParameters.getParameters().getBoolean("flink.snapshot-compression.enabled", false))
            this.getConfig().setUseSnapshotCompression(true);
    }

}
