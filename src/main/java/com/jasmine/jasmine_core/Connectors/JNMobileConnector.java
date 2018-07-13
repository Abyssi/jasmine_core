package com.jasmine.jasmine_core.Connectors;

import com.jasmine.jasmine_core.Connectors.Abstracts.JNKafkaConnector;
import com.jasmine.jasmine_core.Connectors.Messages.JNJSONSerializableSchema;
import com.jasmine.jasmine_core.Connectors.Messages.JNMobileMessage;

import java.util.Properties;

public class JNMobileConnector extends JNKafkaConnector<JNMobileMessage> {

    public JNMobileConnector(String topic, Properties properties) {
        super(new JNJSONSerializableSchema<>(JNMobileMessage.class), topic, properties);
    }

    /* Nameable */

    @Override
    public String getName() {
        return "JNMobileConnector";
    }

}
