package com.jasmine.jasmine_core.Connectors;

import com.jasmine.jasmine_core.Connectors.Abstracts.JNKafkaConnector;
import com.jasmine.jasmine_core.Connectors.Messages.JNJSONSerializableSchema;
import com.jasmine.jasmine_core.Connectors.Messages.JNSemaphoreMessage;

import java.util.Properties;

public class JNSemaphoreConnector extends JNKafkaConnector<JNSemaphoreMessage> {

    public JNSemaphoreConnector(String topic, Properties properties) {
        super(new JNJSONSerializableSchema<>(JNSemaphoreMessage.class), topic, properties);
    }

    /* Nameable */

    @Override
    public String getName() {
        return "JNSemaphoreConnector";
    }

}
