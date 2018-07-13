package com.jasmine.jasmine_core.StreamFunctions.MapFunctions;

import com.jasmine.jasmine_core.Connectors.Messages.JNSemaphoreMessage;
import com.jasmine.jasmine_core.Models.JNBaseSemaphoreMessage;
import org.apache.flink.api.common.functions.MapFunction;

public class JNSemaphoreMessageToBaseSemaphoreMessageMapFunction implements MapFunction<JNSemaphoreMessage, JNBaseSemaphoreMessage> {
    private static final long serialVersionUID = 1L;

    @Override
    public JNBaseSemaphoreMessage map(JNSemaphoreMessage semaphoreMessage) {
        return new JNBaseSemaphoreMessage(semaphoreMessage.getCrossroadsId(), semaphoreMessage.getSemaphoreId(), semaphoreMessage.getPosition(), semaphoreMessage.getTimestamp(), semaphoreMessage.getVehiclesCount(), semaphoreMessage.getAverageSpeed());
    }
}