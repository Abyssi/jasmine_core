package com.jasmine.jasmine_core.Core.Controller;

import com.jasmine.jasmine_core.Core.StreamEnvironments.JNStreamExecutionEnvironment;
import com.jasmine.jasmine_core.Models.JNSemaphoreControllerMessage;
import com.jasmine.jasmine_core.StreamFunctions.SinkFunctions.JNJSONKafkaSinkFunction;
import com.jasmine.jasmine_core.Utils.FlinkParameters;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Properties;

public class JNController {

    public static void main(String[] args) throws Exception {
        ParameterTool parameterTool = FlinkParameters.getParametersWithArgs(args);

        StreamExecutionEnvironment environment = JNStreamExecutionEnvironment.getExecutionEnvironment();

        Properties kafkaProperties = new Properties();
        kafkaProperties.setProperty("bootstrap.servers", parameterTool.get("flink.kafka.bootstrap-servers", "localhost:9092"));
        kafkaProperties.setProperty("group.id", parameterTool.get("flink.kafka.consumer.group-id", "core_monitor_group"));

        new JNSemaphoreController(parameterTool.get("kafka.semaphore.topic", "semaphore-topic"), kafkaProperties) {
            @Override
            public void output(DataStream<JNSemaphoreControllerMessage> semaphoreControllerMessageStream) {
                super.output(semaphoreControllerMessageStream);
                // Send to kafka
                semaphoreControllerMessageStream.addSink(new JNJSONKafkaSinkFunction<>(parameterTool.get("kafka.semaphore.controller.topic", "semaphore-controller") + parameterTool.get("kafka.topic.suffix", "-topic"), kafkaProperties, JNSemaphoreControllerMessage.class)).name("JNJSONKafkaSinkFunction");
            }
        }.addToEnvironment(environment);

        environment.execute("JASMINE Controller");
    }
}
