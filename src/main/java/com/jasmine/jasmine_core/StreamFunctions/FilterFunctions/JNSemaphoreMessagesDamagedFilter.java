package com.jasmine.jasmine_core.StreamFunctions.FilterFunctions;

import com.jasmine.jasmine_core.Connectors.Messages.JNSemaphoreMessage;
import com.jasmine.jasmine_core.Models.JNLightBulb;
import com.jasmine.jasmine_core.Models.JNLightBulbStatus;
import org.apache.flink.api.common.functions.FilterFunction;

public class JNSemaphoreMessagesDamagedFilter implements FilterFunction<JNSemaphoreMessage> {
    private static final long serialVersionUID = 1L;

    @Override
    public boolean filter(JNSemaphoreMessage semaphoreMessage) {
        for (JNLightBulb lightBulb : semaphoreMessage.getLightBulbs())
            if (lightBulb.getStatus() == JNLightBulbStatus.DAMAGED)
                return true;
        return false;
    }
}