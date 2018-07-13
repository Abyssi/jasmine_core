package com.jasmine.jasmine_core.Models;

import com.jasmine.jasmine_core.Utils.MemorySafeAverage;
import com.jasmine.jasmine_core.Utils.MemorySafeMedian;

public class JNAggregabileCrossroads {

    private String id;
    private MemorySafeAverage averageSpeed;
    private MemorySafeMedian medianVehiclesCount;
    private long timestamp;

    public JNAggregabileCrossroads() {
        this("");
    }

    public JNAggregabileCrossroads(String id) {
        this(id, new MemorySafeAverage(), new MemorySafeMedian());
    }

    public JNAggregabileCrossroads(String id, MemorySafeAverage averageSpeed, MemorySafeMedian medianVehiclesCount) {
        this.id = id;
        this.averageSpeed = averageSpeed;
        this.medianVehiclesCount = medianVehiclesCount;
    }

    /*
        Getter and Setter
     */

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MemorySafeAverage getAverageSpeed() {
        return this.averageSpeed;
    }

    public void setAverageSpeed(MemorySafeAverage averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public MemorySafeMedian getMedianVehiclesCount() {
        return this.medianVehiclesCount;
    }

    public void setMedianVehiclesCount(MemorySafeMedian medianVehiclesCount) {
        this.medianVehiclesCount = medianVehiclesCount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}
