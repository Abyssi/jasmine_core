package com.jasmine.jasmine_core.StreamFunctions.TimestampExtractors;

import com.jasmine.jasmine_core.Models.JNSemaphoreRoute;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.time.Time;

public class JNSemaphoreRouteTimestampExtractor extends BoundedOutOfOrdernessTimestampExtractor<JNSemaphoreRoute> {
    private static final long serialVersionUID = 1L;

    private static final long MAX_DELAY = 1;

    public JNSemaphoreRouteTimestampExtractor() {
        super(Time.seconds(MAX_DELAY));
    }

    @Override
    public long extractTimestamp(JNSemaphoreRoute semaphoreRoute) {
        return semaphoreRoute.get(0).getTimestamp();
    }
}
