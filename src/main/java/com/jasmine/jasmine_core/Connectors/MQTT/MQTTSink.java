package com.jasmine.jasmine_core.Connectors.MQTT;

import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

public class MQTTSink<T> extends RichSinkFunction<T> {
    private MQTTConnector connector;
    private String topic;
    private SerializationSchema<T> valueSerializer;

    public MQTTSink(MQTTConnector connector, String topic, SerializationSchema<T> valueSerializer) {
        this.connector = connector;
        this.topic = topic;
        this.valueSerializer = valueSerializer;
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        connector.connect();
    }

    @Override
    public void close() throws Exception {
        super.close();
        connector.close();
    }

    @Override
    public void invoke(T value, Context context) throws Exception {
        connector.publish(topic, valueSerializer.serialize(value));
    }
}
