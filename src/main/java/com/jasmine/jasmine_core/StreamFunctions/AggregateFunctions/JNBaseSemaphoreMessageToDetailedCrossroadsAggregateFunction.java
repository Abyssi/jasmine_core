package com.jasmine.jasmine_core.StreamFunctions.AggregateFunctions;

import com.jasmine.jasmine_core.Models.JNBaseSemaphoreMessage;
import com.jasmine.jasmine_core.Models.JNDetailedCrossroads;
import com.jasmine.jasmine_core.Utils.MemorySafeAverage;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JNBaseSemaphoreMessageToDetailedCrossroadsAggregateFunction implements AggregateFunction<JNBaseSemaphoreMessage, Tuple2<JNDetailedCrossroads, List<Tuple3<String, MemorySafeAverage, Integer>>>, JNDetailedCrossroads> {
    private static final long serialVersionUID = 1L;

    @Override
    public Tuple2<JNDetailedCrossroads, List<Tuple3<String, MemorySafeAverage, Integer>>> createAccumulator() {
        return new Tuple2<>(new JNDetailedCrossroads(), new ArrayList<>());
    }

    @Override
    public Tuple2<JNDetailedCrossroads, List<Tuple3<String, MemorySafeAverage, Integer>>> add(JNBaseSemaphoreMessage baseSemaphoreMessage, Tuple2<JNDetailedCrossroads, List<Tuple3<String, MemorySafeAverage, Integer>>> detailedCrossroadsListTuple2) {
        Tuple3<String, MemorySafeAverage, Integer> tuple;
        if ((tuple = this.find(detailedCrossroadsListTuple2.f1, baseSemaphoreMessage.getSemaphoreId())) == null)
            detailedCrossroadsListTuple2.f1.add((tuple = this.createTupleAccumulator()));

        tuple.f0 = baseSemaphoreMessage.getSemaphoreId();
        tuple.f1.add(baseSemaphoreMessage.getAverageSpeed());
        tuple.f2 += baseSemaphoreMessage.getVehiclesCount();

        detailedCrossroadsListTuple2.f0.setId(baseSemaphoreMessage.getCrossroadsId());

        return detailedCrossroadsListTuple2;
    }

    @Override
    public JNDetailedCrossroads getResult(Tuple2<JNDetailedCrossroads, List<Tuple3<String, MemorySafeAverage, Integer>>> detailedCrossroadsListTuple2) {
        detailedCrossroadsListTuple2.f0.getSemaphoreList().addAll(detailedCrossroadsListTuple2.f1.stream().map(tempSemaphore -> new Tuple3<>(tempSemaphore.f0, tempSemaphore.f1.getAverage(), tempSemaphore.f2)).collect(Collectors.toList()));
        return detailedCrossroadsListTuple2.f0;
    }

    @Override
    public Tuple2<JNDetailedCrossroads, List<Tuple3<String, MemorySafeAverage, Integer>>> merge(Tuple2<JNDetailedCrossroads, List<Tuple3<String, MemorySafeAverage, Integer>>> detailedCrossroadsListTuple2, Tuple2<JNDetailedCrossroads, List<Tuple3<String, MemorySafeAverage, Integer>>> acc1) {
        for (Tuple3<String, MemorySafeAverage, Integer> loopTuple : detailedCrossroadsListTuple2.f1) {
            Tuple3<String, MemorySafeAverage, Integer> tuple;
            if ((tuple = this.find(detailedCrossroadsListTuple2.f1, loopTuple.f0)) == null)
                detailedCrossroadsListTuple2.f1.add((tuple = this.createTupleAccumulator()));

            tuple.f0 = loopTuple.f0;
            tuple.f1.merge(loopTuple.f1);
            tuple.f2 += loopTuple.f2;
        }
        return acc1;
    }

    private Tuple3<String, MemorySafeAverage, Integer> find(List<Tuple3<String, MemorySafeAverage, Integer>> list, String id) {
        for (Tuple3<String, MemorySafeAverage, Integer> tuple : list)
            if (tuple.f0.equals(id))
                return tuple;
        return null;
    }

    public Tuple3<String, MemorySafeAverage, Integer> createTupleAccumulator() {
        return new Tuple3<>(null, new MemorySafeAverage(), 0);
    }
}
