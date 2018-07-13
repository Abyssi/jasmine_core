package com.jasmine.jasmine_core.StreamFunctions.AggregateFunctions;

import com.jasmine.jasmine_core.Connectors.Messages.JNMobileMessage;
import com.jasmine.jasmine_core.Models.JNMobilePing;
import com.jasmine.jasmine_core.Models.JNMobileRoute;
import com.jasmine.jasmine_core.Utils.MemorySafeAverage;
import org.apache.flink.api.common.functions.AggregateFunction;
import scala.Tuple2;

public class JNMobileMessageToMobileRouteAggregateFunction implements AggregateFunction<JNMobileMessage, Tuple2<JNMobileRoute, MemorySafeAverage>, JNMobileRoute> {
    private static final long serialVersionUID = 1L;

    @Override
    public Tuple2<JNMobileRoute, MemorySafeAverage> createAccumulator() {
        return new Tuple2<>(new JNMobileRoute(), new MemorySafeAverage());
    }

    @Override
    public Tuple2<JNMobileRoute, MemorySafeAverage> add(JNMobileMessage mobileMessage, Tuple2<JNMobileRoute, MemorySafeAverage> mobileRouteOverflowSafeAverageTuple2) {
        mobileRouteOverflowSafeAverageTuple2._1.setId(mobileMessage.getId());
        mobileRouteOverflowSafeAverageTuple2._1.add(new JNMobilePing(mobileMessage.getPosition(), mobileMessage.getSpeed(), mobileMessage.getTimestamp()));
        mobileRouteOverflowSafeAverageTuple2._2.add(mobileMessage.getSpeed());
        return mobileRouteOverflowSafeAverageTuple2;
    }

    @Override
    public JNMobileRoute getResult(Tuple2<JNMobileRoute, MemorySafeAverage> mobileRouteOverflowSafeAverageTuple2) {
        mobileRouteOverflowSafeAverageTuple2._1.setAverageSpeed(mobileRouteOverflowSafeAverageTuple2._2.getAverage());
        return mobileRouteOverflowSafeAverageTuple2._1;
    }

    @Override
    public Tuple2<JNMobileRoute, MemorySafeAverage> merge(Tuple2<JNMobileRoute, MemorySafeAverage> mobileRouteOverflowSafeAverageTuple2, Tuple2<JNMobileRoute, MemorySafeAverage> acc1) {
        acc1._1.merge(mobileRouteOverflowSafeAverageTuple2._1);
        acc1._2.merge(mobileRouteOverflowSafeAverageTuple2._2);
        return acc1;
    }
}
