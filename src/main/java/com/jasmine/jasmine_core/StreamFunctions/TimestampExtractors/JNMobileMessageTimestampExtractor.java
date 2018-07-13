package com.jasmine.jasmine_core.StreamFunctions.TimestampExtractors;

import com.jasmine.jasmine_core.Connectors.Messages.JNMobileMessage;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.time.Time;

public class JNMobileMessageTimestampExtractor extends BoundedOutOfOrdernessTimestampExtractor<JNMobileMessage> {
    private static final long serialVersionUID = 1L;

    private static final long MAX_DELAY = 1;

    public JNMobileMessageTimestampExtractor() {
        super(Time.seconds(MAX_DELAY));
    }

    @Override
    public long extractTimestamp(JNMobileMessage mobileMessage) {
        return mobileMessage.getTimestamp();
    }
}
