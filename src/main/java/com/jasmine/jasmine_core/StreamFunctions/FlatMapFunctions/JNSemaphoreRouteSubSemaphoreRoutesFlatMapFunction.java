package com.jasmine.jasmine_core.StreamFunctions.FlatMapFunctions;

import com.jasmine.jasmine_core.Models.JNSemaphoreRoute;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

public class JNSemaphoreRouteSubSemaphoreRoutesFlatMapFunction implements FlatMapFunction<JNSemaphoreRoute, JNSemaphoreRoute> {
    private static final long serialVersionUID = 1L;

    @Override
    public void flatMap(JNSemaphoreRoute semaphoreRoute, Collector<JNSemaphoreRoute> collector) {
        for (int i = 2; i <= semaphoreRoute.size(); i++)
            for (int j = 0; j <= semaphoreRoute.size() - i; j++)
                collector.collect(new JNSemaphoreRoute(semaphoreRoute.subList(j, j + i)));
    }
}
