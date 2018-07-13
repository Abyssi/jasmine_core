package com.jasmine.jasmine_core.StreamFunctions.AggregateFunctions;

import com.jasmine.jasmine_core.Models.JNAggregabileCrossroads;
import com.jasmine.jasmine_core.Models.JNBaseSemaphoreMessage;
import org.apache.flink.api.common.functions.AggregateFunction;

public class JNBaseSemaphoreMessageToAggregabileCrossroadsAggregateFunction implements AggregateFunction<JNBaseSemaphoreMessage, JNAggregabileCrossroads, JNAggregabileCrossroads> {
    private static final long serialVersionUID = 1L;

    @Override
    public JNAggregabileCrossroads createAccumulator() {
        return new JNAggregabileCrossroads();
    }

    @Override
    public JNAggregabileCrossroads add(JNBaseSemaphoreMessage baseSemaphoreMessage, JNAggregabileCrossroads acc) {
        acc.setId(baseSemaphoreMessage.getCrossroadsId());
        acc.getAverageSpeed().add(baseSemaphoreMessage.getAverageSpeed());
        acc.getMedianVehiclesCount().add(baseSemaphoreMessage.getVehiclesCount());

        acc.setTimestamp(Math.min(baseSemaphoreMessage.getTimestamp(), acc.getTimestamp()));

        return acc;
    }

    @Override
    public JNAggregabileCrossroads getResult(JNAggregabileCrossroads aggregabileCrossroads) {
        return aggregabileCrossroads;
    }

    @Override
    public JNAggregabileCrossroads merge(JNAggregabileCrossroads aggregabileCrossroads, JNAggregabileCrossroads acc) {
        acc.getAverageSpeed().merge(aggregabileCrossroads.getAverageSpeed());
        acc.getMedianVehiclesCount().merge(aggregabileCrossroads.getMedianVehiclesCount());

        acc.setTimestamp(Math.min(acc.getTimestamp(), aggregabileCrossroads.getTimestamp()));

        return acc;
    }
}
