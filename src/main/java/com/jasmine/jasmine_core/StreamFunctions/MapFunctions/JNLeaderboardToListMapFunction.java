package com.jasmine.jasmine_core.StreamFunctions.MapFunctions;

import com.jasmine.jasmine_core.Models.JNLeaderboard;
import org.apache.flink.api.common.functions.MapFunction;

import java.util.List;

public class JNLeaderboardToListMapFunction<T> implements MapFunction<JNLeaderboard<T>, List<T>> {
    private static final long serialVersionUID = 1L;

    @Override
    public List<T> map(JNLeaderboard<T> tjnLeaderboard) throws Exception {
        return tjnLeaderboard.getLeaderboard();
    }
}
