package com.jasmine.jasmine_core.StreamFunctions.MapFunctions;

import com.jasmine.jasmine_core.Connectors.Messages.JNSemaphoreMessage;
import com.jasmine.jasmine_core.Models.JNDamagedSemaphore;
import com.jasmine.jasmine_core.Models.JNLightBulb;
import com.jasmine.jasmine_core.Models.JNLightBulbStatus;
import org.apache.flink.api.common.functions.MapFunction;

import java.util.ArrayList;
import java.util.List;

public class JNSemaphoreMessageToJNDamagedSemaphoreMapFunction implements MapFunction<JNSemaphoreMessage, JNDamagedSemaphore> {
    private static final long serialVersionUID = 1L;

    @Override
    public JNDamagedSemaphore map(JNSemaphoreMessage semaphoreMessage) {
        List<JNLightBulb> damagedLightBulbs = new ArrayList<>();
        for (JNLightBulb lightBulb : semaphoreMessage.getLightBulbs())
            if (lightBulb.getStatus() == JNLightBulbStatus.DAMAGED)
                damagedLightBulbs.add(lightBulb);

        return new JNDamagedSemaphore(semaphoreMessage.getCrossroadsId(), semaphoreMessage.getSemaphoreId(), semaphoreMessage.getPosition(), damagedLightBulbs);
    }
}
