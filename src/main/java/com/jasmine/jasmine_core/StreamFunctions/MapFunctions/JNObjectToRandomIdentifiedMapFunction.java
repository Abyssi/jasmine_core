package com.jasmine.jasmine_core.StreamFunctions.MapFunctions;

import com.jasmine.jasmine_core.Utils.Identified;
import org.apache.flink.api.common.functions.MapFunction;

import java.util.Random;

public class JNObjectToRandomIdentifiedMapFunction<T> implements MapFunction<T, Identified<T>> {
    private static final long serialVersionUID = 1L;
    private Random random = new Random();
    private int max;

    public JNObjectToRandomIdentifiedMapFunction(int max) {
        this.max = max;
    }

    @Override
    public Identified<T> map(T t) {
        return new Identified<>(t, String.valueOf(random.nextInt(max + 1)));
    }
}
