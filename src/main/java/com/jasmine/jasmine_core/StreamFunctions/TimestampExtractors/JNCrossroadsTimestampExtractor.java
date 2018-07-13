package com.jasmine.jasmine_core.StreamFunctions.TimestampExtractors;

import com.jasmine.jasmine_core.Models.JNCrossroads;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.time.Time;

public class JNCrossroadsTimestampExtractor extends BoundedOutOfOrdernessTimestampExtractor<JNCrossroads> {
    private static final long serialVersionUID = 1L;

    private static final long MAX_DELAY = 1;

    public JNCrossroadsTimestampExtractor() {
        super(Time.seconds(MAX_DELAY));
    }

    @Override
    public long extractTimestamp(JNCrossroads crossroads) {
        return crossroads.getTimestamp();
    }
}
