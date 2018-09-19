package com.jasmine.jasmine_core.Connectors.MQTT;

import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MQTTSink<T> extends RichSinkFunction<T> {
    private transient MQTTConnector connector;
    private String topic;
    private SerializationSchema<T> valueSerializer;

    public MQTTSink(MQTTConnector connector, String topic, SerializationSchema<T> valueSerializer) {
        this.connector = connector;
        this.topic = topic;
        this.valueSerializer = valueSerializer;
    }

    @Override
    public void invoke(T value, Context context) throws Exception {
        connector.getClient().publish(topic, new MqttMessage(valueSerializer.serialize(value)));
    }
}