package com.jasmine.jasmine_core.StreamFunctions.TimestampExtractors;

import com.jasmine.jasmine_core.Models.JNBaseSemaphoreMessage;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.time.Time;

public class JNBaseSemaphoreMessageTimestampExtractor extends BoundedOutOfOrdernessTimestampExtractor<JNBaseSemaphoreMessage> {
    private static final long serialVersionUID = 1L;

    private static final long MAX_DELAY = 1;

    public JNBaseSemaphoreMessageTimestampExtractor() {
        super(Time.seconds(MAX_DELAY));
    }

    @Override
    public long extractTimestamp(JNBaseSemaphoreMessage baseSemaphoreMessage) {
        return baseSemaphoreMessage.getTimestamp();
    }
}
