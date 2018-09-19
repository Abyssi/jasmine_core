package com.jasmine.jasmine_core.Connectors.MQTT;

import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;

public class MQTTSource<T> extends RichSourceFunction<T> {
    private transient MQTTConnector connector;
    private String topic;
    private DeserializationSchema<T> valueDeserializer;

    public MQTTSource(MQTTConnector connector, String topic, DeserializationSchema<T> valueDeserializer) {
        this.connector = connector;
        this.topic = topic;
        this.valueDeserializer = valueDeserializer;
    }

    @Override
    public void run(SourceContext<T> ctx) throws Exception {
        connector.getClient().subscribe(this.topic, (topic, message) -> ctx.collect(valueDeserializer.deserialize(message.getPayload())));
    }

    @Override
    public void cancel() {
        connector.close();
    }
}
