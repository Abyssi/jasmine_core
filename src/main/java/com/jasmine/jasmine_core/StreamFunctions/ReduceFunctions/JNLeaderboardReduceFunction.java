package com.jasmine.jasmine_core.StreamFunctions.ReduceFunctions;

import com.jasmine.jasmine_core.Models.JNLeaderboard;
import org.apache.flink.api.common.functions.ReduceFunction;

public class JNLeaderboardReduceFunction<T> implements ReduceFunction<JNLeaderboard<T>> {
    @Override
    public JNLeaderboard<T> reduce(JNLeaderboard<T> tjnLeaderboard, JNLeaderboard<T> t1) throws Exception {
        t1.merge(tjnLeaderboard);
        return t1;
    }
}
