package com.jasmine.jasmine_core.Intergation.FSCA;

import com.jasmine.jasmine_core.Connectors.MQTT.JNJSONMQTTSource;
import com.jasmine.jasmine_core.Connectors.MQTT.MQTTConnector;

public class FSCACellStatisticsSource extends JNJSONMQTTSource<FSCACellStatistics> {
    public FSCACellStatisticsSource(MQTTConnector connector, String topic) {
        super(connector, topic, FSCACellStatistics.class);
    }
}
