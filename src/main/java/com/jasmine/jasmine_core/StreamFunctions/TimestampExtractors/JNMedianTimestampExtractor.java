package com.jasmine.jasmine_core.StreamFunctions.TimestampExtractors;

import com.jasmine.jasmine_core.Models.JNMedian;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.time.Time;

public class JNMedianTimestampExtractor extends BoundedOutOfOrdernessTimestampExtractor<JNMedian> {
    private static final long serialVersionUID = 1L;

    private static final long MAX_DELAY = 1;

    public JNMedianTimestampExtractor() {
        super(Time.seconds(MAX_DELAY));
    }

    @Override
    public long extractTimestamp(JNMedian median) {
        return median.getTimestamp();
    }
}
