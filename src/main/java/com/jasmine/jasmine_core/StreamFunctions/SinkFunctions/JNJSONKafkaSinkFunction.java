package com.jasmine.jasmine_core.StreamFunctions.SinkFunctions;

import com.jasmine.jasmine_core.Connectors.Messages.JNJSONSerializableSchema;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;

import java.util.Properties;

public class JNJSONKafkaSinkFunction<T> extends FlinkKafkaProducer011<T> {
    @SuppressWarnings("unchecked")
    public JNJSONKafkaSinkFunction(String topic, Properties properties, Class<?> tClass) {
        super(topic, new JNJSONSerializableSchema<>((Class<T>) tClass), properties);
    }
}
