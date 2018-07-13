package com.jasmine.jasmine_core.StreamFunctions.AggregateFunctions;

import com.jasmine.jasmine_core.Models.JNMedian;
import org.apache.flink.api.common.functions.AggregateFunction;

import java.util.Optional;

public class JNMedianAggregateFunction implements AggregateFunction<JNMedian, Optional<JNMedian>, Double> {
    private static final long serialVersionUID = 1L;

    @Override
    public Optional<JNMedian> createAccumulator() {
        return Optional.empty();
    }

    @Override
    public Optional<JNMedian> add(JNMedian median, Optional<JNMedian> acc) {
        if (!acc.isPresent())
            return Optional.of(median);

        acc.get().merge(median);
        return acc;
    }

    @Override
    public Double getResult(Optional<JNMedian> acc) {
        return acc.get().getMedian();
    }

    @Override
    public Optional<JNMedian> merge(Optional<JNMedian> median, Optional<JNMedian> acc) {
        if (!acc.isPresent())
            return median;

        if (!median.isPresent())
            return acc;

        acc.get().merge(median.get());
        return acc;
    }
}
