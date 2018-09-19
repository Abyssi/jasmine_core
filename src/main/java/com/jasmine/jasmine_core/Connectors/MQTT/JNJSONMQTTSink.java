package com.jasmine.jasmine_core.Connectors.MQTT;

import com.jasmine.jasmine_core.Connectors.Messages.JNJSONSerializableSchema;

public class JNJSONMQTTSink<T> extends MQTTSink<T> {

    public JNJSONMQTTSink(MQTTConnector connector, String topic, Class<T> valueClass) {
        super(connector, topic, new JNJSONSerializableSchema<>(valueClass));
    }
}
