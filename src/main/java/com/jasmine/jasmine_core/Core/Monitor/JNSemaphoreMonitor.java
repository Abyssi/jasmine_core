package com.jasmine.jasmine_core.Core.Monitor;

import com.jasmine.jasmine_core.Connectors.JNSemaphoreConnector;
import com.jasmine.jasmine_core.Connectors.Messages.JNSemaphoreMessage;
import com.jasmine.jasmine_core.Models.*;
import com.jasmine.jasmine_core.StreamFunctions.AggregateFunctions.*;
import com.jasmine.jasmine_core.StreamFunctions.FilterFunctions.JNNotChangedFilter;
import com.jasmine.jasmine_core.StreamFunctions.FilterFunctions.JNSemaphoreMessagesDamagedFilter;
import com.jasmine.jasmine_core.StreamFunctions.FlatMapFunctions.JNBiggerThanMedianCrossroadsCoFlatMapFunction;
import com.jasmine.jasmine_core.StreamFunctions.KeySelectors.JNAggregabileCrossroadsIdKeySelector;
import com.jasmine.jasmine_core.StreamFunctions.KeySelectors.JNBaseSemaphoreMessageCrossroadsIdKeySelector;
import com.jasmine.jasmine_core.StreamFunctions.KeySelectors.JNIdentifiedIdKeySelector;
import com.jasmine.jasmine_core.StreamFunctions.MapFunctions.*;
import com.jasmine.jasmine_core.StreamFunctions.ReduceFunctions.JNLeaderboardReduceFunction;
import com.jasmine.jasmine_core.StreamFunctions.ReduceFunctions.JNMedianReduceFunction;
import com.jasmine.jasmine_core.StreamFunctions.SinkFunctions.JsonPrintSinkFunction;
import com.jasmine.jasmine_core.StreamFunctions.TimestampExtractors.*;
import com.jasmine.jasmine_core.Utils.FlinkParameters;
import com.jasmine.jasmine_core.Utils.Identified;
import com.jasmine.jasmine_core.Utils.MetricsMapper;
import org.apache.flink.streaming.api.datastream.*;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;

import java.util.List;
import java.util.Properties;

public class JNSemaphoreMonitor extends JNSemaphoreConnector {

