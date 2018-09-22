package com.jasmine.jasmine_core.Intergation.Masaccio;

import com.jasmine.jasmine_core.Models.JNCrossroads;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

import java.util.List;

public class JNFirstElementInListExtractor implements FlatMapFunction<List<JNCrossroads>, JNCrossroads> {
    private static final long serialVersionUID = 1L;

    @Override
    @SuppressWarnings("unchecked")
    public void flatMap(List<JNCrossroads> ts, Collector<JNCrossroads> collector) throws Exception {
        if (ts.size() > 0)
            collector.collect((JNCrossroads) ts.get(0));
    }
}
