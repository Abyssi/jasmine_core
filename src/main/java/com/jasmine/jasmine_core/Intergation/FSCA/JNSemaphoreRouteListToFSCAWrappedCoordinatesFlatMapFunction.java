package com.jasmine.jasmine_core.Intergation.FSCA;

import com.jasmine.jasmine_core.Models.JNSemaphorePing;
import com.jasmine.jasmine_core.Models.JNSemaphoreRoute;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

import java.util.List;

public class JNSemaphoreRouteListToFSCAWrappedCoordinatesFlatMapFunction implements FlatMapFunction<List<JNSemaphoreRoute>, FSCAWrappedCoordinates> {
    private static final long serialVersionUID = 1L;

    @Override
    public void flatMap(List<JNSemaphoreRoute> routes, Collector<FSCAWrappedCoordinates> collector) throws Exception {
        for (JNSemaphoreRoute route : routes)
            for (JNSemaphorePing ping : route) {
                collector.collect(new FSCAWrappedCoordinates(new FSCALatLonCoordinates(44.910377, 8.606731)));
            }
    }

}
