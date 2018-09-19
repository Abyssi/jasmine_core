package com.jasmine.jasmine_core.Intergation;

import com.jasmine.jasmine_core.Models.JNDamagedSemaphore;
import org.apache.flink.api.common.functions.MapFunction;

import java.time.Instant;

public class JNDamagedSemaphoreToMasaccioMessageMapFunction implements MapFunction<JNDamagedSemaphore, MasaccioMessage> {
    private static final long serialVersionUID = 1L;

    @Override
    public MasaccioMessage map(JNDamagedSemaphore damagedSemaphore) throws Exception {
        //return new MasaccioMessage(damagedSemaphore.getId().replaceAll("[^0-9]", ""), "1", "1", Instant.now());
        return new MasaccioMessage("10", "", "1", Instant.now());
    }
}
