package com.jasmine.jasmine_core.StreamFunctions.AggregateFunctions;

import com.jasmine.jasmine_core.Models.JNAggregabileCrossroads;
import org.apache.flink.api.common.functions.AggregateFunction;

public class JNAggregabileCrossroadsAggregateFunction implements AggregateFunction<JNAggregabileCrossroads, JNAggregabileCrossroads, JNAggregabileCrossroads> {
    private static final long serialVersionUID = 1L;

    @Override
    public JNAggregabileCrossroads createAccumulator() {
        return new JNAggregabileCrossroads();
    }

    @Override
    public JNAggregabileCrossroads add(JNAggregabileCrossroads aggregabileCrossroads, JNAggregabileCrossroads acc) {
        acc.setId(aggregabileCrossroads.getId());
        acc.getAverageSpeed().merge(aggregabileCrossroads.getAverageSpeed());
        acc.getMedianVehiclesCount().merge(aggregabileCrossroads.getMedianVehiclesCount());
        return acc;
    }

    @Override
    public JNAggregabileCrossroads getResult(JNAggregabileCrossroads aggregabileCrossroads) {
        return aggregabileCrossroads;
    }

    @Override
    public JNAggregabileCrossroads merge(JNAggregabileCrossroads aggregabileCrossroads, JNAggregabileCrossroads acc) {
        acc.setId(aggregabileCrossroads.getId());
        acc.getAverageSpeed().merge(aggregabileCrossroads.getAverageSpeed());
        acc.getMedianVehiclesCount().merge(aggregabileCrossroads.getMedianVehiclesCount());
        return acc;
    }
}
