package com.jasmine.jasmine_core.Core.Monitor;

import com.jasmine.jasmine_core.Connectors.JNMobileConnector;
import com.jasmine.jasmine_core.Connectors.Messages.JNMobileMessage;
import com.jasmine.jasmine_core.Models.JNMobileRoute;
import com.jasmine.jasmine_core.Models.JNSemaphoreRoute;
import com.jasmine.jasmine_core.Models.JNSemaphoreRouteLeaderboard;
import com.jasmine.jasmine_core.StreamFunctions.AggregateFunctions.JNLeaderboardAggregateFunction;
import com.jasmine.jasmine_core.StreamFunctions.AggregateFunctions.JNMobileMessageToMobileRouteAggregateFunction;
import com.jasmine.jasmine_core.StreamFunctions.AggregateFunctions.JNSemaphoreRouteAggregateFunction;
import com.jasmine.jasmine_core.StreamFunctions.AggregateFunctions.JNTopSemaphoreRouteLeaderboardAggregateFunction;
import com.jasmine.jasmine_core.StreamFunctions.FilterFunctions.JNNotChangedFilter;
import com.jasmine.jasmine_core.StreamFunctions.FlatMapFunctions.JNSemaphoreRouteSubSemaphoreRoutesFlatMapFunction;
import com.jasmine.jasmine_core.StreamFunctions.KeySelectors.JNMobileMessageIdKeySelector;
import com.jasmine.jasmine_core.StreamFunctions.KeySelectors.JNSemaphoreRouteIdKeySelector;
import com.jasmine.jasmine_core.StreamFunctions.MapFunctions.JNLeaderboardToListMapFunction;
import com.jasmine.jasmine_core.StreamFunctions.MapFunctions.JNMobileRouteToSemaphoreRouteMapFunction;
import com.jasmine.jasmine_core.StreamFunctions.SinkFunctions.JsonPrintSinkFunction;
import com.jasmine.jasmine_core.StreamFunctions.TimestampExtractors.JNMobileMessageTimestampExtractor;
import com.jasmine.jasmine_core.StreamFunctions.TimestampExtractors.JNSemaphoreRouteLeaderboardTimestampExtractor;
import com.jasmine.jasmine_core.StreamFunctions.TimestampExtractors.JNSemaphoreRouteTimestampExtractor;
import com.jasmine.jasmine_core.Utils.FlinkParameters;
import com.jasmine.jasmine_core.Utils.MetricsMapper;
import org.apache.flink.streaming.api.datastream.AllWindowedStream;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;

import java.util.List;
import java.util.Properties;

public class JNMobileMonitor extends JNMobileConnector {

