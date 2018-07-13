package com.jasmine.jasmine_core.StreamFunctions.SinkFunctions.RedisKeySelectors;

import java.io.Serializable;

public interface JNRedisKeySelector<IN> extends Serializable {
    String getKey(IN var1);
}

