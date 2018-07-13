package com.jasmine.jasmine_core.StreamFunctions.TimestampExtractors;

import com.jasmine.jasmine_core.Models.JNSemaphoreRoute;
import com.jasmine.jasmine_core.Models.JNSemaphoreRouteLeaderboard;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.time.Time;

public class JNSemaphoreRouteLeaderboardTimestampExtractor extends BoundedOutOfOrdernessTimestampExtractor<JNSemaphoreRouteLeaderboard> {
    private static final long serialVersionUID = 1L;

    private static final long MAX_DELAY = 1;

    public JNSemaphoreRouteLeaderboardTimestampExtractor() {
        super(Time.seconds(MAX_DELAY));
    }

    @Override
    public long extractTimestamp(JNSemaphoreRouteLeaderboard semaphoreRouteLeaderboard) {
        long timestamp = Long.MAX_VALUE;
        for (JNSemaphoreRoute semaphoreRoute : semaphoreRouteLeaderboard.getLeaderboard())
            if (semaphoreRoute.get(0).getTimestamp() < timestamp)
                timestamp = semaphoreRoute.get(0).getTimestamp();
        return timestamp;
    }
}
