package com.jasmine.jasmine_core.StreamFunctions.KeySelectors;

import com.jasmine.jasmine_core.Models.JNBaseSemaphoreMessage;
import org.apache.flink.api.java.functions.KeySelector;

public class JNBaseSemaphoreMessageCrossroadsIdKeySelector implements KeySelector<JNBaseSemaphoreMessage, String> {
    private static final long serialVersionUID = 1L;

    @Override
    public String getKey(JNBaseSemaphoreMessage baseSemaphoreMessage) {
        return baseSemaphoreMessage.getCrossroadsId();
    }
}
