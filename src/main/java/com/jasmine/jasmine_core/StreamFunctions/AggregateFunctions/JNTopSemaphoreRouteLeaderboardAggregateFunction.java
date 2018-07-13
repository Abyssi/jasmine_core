package com.jasmine.jasmine_core.StreamFunctions.AggregateFunctions;

import com.jasmine.jasmine_core.Models.JNLeaderboard;
import com.jasmine.jasmine_core.Models.JNSemaphoreRoute;
import com.jasmine.jasmine_core.Models.JNSemaphoreRouteLeaderboard;
import org.apache.flink.api.common.functions.AggregateFunction;

public class JNTopSemaphoreRouteLeaderboardAggregateFunction implements AggregateFunction<JNSemaphoreRoute, JNSemaphoreRouteLeaderboard, JNSemaphoreRouteLeaderboard> {
    private static final long serialVersionUID = 1L;

    private int maxSize;

    public JNTopSemaphoreRouteLeaderboardAggregateFunction(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public JNSemaphoreRouteLeaderboard createAccumulator() {
        return new JNSemaphoreRouteLeaderboard(this.maxSize, JNLeaderboard.ORDER.DESCENDING);
    }

    @Override
    public JNSemaphoreRouteLeaderboard add(JNSemaphoreRoute semaphoreRoute, JNSemaphoreRouteLeaderboard semaphoreLeaderboard) {
        semaphoreLeaderboard.add(semaphoreRoute);
        return semaphoreLeaderboard;
    }

    @Override
    public JNSemaphoreRouteLeaderboard getResult(JNSemaphoreRouteLeaderboard semaphoreLeaderboard) {
        return semaphoreLeaderboard;
    }

    @Override
    public JNSemaphoreRouteLeaderboard merge(JNSemaphoreRouteLeaderboard semaphoreLeaderboard, JNSemaphoreRouteLeaderboard acc1) {
        acc1.merge(semaphoreLeaderboard);
        return acc1;
    }
}
