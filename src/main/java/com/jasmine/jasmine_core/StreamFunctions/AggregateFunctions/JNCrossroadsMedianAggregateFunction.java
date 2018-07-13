package com.jasmine.jasmine_core.StreamFunctions.AggregateFunctions;

import com.jasmine.jasmine_core.Models.JNCrossroads;
import com.jasmine.jasmine_core.Models.JNMedian;
import org.apache.flink.api.common.functions.AggregateFunction;

public class JNCrossroadsMedianAggregateFunction implements AggregateFunction<JNCrossroads, JNMedian, JNMedian> {
    @Override
    public JNMedian createAccumulator() {
        return new JNMedian();
    }

    @Override
    public JNMedian add(JNCrossroads crossroads, JNMedian memorySafeMedian) {
        memorySafeMedian.add(crossroads.getMedianVehiclesCount());
        return memorySafeMedian;
    }

    @Override
    public JNMedian getResult(JNMedian memorySafeMedian) {
        return memorySafeMedian;
    }

    @Override
    public JNMedian merge(JNMedian memorySafeMedian, JNMedian acc1) {
        acc1.merge(memorySafeMedian);
        return acc1;
    }
}
