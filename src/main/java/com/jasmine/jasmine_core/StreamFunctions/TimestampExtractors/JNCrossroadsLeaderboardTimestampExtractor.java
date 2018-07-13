package com.jasmine.jasmine_core.StreamFunctions.TimestampExtractors;

import com.jasmine.jasmine_core.Models.JNCrossroads;
import com.jasmine.jasmine_core.Models.JNCrossroadsLeaderboard;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.time.Time;

public class JNCrossroadsLeaderboardTimestampExtractor extends BoundedOutOfOrdernessTimestampExtractor<JNCrossroadsLeaderboard> {
    private static final long serialVersionUID = 1L;

    private static final long MAX_DELAY = 1;

    public JNCrossroadsLeaderboardTimestampExtractor() {
        super(Time.seconds(MAX_DELAY));
    }

    @Override
    public long extractTimestamp(JNCrossroadsLeaderboard crossroadsLeaderboard) {
        long timestamp = Long.MAX_VALUE;
        for (JNCrossroads crossroads : crossroadsLeaderboard.getLeaderboard())
            if (crossroads.getTimestamp() < timestamp)
                timestamp = crossroads.getTimestamp();
        return timestamp;
    }
}
