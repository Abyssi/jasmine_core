package com.jasmine.jasmine_core.StreamFunctions.MapFunctions;

import com.jasmine.jasmine_core.Models.JNAggregabileCrossroads;
import com.jasmine.jasmine_core.Models.JNCrossroads;
import org.apache.flink.api.common.functions.MapFunction;

public class JNAggregabileCrossroadsToCrossroadsMapFunction implements MapFunction<JNAggregabileCrossroads, JNCrossroads> {
    private static final long serialVersionUID = 1L;

    @Override
    public JNCrossroads map(JNAggregabileCrossroads aggregabileCrossroads) {
        return new JNCrossroads(aggregabileCrossroads.getId(), aggregabileCrossroads.getAverageSpeed().getAverage(), aggregabileCrossroads.getMedianVehiclesCount().getMedian());
    }
}
