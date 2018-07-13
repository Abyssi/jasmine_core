package com.jasmine.jasmine_core.StreamFunctions.FlatMapFunctions;

import com.jasmine.jasmine_core.Models.JNCrossroads;
import org.apache.flink.streaming.api.checkpoint.ListCheckpointed;
import org.apache.flink.streaming.api.functions.co.CoFlatMapFunction;
import org.apache.flink.util.Collector;

import java.util.Collections;
import java.util.List;

public class JNBiggerThanMedianCrossroadsCoFlatMapFunction implements CoFlatMapFunction<Double, JNCrossroads, JNCrossroads>, ListCheckpointed<Double> {
    private static final long serialVersionUID = 1L;

    private Double currentMedian = 0d;

    @Override
    public void flatMap1(Double value, Collector<JNCrossroads> out) throws Exception {
        currentMedian = value;
    }

    @Override
    public void flatMap2(JNCrossroads crossroads, Collector<JNCrossroads> out) throws Exception {
        if (currentMedian != null && crossroads.getMedianVehiclesCount() > currentMedian)
            out.collect(crossroads);
    }

    @Override
    public List<Double> snapshotState(long checkpointId, long checkpointTimestamp) {
        return Collections.singletonList(currentMedian);
    }

    @Override
    public void restoreState(List<Double> state) {
        for (Double s : state)
            currentMedian = s;
    }
}
