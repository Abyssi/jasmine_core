package com.jasmine.jasmine_core.StreamFunctions.AggregateFunctions;

import com.jasmine.jasmine_core.Models.JNBaseSemaphoreMessage;
import com.jasmine.jasmine_core.Models.JNCrossroads;
import com.jasmine.jasmine_core.Utils.MemorySafeAverage;
import com.jasmine.jasmine_core.Utils.MemorySafeMedian;
import org.apache.flink.api.common.functions.AggregateFunction;
import scala.Tuple3;

public class JNBaseSemaphoreMessageToCrossroadsAggregateFunction implements AggregateFunction<JNBaseSemaphoreMessage, Tuple3<JNCrossroads, MemorySafeAverage, MemorySafeMedian>, JNCrossroads> {
    private static final long serialVersionUID = 1L;

    @Override
    public Tuple3<JNCrossroads, MemorySafeAverage, MemorySafeMedian> createAccumulator() {
        return new Tuple3<>(new JNCrossroads(), new MemorySafeAverage(), new MemorySafeMedian());
    }

    @Override
    public Tuple3<JNCrossroads, MemorySafeAverage, MemorySafeMedian> add(JNBaseSemaphoreMessage baseSemaphoreMessage, Tuple3<JNCrossroads, MemorySafeAverage, MemorySafeMedian> crossroadsOverflowSafeAverageSafeMedianTuple) {
        crossroadsOverflowSafeAverageSafeMedianTuple._1().setId(baseSemaphoreMessage.getCrossroadsId());
        crossroadsOverflowSafeAverageSafeMedianTuple._2().add(baseSemaphoreMessage.getAverageSpeed());
        crossroadsOverflowSafeAverageSafeMedianTuple._3().add(baseSemaphoreMessage.getVehiclesCount());

        crossroadsOverflowSafeAverageSafeMedianTuple._1().setTimestamp(Math.max(baseSemaphoreMessage.getTimestamp(), crossroadsOverflowSafeAverageSafeMedianTuple._1().getTimestamp()));

        return crossroadsOverflowSafeAverageSafeMedianTuple;
    }

    @Override
    public JNCrossroads getResult(Tuple3<JNCrossroads, MemorySafeAverage, MemorySafeMedian> crossroadsOverflowSafeAverageSafeMedianTuple) {
        crossroadsOverflowSafeAverageSafeMedianTuple._1().setAverageSpeed(crossroadsOverflowSafeAverageSafeMedianTuple._2().getAverage());
        crossroadsOverflowSafeAverageSafeMedianTuple._1().setMedianVehiclesCount(crossroadsOverflowSafeAverageSafeMedianTuple._3().getMedian());
        return crossroadsOverflowSafeAverageSafeMedianTuple._1();
    }

    @Override
    public Tuple3<JNCrossroads, MemorySafeAverage, MemorySafeMedian> merge(Tuple3<JNCrossroads, MemorySafeAverage, MemorySafeMedian> crossroadsOverflowSafeAverageSafeMedianTuple, Tuple3<JNCrossroads, MemorySafeAverage, MemorySafeMedian> acc1) {
        acc1._2().merge(crossroadsOverflowSafeAverageSafeMedianTuple._2());
        acc1._3().merge(crossroadsOverflowSafeAverageSafeMedianTuple._3());

        crossroadsOverflowSafeAverageSafeMedianTuple._1().setTimestamp(Math.max(acc1._1().getTimestamp(), crossroadsOverflowSafeAverageSafeMedianTuple._1().getTimestamp()));

        return acc1;
    }
}
