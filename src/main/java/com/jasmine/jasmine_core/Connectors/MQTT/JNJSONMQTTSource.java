package com.jasmine.jasmine_core.Connectors.MQTT;

import com.jasmine.jasmine_core.Connectors.Messages.JNJSONSerializableSchema;

public class JNJSONMQTTSource<T> extends MQTTSource<T> {

    public JNJSONMQTTSource(MQTTConnector connector, String topic, Class<T> valueClass) {
        super(connector, topic, new JNJSONSerializableSchema<>(valueClass));
    }
}
