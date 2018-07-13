package com.jasmine.jasmine_core.Models;

import com.jasmine.jasmine_core.Utils.MemorySafeMedian;

public class JNMedian extends MemorySafeMedian {
    private long timestamp;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
