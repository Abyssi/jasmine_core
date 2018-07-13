package com.jasmine.jasmine_core.StreamFunctions.TimestampExtractors.Abstracts;

import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor;

public abstract class IgnoringViolationAscendingTimestampExtractor<T> extends AscendingTimestampExtractor<T> {
    public IgnoringViolationAscendingTimestampExtractor() {
        this.withViolationHandler(new AscendingTimestampExtractor.IgnoringHandler());
    }
}
