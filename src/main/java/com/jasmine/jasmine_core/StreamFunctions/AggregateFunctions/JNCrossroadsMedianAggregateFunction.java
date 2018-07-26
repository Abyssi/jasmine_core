package com.jasmine.jasmine_core.StreamFunctions.AggregateFunctions;

import com.jasmine.jasmine_core.Models.JNCrossroads;
import com.jasmine.jasmine_core.Models.JNMedian;

public class JNCrossroadsMedianAggregateFunction extends IdentifiedInputAggregateFunction<JNCrossroads, JNMedian, JNMedian> {
    @Override
    public JNMedian createAccumulator() {
        return new JNMedian(Long.MAX_VALUE);
    }

    @Override
    public JNMedian _add(JNCrossroads crossroads, JNMedian memorySafeMedian) {
        memorySafeMedian.add(crossroads.getMedianVehiclesCount());
        memorySafeMedian.setTimestamp(Long.min(crossroads.getTimestamp(), memorySafeMedian.getTimestamp()));
        return memorySafeMedian;
    }

    @Override
    public JNMedian getResult(JNMedian memorySafeMedian) {
        return memorySafeMedian;
    }

    @Override
    public JNMedian merge(JNMedian memorySafeMedian, JNMedian acc1) {
        acc1.merge(memorySafeMedian);
        acc1.setTimestamp(Long.min(acc1.getTimestamp(), memorySafeMedian.getTimestamp()));
        return acc1;
    }
}
