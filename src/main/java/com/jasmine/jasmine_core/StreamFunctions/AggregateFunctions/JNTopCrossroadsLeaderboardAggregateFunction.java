package com.jasmine.jasmine_core.StreamFunctions.AggregateFunctions;

import com.jasmine.jasmine_core.Models.JNCrossroads;
import com.jasmine.jasmine_core.Models.JNCrossroadsLeaderboard;
import com.jasmine.jasmine_core.Models.JNLeaderboard;
import org.apache.flink.api.common.functions.AggregateFunction;

public class JNTopCrossroadsLeaderboardAggregateFunction implements AggregateFunction<JNCrossroads, JNCrossroadsLeaderboard, JNCrossroadsLeaderboard> {
    private static final long serialVersionUID = 1L;

    private int maxSize;

    public JNTopCrossroadsLeaderboardAggregateFunction(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public JNCrossroadsLeaderboard createAccumulator() {
        return new JNCrossroadsLeaderboard(this.maxSize, JNLeaderboard.ORDER.DESCENDING);
    }

    @Override
    public JNCrossroadsLeaderboard add(JNCrossroads crossroads, JNCrossroadsLeaderboard crossroadsLeaderboard) {
        crossroadsLeaderboard.add(crossroads);
        return crossroadsLeaderboard;
    }

    @Override
    public JNCrossroadsLeaderboard getResult(JNCrossroadsLeaderboard crossroadsLeaderboard) {
        return crossroadsLeaderboard;
    }

    @Override
    public JNCrossroadsLeaderboard merge(JNCrossroadsLeaderboard crossroadsLeaderboard, JNCrossroadsLeaderboard acc1) {
        acc1.merge(crossroadsLeaderboard);
        return acc1;
    }
}