    public JNMobileMonitor(String topic, Properties properties) {
        super(topic, properties);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void configure(DataStream<JNMobileMessage> dataStream) {
        Time[] timeWindow = {Time.milliseconds(FlinkParameters.getParameters().getLong("kafka.route.window", 300000)), Time.milliseconds(FlinkParameters.getParameters().getLong("kafka.route.window.slide", 60000))};

        // Assign timestamps to messages
        DataStream<JNMobileMessage> timestampedMobileMessageStream = MetricsMapper.wrap(dataStream.assignTimestampsAndWatermarks(new JNMobileMessageTimestampExtractor()).name("JNMobileMessageTimestampExtractor"));
        // Key by the
        KeyedStream<JNMobileMessage, String> keyedTimestampedMobileMessageStream = timestampedMobileMessageStream.keyBy(new JNMobileMessageIdKeySelector());
        // Assign sliding window
        WindowedStream<JNMobileMessage, String, TimeWindow> windowedKeyedTimestampedMobileMessageStream = timeWindow[1].toMilliseconds() == 0 ? keyedTimestampedMobileMessageStream.timeWindow(timeWindow[0]) : keyedTimestampedMobileMessageStream.timeWindow(timeWindow[0], timeWindow[1]);
        // Aggregate and retrieve mobile route
        DataStream<JNMobileRoute> mobileRouteStream = MetricsMapper.wrap(windowedKeyedTimestampedMobileMessageStream.aggregate(new JNMobileMessageToMobileRouteAggregateFunction()).name("JNMobileMessageToMobileRouteAggregateFunction"));
        //mobileRouteStream.print();
        // Map mobile route to semaphore route
        DataStream<JNSemaphoreRoute> semaphoreRouteStream = MetricsMapper.wrap(mobileRouteStream.map(new JNMobileRouteToSemaphoreRouteMapFunction()).name("JNMobileRouteToSemaphoreRouteMapFunction"));
        // Split in all sub semaphoreRoutes
        DataStream<JNSemaphoreRoute> subSemaphoreRouteStream = MetricsMapper.wrap(semaphoreRouteStream.flatMap(new JNSemaphoreRouteSubSemaphoreRoutesFlatMapFunction()).name("JNSemaphoreRouteSubSemaphoreRoutesFlatMapFunction"));
        // Assign timestamps to routes
        DataStream<JNSemaphoreRoute> timestampedSubSemaphoreRouteStream = MetricsMapper.wrap(subSemaphoreRouteStream.assignTimestampsAndWatermarks(new JNSemaphoreRouteTimestampExtractor()).name("JNSemaphoreRouteTimestampExtractor"));
        // Key by route id
        KeyedStream<JNSemaphoreRoute, String> keyedTimestampedSubSemaphoreRouteStream = timestampedSubSemaphoreRouteStream.keyBy(new JNSemaphoreRouteIdKeySelector());
        // Assign sliding window
        WindowedStream<JNSemaphoreRoute, String, TimeWindow> windowedKeyedTimestampedSubSemaphoreRouteStream = timeWindow[1].toMilliseconds() == 0 ? keyedTimestampedSubSemaphoreRouteStream.timeWindow(timeWindow[0]) : keyedTimestampedSubSemaphoreRouteStream.timeWindow(timeWindow[0], timeWindow[1]);
        // Reduce to single route
        DataStream<JNSemaphoreRoute> aggregatedSemaphoreRouteStream = MetricsMapper.wrap(windowedKeyedTimestampedSubSemaphoreRouteStream.aggregate(new JNSemaphoreRouteAggregateFunction()).name("JNSemaphoreRouteAggregateFunction"));
        // Assign timestamps to routes
        DataStream<JNSemaphoreRoute> timestampedSemaphoreRouteStream = MetricsMapper.wrap(aggregatedSemaphoreRouteStream.assignTimestampsAndWatermarks(new JNSemaphoreRouteTimestampExtractor()).name("JNSemaphoreRouteTimestampExtractor"));
        // Assign sliding window
        AllWindowedStream<JNSemaphoreRoute, TimeWindow> windowedTimestampedSemaphoreRouteStream = timeWindow[1].toMilliseconds() == 0 ? timestampedSemaphoreRouteStream.timeWindowAll(timeWindow[0]) : timestampedSemaphoreRouteStream.timeWindowAll(timeWindow[0], timeWindow[1]);

        // Compute partial bottom 1 descending
        DataStream<JNSemaphoreRouteLeaderboard> partialTopSemaphoreRouteLeaderboardStream = MetricsMapper.wrap(windowedTimestampedSemaphoreRouteStream.aggregate(new JNTopSemaphoreRouteLeaderboardAggregateFunction(1)).name("JNTopSemaphoreRouteLeaderboardAggregateFunction(1)"));
        // Assign timestamps to crossroads
        DataStream<JNSemaphoreRouteLeaderboard> timestampedPartialTopSemaphoreRouteLeaderboardStream = MetricsMapper.wrap(partialTopSemaphoreRouteLeaderboardStream.assignTimestampsAndWatermarks(new JNSemaphoreRouteLeaderboardTimestampExtractor()).name("JNSemaphoreRouteLeaderboardTimestampExtractor"));
        // Assign sliding windows
        AllWindowedStream<JNSemaphoreRouteLeaderboard, TimeWindow> windowedTimestampedPartialTopSemaphoreRouteLeaderboardStream = timeWindow[1].toMilliseconds() == 0 ? timestampedPartialTopSemaphoreRouteLeaderboardStream.timeWindowAll(timeWindow[0]) : timestampedPartialTopSemaphoreRouteLeaderboardStream.timeWindowAll(timeWindow[0], timeWindow[1]);
        // Compute final top 10
        DataStream<JNSemaphoreRouteLeaderboard> bottomSemaphoreRouteLeaderboardStream = MetricsMapper.wrap(windowedTimestampedPartialTopSemaphoreRouteLeaderboardStream.aggregate(new JNLeaderboardAggregateFunction()).setParallelism(1).name("JNLeaderboardAggregateFunction"));
        // Filter if is equal to last value
        DataStream<JNSemaphoreRouteLeaderboard> filteredTopSemaphoreRouteLeaderboardStream = MetricsMapper.wrap(bottomSemaphoreRouteLeaderboardStream.filter(new JNNotChangedFilter<>()).name("JNNotChangedFilter"));
        // Map to a list
        DataStream<List<JNSemaphoreRoute>> topSemaphoreRouteStream = MetricsMapper.wrap(filteredTopSemaphoreRouteLeaderboardStream.map(new JNLeaderboardToListMapFunction()).name("JNLeaderboardToListMapFunction"));

        this.output(topSemaphoreRouteStream);
    }

    public void output(DataStream<List<JNSemaphoreRoute>> bottomSemaphoreRouteStream) {
        if (FlinkParameters.getParameters().getBoolean("use.log.sink", false)) {
            // Print for debugging
            bottomSemaphoreRouteStream.addSink(new JsonPrintSinkFunction<>("topSemaphoreRouteStream")).name("JsonPrintSinkFunction");
        }
    }

    /* Nameable */

    @Override
    public String getName() {
        return "JNMobileMonitor";
    }
}
