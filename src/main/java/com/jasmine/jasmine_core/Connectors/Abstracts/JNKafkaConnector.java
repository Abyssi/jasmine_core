package com.jasmine.jasmine_core.Connectors.Abstracts;

import com.jasmine.jasmine_core.Utils.Nameable;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;

import java.util.Properties;

/**
 * Flink Kafka connector
 */
public abstract class JNKafkaConnector<T> implements Nameable {

    private FlinkKafkaConsumer011 consumer;
    private StreamExecutionEnvironment attachedStreamExecutionEnvironment;
    private DataStream dataStream;

    @SuppressWarnings("unchecked")
    public JNKafkaConnector(DeserializationSchema deserializationSchema, String topic, Properties properties) {
        this.consumer = new FlinkKafkaConsumer011<JNKafkaConnector>(topic, deserializationSchema, properties);
        this.consumer.setCommitOffsetsOnCheckpoints(true);
        this.consumer.setStartFromLatest();
    }

    @SuppressWarnings("unchecked")
    public void addToEnvironment(StreamExecutionEnvironment streamExecutionEnvironment) {
        this.attachedStreamExecutionEnvironment = streamExecutionEnvironment;
        this.dataStream = streamExecutionEnvironment.addSource(this.consumer).name(this.getName());
        configure(this.dataStream);
    }

    protected void configure(DataStream<T> dataStream) {
        dataStream.print();
    }

    /*
        Getter and Setter
     */

    public FlinkKafkaConsumer011 getConsumer() {
        return this.consumer;
    }

    public StreamExecutionEnvironment getAttachedStreamExecutionEnvironment() {
        return this.attachedStreamExecutionEnvironment;
    }

    public DataStream getDataStream() {
        return this.dataStream;
    }

    /* Nameable */

    @Override
    public String getName() {
        return "JNKafkaConnector";
    }

}
