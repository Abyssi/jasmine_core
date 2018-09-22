package com.jasmine.jasmine_core.Intergation.FSCA;

import com.jasmine.jasmine_core.Connectors.MQTT.MQTTConnector;
import com.jasmine.jasmine_core.Utils.FlinkParameters;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class FSCAMonitor {

    private MQTTConnector connector;

    public FSCAMonitor(MQTTConnector connector) {
        this.connector = connector;
    }

    public void addToEnvironment(StreamExecutionEnvironment streamExecutionEnvironment) {
        configureAlarms(streamExecutionEnvironment.addSource(new FSCACellAlarmSource(this.connector, FlinkParameters.getParameters().get("fsca.mqtt.cell.alarm.topic", "fuoco/Jasmine/cellaIncendiata"))).name(""));
        configureEmergenceRoute(streamExecutionEnvironment.addSource(new FSCARouteSource(this.connector, FlinkParameters.getParameters().get("fsca.mqtt.cell.emergence.route.topic", "fuoco/Jasmine/percorso"))).name(""));
        configureCellStatistics(streamExecutionEnvironment.addSource(new FSCACellStatisticsSource(this.connector, FlinkParameters.getParameters().get("fsca.mqtt.cell.stats.input.topic", "jasmine/output/"))).name(""));
    }

    public void configureAlarms(DataStream<FSCACellAlarm> cellAlarmDataStream) {
        cellAlarmDataStream.print();
    }

    public void configureEmergenceRoute(DataStream<FSCARoute> emergenceRouteDataStream) {
        emergenceRouteDataStream.print();
    }

    public void configureCellStatistics(DataStream<FSCACellStatistics> cellStatisticsDataStream) {
        cellStatisticsDataStream.print();
    }
}
