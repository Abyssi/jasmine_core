package com.jasmine.jasmine_core.Core.Controller;

import com.jasmine.jasmine_core.Connectors.JNSemaphoreConnector;
import com.jasmine.jasmine_core.Connectors.Messages.JNSemaphoreMessage;
import com.jasmine.jasmine_core.Models.JNBaseSemaphoreMessage;
import com.jasmine.jasmine_core.Models.JNDetailedCrossroads;
import com.jasmine.jasmine_core.Models.JNSemaphoreControllerMessage;
import com.jasmine.jasmine_core.StreamFunctions.AggregateFunctions.JNBaseSemaphoreMessageToDetailedCrossroadsAggregateFunction;
import com.jasmine.jasmine_core.StreamFunctions.KeySelectors.JNBaseSemaphoreMessageCrossroadsIdKeySelector;
import com.jasmine.jasmine_core.StreamFunctions.MapFunctions.JNSemaphoreMessageToBaseSemaphoreMessageMapFunction;
import com.jasmine.jasmine_core.StreamFunctions.ProcessFunctions.JNDetailedCrossroadsToSemaphoreControllerMessageProcessFunction;
import com.jasmine.jasmine_core.StreamFunctions.SinkFunctions.JsonPrintSinkFunction;
import com.jasmine.jasmine_core.StreamFunctions.TimestampExtractors.JNBaseSemaphoreMessageTimestampExtractor;
import com.jasmine.jasmine_core.Utils.FlinkParameters;
import com.jasmine.jasmine_core.Utils.MetricsMapper;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;

import java.util.Properties;

public class JNSemaphoreController extends JNSemaphoreConnector {

    public JNSemaphoreController(String topic, Properties properties) {
        super(topic, properties);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void configure(DataStream<JNSemaphoreMessage> dataStream) {
        //Map semaphore message to base semaphore message
        DataStream<JNBaseSemaphoreMessage> baseSemaphoreMessageStream = MetricsMapper.wrap(dataStream.map(new JNSemaphoreMessageToBaseSemaphoreMessageMapFunction()).name("JNSemaphoreMessageToBaseSemaphoreMessageMapFunction"));
        // Assign timestamps to messages
        DataStream<JNBaseSemaphoreMessage> timestampedBaseSemaphoreMessageStream = MetricsMapper.wrap(baseSemaphoreMessageStream.assignTimestampsAndWatermarks(new JNBaseSemaphoreMessageTimestampExtractor()).name("JNBaseSemaphoreMessageTimestampExtractor"));
        // Key by the crossroads id
        KeyedStream<JNBaseSemaphoreMessage, String> keyedTimestampedBaseSemaphoreMessageStream = timestampedBaseSemaphoreMessageStream.keyBy(new JNBaseSemaphoreMessageCrossroadsIdKeySelector());

        // Assign time windows
        Time[] timeWindow = {Time.milliseconds(FlinkParameters.getParameters().getLong("kafka.controller.window", 300000)), Time.milliseconds(FlinkParameters.getParameters().getLong("kafka.controller.window.slide", 0))};
        // Assign sliding windows
        WindowedStream<JNBaseSemaphoreMessage, String, TimeWindow> windowedKeyedTimestampedBaseSemaphoreMessageStream = timeWindow[1].toMilliseconds() == 0 ? keyedTimestampedBaseSemaphoreMessageStream.timeWindow(timeWindow[0]) : keyedTimestampedBaseSemaphoreMessageStream.timeWindow(timeWindow[0], timeWindow[1]);
        // Reduce keyed semaphore message to crossroads
        DataStream<JNDetailedCrossroads> detailedCrossroadsStream = MetricsMapper.wrap(windowedKeyedTimestampedBaseSemaphoreMessageStream.aggregate(new JNBaseSemaphoreMessageToDetailedCrossroadsAggregateFunction()).name("JNBaseSemaphoreMessageToDetailedCrossroadsAggregateFunction"));

        // Apply function to get eventual message to send
        DataStream<JNSemaphoreControllerMessage> semaphoreControllerMessageStream = MetricsMapper.wrap(detailedCrossroadsStream.process(new JNDetailedCrossroadsToSemaphoreControllerMessageProcessFunction()).name("JNDetailedCrossroadsToSemaphoreControllerMessageProcessFunction"));

        this.output(semaphoreControllerMessageStream);
    }

    public void output(DataStream<JNSemaphoreControllerMessage> semaphoreControllerMessageStream) {
        if (FlinkParameters.getParameters().getBoolean("use.log.sink", false)) {
            // Print for debugging
            semaphoreControllerMessageStream.addSink(new JsonPrintSinkFunction<>("semaphoreControllerMessageStream")).name("JsonPrintSinkFunction");
        }
    }

    /* Nameable */

    @Override
    public String getName() {
        return "JNSemaphoreController";
    }
}
