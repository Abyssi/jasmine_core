package com.jasmine.jasmine_core.StreamFunctions.AggregateFunctions;

import com.jasmine.jasmine_core.Models.JNLeaderboard;
import org.apache.flink.api.common.functions.AggregateFunction;

import java.util.Optional;

public class JNLeaderboardAggregateFunction<T> implements AggregateFunction<JNLeaderboard<T>, Optional<JNLeaderboard<T>>, JNLeaderboard<T>> {
    @Override
    public Optional<JNLeaderboard<T>> createAccumulator() {
        return Optional.empty();
    }

    @Override
    public Optional<JNLeaderboard<T>> add(JNLeaderboard<T> leaderboard, Optional<JNLeaderboard<T>> acc) {
        if (!acc.isPresent())
            return Optional.of(leaderboard);

        acc.get().merge(leaderboard);
        return acc;
    }

    @Override
    public JNLeaderboard<T> getResult(Optional<JNLeaderboard<T>> acc) {
        return acc.get();
    }

    @Override
    public Optional<JNLeaderboard<T>> merge(Optional<JNLeaderboard<T>> leaderboard, Optional<JNLeaderboard<T>> acc) {
        if (!acc.isPresent())
            return leaderboard;

        if (!leaderboard.isPresent())
            return acc;

        acc.get().merge(leaderboard.get());
        return acc;
    }
}
