package com.jasmine.jasmine_core.StreamFunctions.AggregateFunctions;

import com.jasmine.jasmine_core.Utils.Identified;
import org.apache.flink.api.common.functions.AggregateFunction;

public abstract class IdentifiedInputAggregateFunction<IN, ACC, OUT> implements AggregateFunction<Identified<IN>, ACC, OUT> {
    @Override
    public ACC add(Identified<IN> decorable, ACC acc) {
        return this._add(decorable.getBase(), acc);
    }

    public abstract ACC _add(IN in, ACC acc);
}
