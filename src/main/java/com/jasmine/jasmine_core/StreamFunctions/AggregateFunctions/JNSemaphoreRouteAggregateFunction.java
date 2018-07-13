package com.jasmine.jasmine_core.StreamFunctions.AggregateFunctions;

import com.jasmine.jasmine_core.Models.JNSemaphorePing;
import com.jasmine.jasmine_core.Models.JNSemaphoreRoute;
import com.jasmine.jasmine_core.Utils.MemorySafeAverage;
import org.apache.flink.api.common.functions.AggregateFunction;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.List;

public class JNSemaphoreRouteAggregateFunction implements AggregateFunction<JNSemaphoreRoute, Tuple2<JNSemaphoreRoute, List<MemorySafeAverage>>, JNSemaphoreRoute> {
    private static final long serialVersionUID = 1L;

    @Override
    public Tuple2<JNSemaphoreRoute, List<MemorySafeAverage>> createAccumulator() {
        return new Tuple2<>(new JNSemaphoreRoute(), new ArrayList<>());
    }

    @Override
    public Tuple2<JNSemaphoreRoute, List<MemorySafeAverage>> add(JNSemaphoreRoute semaphoreRoute, Tuple2<JNSemaphoreRoute, List<MemorySafeAverage>> acc1) {
        if (acc1._1.size() == 0) {
            acc1._1.addAll(semaphoreRoute);
            for (JNSemaphorePing semaphorePing : acc1._1)
                acc1._2.add(new MemorySafeAverage(semaphorePing.getSpeed()));
        } else {
            for (int i = 0; i < acc1._1.size(); i++)
                acc1._2.get(i).add(semaphoreRoute.get(i).getSpeed());
        }
        acc1._1.setVehiclesCount(acc1._1.getVehiclesCount() + 1);
        return acc1;
    }

    @Override
    public JNSemaphoreRoute getResult(Tuple2<JNSemaphoreRoute, List<MemorySafeAverage>> acc) {
        for (int i = 0; i < acc._1.size(); i++)
            acc._1.get(i).setSpeed(acc._2.get(i).getAverage());

        acc._1.setCongestion(acc._1.computeCongestion());
        acc._1.setId(acc._1.computeId());

        return acc._1;
    }

    @Override
    public Tuple2<JNSemaphoreRoute, List<MemorySafeAverage>> merge(Tuple2<JNSemaphoreRoute, List<MemorySafeAverage>> semaphoreRouteAndAverageTuple, Tuple2<JNSemaphoreRoute, List<MemorySafeAverage>> acc1) {
        if (acc1._1.size() == 0)
            acc1._1.addAll(semaphoreRouteAndAverageTuple._1);
        for (int i = 0; i < acc1._1.size(); i++)
            acc1._2.get(i).merge(semaphoreRouteAndAverageTuple._2.get(i));
        acc1._1.setVehiclesCount(semaphoreRouteAndAverageTuple._1.getVehiclesCount());
        return acc1;
    }
}
