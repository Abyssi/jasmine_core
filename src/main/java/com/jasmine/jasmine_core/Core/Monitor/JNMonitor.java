package com.jasmine.jasmine_core.Core.Monitor;

import com.jasmine.jasmine_core.Connectors.JNClusterRedisConnector;
import com.jasmine.jasmine_core.Connectors.MQTT.JNJSONMQTTSink;
import com.jasmine.jasmine_core.Connectors.MQTT.JNJSONMQTTSource;
import com.jasmine.jasmine_core.Connectors.MQTT.MQTTConnector;
import com.jasmine.jasmine_core.Connectors.MQTT.MQTTSink;
import com.jasmine.jasmine_core.Core.StreamEnvironments.JNStreamExecutionEnvironment;
import com.jasmine.jasmine_core.Intergation.FSCA.FSCAMonitor;
import com.jasmine.jasmine_core.Intergation.FSCA.FSCATest;
import com.jasmine.jasmine_core.Intergation.FSCA.FSCAWrappedCoordinates;
import com.jasmine.jasmine_core.Intergation.FSCA.JNSemaphoreRouteListToFSCAWrappedCoordinatesFlatMapFunction;
import com.jasmine.jasmine_core.Intergation.Masaccio.*;
import com.jasmine.jasmine_core.Models.JNCrossroads;
import com.jasmine.jasmine_core.Models.JNDamagedSemaphore;
import com.jasmine.jasmine_core.Models.JNSemaphoreRoute;
import com.jasmine.jasmine_core.StreamFunctions.SinkFunctions.JNHSetRedisSinkFunction;
import com.jasmine.jasmine_core.StreamFunctions.SinkFunctions.JNJSONKafkaSinkFunction;
import com.jasmine.jasmine_core.StreamFunctions.SinkFunctions.RedisKeySelectors.JNRedisCrossroadsIdKeySelector;
import com.jasmine.jasmine_core.StreamFunctions.SinkFunctions.RedisKeySelectors.JNRedisDamagedSemaphoreCompoundIdKeySelector;
import com.jasmine.jasmine_core.StreamFunctions.SinkFunctions.RedisKeySelectors.JNRedisSemaphoreRouteIdKeySelector;
import com.jasmine.jasmine_core.StreamFunctions.SinkFunctions.RedisKeySelectors.JNRedisStaticKeySelector;
import com.jasmine.jasmine_core.Utils.FlinkParameters;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class JNMonitor {

    public static void main(String[] args) throws Exception {
        ParameterTool parameterTool = FlinkParameters.getParametersWithArgs(args);

        StreamExecutionEnvironment environment = JNStreamExecutionEnvironment.getExecutionEnvironment();

        Properties kafkaProperties = new Properties();
        kafkaProperties.setProperty("bootstrap.servers", parameterTool.get("flink.kafka.bootstrap-servers", "localhost:9092"));
        kafkaProperties.setProperty("group.id", parameterTool.get("flink.kafka.consumer.group-id", "core_monitor_group"));

        JNClusterRedisConnector redisConnector = parameterTool.getBoolean("use.redis.sink", false) ? new JNClusterRedisConnector(Collections.singletonList(new InetSocketAddress(parameterTool.get("flink.redis.host", "localhost"), parameterTool.getInt("flink.redis.port", 6379)))) : null;

        MQTTConnector mqttConnector = parameterTool.getBoolean("masaccio.integration.enabled", false) || parameterTool.getBoolean("fsca.integration.enabled", false) ? new MQTTConnector(parameterTool.get("external.mqtt.broker.host", "193.206.52.98"), parameterTool.getInt("external.mqtt.broker.port", 1883)) : null;

        new JNJSONMQTTSource<>(mqttConnector, "test", JNDamagedSemaphore.class);

        new JNSemaphoreMonitor(parameterTool.get("kafka.semaphore.topic", "semaphore-topic"), kafkaProperties) {
            @Override
            public void multipleOutput(DataStream<List<JNCrossroads>> top10CrossroadsStream, DataStream<JNCrossroads> biggerThanMedianCrossroadsStream, Time timeWindow) {
                super.multipleOutput(top10CrossroadsStream, biggerThanMedianCrossroadsStream, timeWindow);
                String key = String.valueOf(timeWindow.toMilliseconds());

                // Send to kafka
                top10CrossroadsStream.addSink(new JNJSONKafkaSinkFunction<>(parameterTool.get("kafka.top.ten.crossroads.topic", "top-ten-crossroads-") + key + parameterTool.get("kafka.topic.suffix", "-topic"), kafkaProperties, List.class)).name("JNJSONKafkaSinkFunction");
                biggerThanMedianCrossroadsStream.addSink(new JNJSONKafkaSinkFunction<>(parameterTool.get("kafka.outlier.crossroads.topic", "outlier-crossroads-") + key + parameterTool.get("kafka.topic.suffix", "-topic"), kafkaProperties, JNCrossroads.class)).name("JNJSONKafkaSinkFunction");

                if (parameterTool.getBoolean("use.redis.sink", false) && redisConnector != null) {
                    top10CrossroadsStream.addSink(new JNHSetRedisSinkFunction<>(redisConnector.getConfig(), "topCrossroads", new JNRedisStaticKeySelector(key))).name(String.format("JNSetRedisSinkFunction(topCrossroads-%s)", key));
                    biggerThanMedianCrossroadsStream.addSink(new JNHSetRedisSinkFunction<>(redisConnector.getConfig(), "biggerThanMedianCrossroads", new JNRedisCrossroadsIdKeySelector())).name("JNSetRedisSinkFunction(biggerThanMedianCrossroads-JNRedisCrossroadsIdKeySelector)");
                }

                // Send to mqtt
                if (parameterTool.getBoolean("masaccio.integration.enabled", false) && mqttConnector != null) {
                    top10CrossroadsStream.flatMap(new JNFirstElementInListExtractor())
                            .map(new JNCrossroadsAverageSpeedToMasaccioMessageMapFunction()).addSink(new MQTTSink<>(mqttConnector, parameterTool.get("masaccio.mqtt.crossroads.average.speed.topic", "area/2/monitoring/velocita_avg"), new MasaccioSerializer()));
                    biggerThanMedianCrossroadsStream.map(new JNCrossroadsVehiclesCountToMasaccioMessageMapFunction()).addSink(new MQTTSink<>(mqttConnector, parameterTool.get("masaccio.mqtt.average.vehicles.count.topic", "area/2/monitoring/veicoli_avg"), new MasaccioSerializer()));
                }
            }

            @Override
            public void singleOutput(DataStream<JNDamagedSemaphore> damagedSemaphoreStream) {
                super.singleOutput(damagedSemaphoreStream);

                // Send to kafka
                damagedSemaphoreStream.addSink(new JNJSONKafkaSinkFunction<>(parameterTool.get("kafka.damaged.semaphore.topic", "damaged-semaphore") + parameterTool.get("kafka.topic.suffix", "-topic"), kafkaProperties, JNDamagedSemaphore.class)).name("JNJSONKafkaSinkFunction");

                if (parameterTool.getBoolean("use.redis.sink", false) && redisConnector != null)
                    damagedSemaphoreStream.addSink(new JNHSetRedisSinkFunction<>(redisConnector.getConfig(), "damagedSemaphore", new JNRedisDamagedSemaphoreCompoundIdKeySelector())).name("JNSetRedisSinkFunction(damagedSemaphore-JNRedisDamagedSemaphoreCompoundIdKeySelector)");

                // Send to mqtt
                if (parameterTool.getBoolean("masaccio.integration.enabled", false) && mqttConnector != null)
                    damagedSemaphoreStream.map(new JNDamagedSemaphoreToMasaccioMessageMapFunction()).setParallelism(1).addSink(new MQTTSink<>(mqttConnector, parameterTool.get("masaccio.mqtt.damaged.semaphores.topic", "area/2/monitoring/luce_semaforo"), new MasaccioSerializer())).setParallelism(1);

                damagedSemaphoreStream.flatMap(new FSCATest()).addSink(new JNJSONMQTTSink<>(mqttConnector, parameterTool.get("fsca.mqtt.cell.stats.output.topic", "jasmine/input"), FSCAWrappedCoordinates.class));
            }
        }.addToEnvironment(environment);

        new JNMobileMonitor(parameterTool.get("kafka.mobile.topic", "mobile-topic"), kafkaProperties) {
            @Override
            public void output(DataStream<List<JNSemaphoreRoute>> topSemaphoreRouteStream) {
                super.output(topSemaphoreRouteStream);

                // Send to kafka
                topSemaphoreRouteStream.addSink(new JNJSONKafkaSinkFunction<>(parameterTool.get("kafka.top.semaphore.route.topic", "top-semaphore-route") + parameterTool.get("kafka.topic.suffix", "-topic"), kafkaProperties, List.class)).name("JNJSONKafkaSinkFunction");

                if (parameterTool.getBoolean("use.redis.sink", false) && redisConnector != null)
                    topSemaphoreRouteStream.addSink(new JNHSetRedisSinkFunction<>(redisConnector.getConfig(), "topSemaphoreRoute", new JNRedisSemaphoreRouteIdKeySelector())).name("JNSetRedisSinkFunction(bottomSemaphoreRoute-JNRedisSemaphoreRouteIdKeySelector)");

                if (parameterTool.getBoolean("fsca.integration.enabled", false) && mqttConnector != null)
                    topSemaphoreRouteStream.flatMap(new JNSemaphoreRouteListToFSCAWrappedCoordinatesFlatMapFunction()).addSink(new JNJSONMQTTSink<>(mqttConnector, parameterTool.get("fsca.mqtt.cell.stats.output.topic", "jasmine/input/"), FSCAWrappedCoordinates.class));
            }
        }.addToEnvironment(environment);

        if (parameterTool.getBoolean("fsca.integration.enabled", false) && mqttConnector != null)
            new FSCAMonitor(mqttConnector).addToEnvironment(environment);

        environment.execute("JASMINE Monitor");
    }


}
