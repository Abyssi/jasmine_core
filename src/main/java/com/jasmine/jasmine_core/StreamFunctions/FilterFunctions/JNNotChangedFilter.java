package com.jasmine.jasmine_core.StreamFunctions.FilterFunctions;

import org.apache.flink.api.common.functions.FilterFunction;

public class JNNotChangedFilter<T> implements FilterFunction<T> {
    private static final long serialVersionUID = 1L;

    private T o = null;

    @Override
    public boolean filter(T t) throws Exception {
        boolean changed = !t.equals(o);
        if (changed) o = t;
        return changed;
    }
}
