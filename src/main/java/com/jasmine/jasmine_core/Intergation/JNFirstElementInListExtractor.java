package com.jasmine.jasmine_core.Intergation;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

import java.util.List;

public class JNFirstElementInListExtractor<T> implements FlatMapFunction<List<T>, T> {
    private static final long serialVersionUID = 1L;

    @Override
    public void flatMap(List<T> ts, Collector<T> collector) {
        if (ts.size() > 0)
            collector.collect(ts.get(0));
    }
}