    public JNSemaphoreMonitor(String topic, Properties properties) {
        super(topic, properties);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void configure(DataStream<JNSemaphoreMessage> dataStream) {
        // Filter non damaged semaphores
        DataStream<JNSemaphoreMessage> damagedSemaphoreMessageFilteredStream = MetricsMapper.wrap(dataStream.filter(new JNSemaphoreMessagesDamagedFilter()).name("JNSemaphoreMessagesDamagedFilter"));
        // Map to damaged semaphore
        DataStream<JNDamagedSemaphore> damagedSemaphoreStream = MetricsMapper.wrap(damagedSemaphoreMessageFilteredStream.map(new JNSemaphoreMessageToJNDamagedSemaphoreMapFunction()).name("JNSemaphoreMessageToJNDamagedSemaphoreMapFunction"));
        // Process output
        this.singleOutput(damagedSemaphoreStream);

        //Map semaphore message to base semaphore message
        DataStream<JNBaseSemaphoreMessage> baseSemaphoreMessageStream = MetricsMapper.wrap(dataStream.map(new JNSemaphoreMessageToBaseSemaphoreMessageMapFunction()).name("JNSemaphoreMessageToBaseSemaphoreMessageMapFunction"));
        // Assign timestamps to messages
        DataStream<JNBaseSemaphoreMessage> timestampedBaseSemaphoreMessageStream = MetricsMapper.wrap(baseSemaphoreMessageStream.assignTimestampsAndWatermarks(new JNBaseSemaphoreMessageTimestampExtractor()).name("JNBaseSemaphoreMessageTimestampExtractor"));
        // Key by the crossroads id
        KeyedStream<JNBaseSemaphoreMessage, String> keyedTimestampedBaseSemaphoreMessageStream = timestampedBaseSemaphoreMessageStream.keyBy(new JNBaseSemaphoreMessageCrossroadsIdKeySelector());

        // Assign time windows
        Time[][] timeWindows = {
                {Time.milliseconds(FlinkParameters.getParameters().getLong("kafka.small.window", 900000)), Time.milliseconds(FlinkParameters.getParameters().getLong("kafka.small.window.slide", 0))},
                {Time.milliseconds(FlinkParameters.getParameters().getLong("kafka.medium.window", 3600000)), Time.milliseconds(FlinkParameters.getParameters().getLong("kafka.medium.window.slide", 0))},
                {Time.milliseconds(FlinkParameters.getParameters().getLong("kafka.large.window", 86400000)), Time.milliseconds(FlinkParameters.getParameters().getLong("kafka.large.window.slide", 0))}
        };

        DataStream<JNAggregabileCrossroads> currentAggregabileCrossroadsStream = null;

        for (Time[] timeWindow : timeWindows) {
            DataStream<JNCrossroads> crossroadsStream;
            if (FlinkParameters.getParameters().getBoolean("use.window.chaining", false)) {
                if (currentAggregabileCrossroadsStream == null || FlinkParameters.getParameters().getBoolean("use.window.chaining", false)) {
                    // Assign the pre-aggregation window
                    WindowedStream<JNBaseSemaphoreMessage, String, TimeWindow> windowedKeyedTimestampedBaseSemaphoreMessageStream = timeWindow[1].toMilliseconds() == 0 ? keyedTimestampedBaseSemaphoreMessageStream.timeWindow(timeWindow[0]) : keyedTimestampedBaseSemaphoreMessageStream.timeWindow(timeWindow[0], timeWindow[1]);
                    // Reduce keyed semaphore message to crossroads
                    currentAggregabileCrossroadsStream = MetricsMapper.wrap(windowedKeyedTimestampedBaseSemaphoreMessageStream.aggregate(new JNBaseSemaphoreMessageToAggregabileCrossroadsAggregateFunction()).name("JNBaseSemaphoreMessageToAggregabileCrossroadsAggregateFunction"));
                } else {
                    // Assign timestamps to messages
                    DataStream<JNAggregabileCrossroads> timestampedAggregabileCrossroadsStream = MetricsMapper.wrap(currentAggregabileCrossroadsStream.assignTimestampsAndWatermarks(new JNAggregabileCrossroadsTimestampExtractor()).name("JNAggregabileCrossroadsTimestampExtractor"));
                    // Key by the crossroads id
                    KeyedStream<JNAggregabileCrossroads, String> keyedTimestampedAggregabileCrossroadsStream = timestampedAggregabileCrossroadsStream.keyBy(new JNAggregabileCrossroadsIdKeySelector());
                    // Assign the pre-aggregation window
                    WindowedStream<JNAggregabileCrossroads, String, TimeWindow> windowedKeyedTimestampedBaseSemaphoreMessageStream = timeWindow[1].toMilliseconds() == 0 ? keyedTimestampedAggregabileCrossroadsStream.timeWindow(timeWindow[0]) : keyedTimestampedAggregabileCrossroadsStream.timeWindow(timeWindow[0], timeWindow[1]);
                    // Reduce keyed semaphore message to crossroads
                    currentAggregabileCrossroadsStream = MetricsMapper.wrap(windowedKeyedTimestampedBaseSemaphoreMessageStream.aggregate(new JNAggregabileCrossroadsAggregateFunction()).name("JNAggregabileCrossroadsAggregateFunction"));
                }
                crossroadsStream = MetricsMapper.wrap(currentAggregabileCrossroadsStream.map(new JNAggregabileCrossroadsToCrossroadsMapFunction()).name("JNAggregabileCrossroadsToCrossroadsMapFunction"));
            } else {
                // Assign the pre-aggregation window
                WindowedStream<JNBaseSemaphoreMessage, String, TimeWindow> windowedKeyedTimestampedBaseSemaphoreMessageStream = timeWindow[1].toMilliseconds() == 0 ? keyedTimestampedBaseSemaphoreMessageStream.timeWindow(timeWindow[0]) : keyedTimestampedBaseSemaphoreMessageStream.timeWindow(timeWindow[0], timeWindow[1]);
                // Reduce keyed semaphore message to crossroads
                crossroadsStream = MetricsMapper.wrap(windowedKeyedTimestampedBaseSemaphoreMessageStream.aggregate(new JNBaseSemaphoreMessageToCrossroadsAggregateFunction()).name("JNBaseSemaphoreMessageToCrossroadsAggregateFunction"));
            }

            // Assign timestamps to crossroads
            DataStream<JNCrossroads> timestampedCrossroadsStream = MetricsMapper.wrap(crossroadsStream.assignTimestampsAndWatermarks(new JNCrossroadsTimestampExtractor()).name("JNCrossroadsTimestampExtractor"));
            // Key by random id
            KeyedStream<Identified<JNCrossroads>, String> keyedTimestampedCrossroadsStream = timestampedCrossroadsStream.map(new JNObjectToRandomIdentifiedMapFunction(FlinkParameters.getParameters().getInt("flink.parallelism", 4))).name("JNObjectToRandomIdentifiedMapFunction").keyBy(new JNIdentifiedIdKeySelector());
            // Assign sliding windows
            WindowedStream<Identified<JNCrossroads>, String, TimeWindow> windowedKeyedTimestampedCrossroadsStream = timeWindow[1].toMilliseconds() == 0 ? keyedTimestampedCrossroadsStream.timeWindow(timeWindow[0]) : keyedTimestampedCrossroadsStream.timeWindow(timeWindow[0], timeWindow[1]);

            // Compute partial top 10
            DataStream<JNCrossroadsLeaderboard> partialTop10CrossroadsLeaderboardStream = MetricsMapper.wrap(windowedKeyedTimestampedCrossroadsStream.aggregate(new JNTopCrossroadsLeaderboardAggregateFunction(10)).name("JNTopCrossroadsLeaderboardAggregateFunction(10)"));
            // Assign timestamps to crossroads
            DataStream<JNCrossroadsLeaderboard> timestampedPartialTop10CrossroadsLeaderboardStream = MetricsMapper.wrap(partialTop10CrossroadsLeaderboardStream.assignTimestampsAndWatermarks(new JNCrossroadsLeaderboardTimestampExtractor()).setParallelism(1).name("JNCrossroadsLeaderboardTimestampExtractor"));
            // Assign sliding windows
            AllWindowedStream<JNCrossroadsLeaderboard, TimeWindow> windowedTimestampedPartialTop10CrossroadsLeaderboardStream = timeWindow[1].toMilliseconds() == 0 ? timestampedPartialTop10CrossroadsLeaderboardStream.timeWindowAll(timeWindow[0]) : timestampedPartialTop10CrossroadsLeaderboardStream.timeWindowAll(timeWindow[0], timeWindow[1]);
            // Compute final top 10
            DataStream<JNCrossroadsLeaderboard> top10CrossroadsLeaderboardStream = MetricsMapper.wrap(windowedTimestampedPartialTop10CrossroadsLeaderboardStream.reduce(new JNLeaderboardReduceFunction()).setParallelism(1).name("JNLeaderboardReduceFunction"));
            // Filter if is equal to last value
            DataStream<JNCrossroadsLeaderboard> filteredTop10CrossroadsLeaderboardStream = MetricsMapper.wrap(top10CrossroadsLeaderboardStream.filter(new JNNotChangedFilter<>()).name("JNNotChangedFilter"));
            // Map to a list
            DataStream<List<JNCrossroads>> top10CrossroadsStream = MetricsMapper.wrap(filteredTop10CrossroadsLeaderboardStream.map(new JNLeaderboardToListMapFunction()).name("JNLeaderboardToListMapFunction"));

            // Compute partial median data
            DataStream<JNMedian> partialCrossroadsMedianDataStream = MetricsMapper.wrap(windowedKeyedTimestampedCrossroadsStream.aggregate(new JNCrossroadsMedianAggregateFunction()).name("JNCrossroadsMedianAggregateFunction"));
            // Assign timestamps to crossroads
            DataStream<JNMedian> timestampedPartialCrossroadsMedianDataStream = MetricsMapper.wrap(partialCrossroadsMedianDataStream.assignTimestampsAndWatermarks(new JNMedianTimestampExtractor()).setParallelism(1).name("JNMedianTimestampExtractor"));
            // Assign sliding windows
            AllWindowedStream<JNMedian, TimeWindow> windowedTimestampedPartialCrossroadsMedianDataStream = timeWindow[1].toMilliseconds() == 0 ? timestampedPartialCrossroadsMedianDataStream.timeWindowAll(timeWindow[0]) : timestampedPartialCrossroadsMedianDataStream.timeWindowAll(timeWindow[0], timeWindow[1]);
            // Compute final median struct
            DataStream<JNMedian> crossroadsMedianStructStream = MetricsMapper.wrap(windowedTimestampedPartialCrossroadsMedianDataStream.reduce(new JNMedianReduceFunction()).setParallelism(1).name("JNMedianReduceFunction"));
            // Map to a double
            DataStream<Double> crossroadsMedianStream = MetricsMapper.wrap(crossroadsMedianStructStream.map(new JNMedianToDoubleMapFunction()).name("JNMedianToDoubleMapFunction"));

            // Connect crossroads stream with median stream
            ConnectedStreams<Double, JNCrossroads> medianAndCrossroadsConnectedStream = crossroadsMedianStream.connect(crossroadsStream);
            // Filter out all crossroads with median < global median
            DataStream<JNCrossroads> biggerThanMedianCrossroadsStream = MetricsMapper.wrap(medianAndCrossroadsConnectedStream.flatMap(new JNBiggerThanMedianCrossroadsCoFlatMapFunction()).name("JNBiggerThanMedianCrossroadsCoFlatMapFunction"));

            // Process output
            this.multipleOutput(top10CrossroadsStream, biggerThanMedianCrossroadsStream, timeWindow[0]);

        }
    }

    public void multipleOutput(DataStream<List<JNCrossroads>> top10CrossroadsStream, DataStream<JNCrossroads> biggerThanMedianCrossroadsStream, Time timeWindow) {
        String key = String.valueOf(timeWindow.toMilliseconds());

        if (FlinkParameters.getParameters().getBoolean("use.log.sink", false)) {
            // Print for debugging
            top10CrossroadsStream.addSink(new JsonPrintSinkFunction<>("top10CrossroadsStream" + "-" + key)).name("JsonPrintSinkFunction");
            biggerThanMedianCrossroadsStream.addSink(new JsonPrintSinkFunction<>("biggerThanMedianCrossroadsStream" + "-" + key)).name("JsonPrintSinkFunction");
        }
    }

    public void singleOutput(DataStream<JNDamagedSemaphore> damagedSemaphoreStream) {
        if (FlinkParameters.getParameters().getBoolean("use.log.sink", false)) {
            // Print for debugging
            damagedSemaphoreStream.addSink(new JsonPrintSinkFunction<>("damagedSemaphoreStream")).name("JsonPrintSinkFunction");
        }
    }

    /* Nameable */

    @Override
    public String getName() {
        return "JNSemaphoreMonitor";
    }

}
