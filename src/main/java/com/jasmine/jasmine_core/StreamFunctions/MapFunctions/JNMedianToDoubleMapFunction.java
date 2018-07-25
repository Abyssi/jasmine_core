package com.jasmine.jasmine_core.StreamFunctions.MapFunctions;

import com.jasmine.jasmine_core.Models.JNMedian;
import org.apache.flink.api.common.functions.MapFunction;

public class JNMedianToDoubleMapFunction implements MapFunction<JNMedian, Double> {
    private static final long serialVersionUID = 1L;

    @Override
    public Double map(JNMedian median) throws Exception {
        return median.getMedian();
    }
}
