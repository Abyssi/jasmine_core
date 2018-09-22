package com.jasmine.jasmine_core.Intergation.FSCA;

import com.jasmine.jasmine_core.Models.JNDamagedSemaphore;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

public class FSCATest implements FlatMapFunction<JNDamagedSemaphore, FSCAWrappedCoordinates> {
    private static final long serialVersionUID = 1L;

    @Override
    public void flatMap(JNDamagedSemaphore routes, Collector<FSCAWrappedCoordinates> collector) throws Exception {
        collector.collect(new FSCAWrappedCoordinates(new FSCALatLonCoordinates(44.910377, 8.606731)));
    }

}

