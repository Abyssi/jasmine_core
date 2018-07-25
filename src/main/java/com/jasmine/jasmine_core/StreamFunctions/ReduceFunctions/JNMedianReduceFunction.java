package com.jasmine.jasmine_core.StreamFunctions.ReduceFunctions;

import com.jasmine.jasmine_core.Models.JNMedian;
import org.apache.flink.api.common.functions.ReduceFunction;

public class JNMedianReduceFunction implements ReduceFunction<JNMedian> {
    @Override
    public JNMedian reduce(JNMedian median, JNMedian acc) throws Exception {
        acc.merge(median);
        return acc;
    }
}
