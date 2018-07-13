package com.jasmine.jasmine_core.StreamFunctions.KeySelectors;

import com.jasmine.jasmine_core.Connectors.Messages.JNMobileMessage;
import org.apache.flink.api.java.functions.KeySelector;

public class JNMobileMessageIdKeySelector implements KeySelector<JNMobileMessage, String> {
    private static final long serialVersionUID = 1L;

    @Override
    public String getKey(JNMobileMessage mobileMessage) {
        return mobileMessage.getId();
    }
}
