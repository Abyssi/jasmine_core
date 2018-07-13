package com.jasmine.jasmine_core.Utils;

import com.tdunning.math.stats.AVLTreeDigest;
import com.tdunning.math.stats.TDigest;

public class MemorySafeMedian {
    private TDigest digest;

    public MemorySafeMedian() {
        this.digest = new AVLTreeDigest(100);
    }

    public void add(double value) {
        this.digest.add(value);
    }

    public void merge(MemorySafeMedian memorySafeMedian) {
        digest.add(memorySafeMedian.getMedian());
    }

    public double getMedian() {
        return this.digest.quantile(0.5);
    }
}
