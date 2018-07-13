package com.jasmine.jasmine_core.StreamFunctions.TimestampExtractors;

import com.jasmine.jasmine_core.Models.JNAggregabileCrossroads;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.time.Time;

public class JNAggregabileCrossroadsTimestampExtractor extends BoundedOutOfOrdernessTimestampExtractor<JNAggregabileCrossroads> {
    private static final long serialVersionUID = 1L;

    private static final long MAX_DELAY = 1;

    public JNAggregabileCrossroadsTimestampExtractor() {
        super(Time.seconds(MAX_DELAY));
    }

    @Override
    public long extractTimestamp(JNAggregabileCrossroads aggregabileCrossroads) {
        return aggregabileCrossroads.getTimestamp();
    }
}
   
