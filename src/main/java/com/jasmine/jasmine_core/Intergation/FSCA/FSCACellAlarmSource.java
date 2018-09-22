package com.jasmine.jasmine_core.Intergation.FSCA;

import com.jasmine.jasmine_core.Connectors.MQTT.JNJSONMQTTSource;
import com.jasmine.jasmine_core.Connectors.MQTT.MQTTConnector;

public class FSCACellAlarmSource extends JNJSONMQTTSource<FSCACellAlarm> {
    public FSCACellAlarmSource(MQTTConnector connector, String topic) {
        super(connector, topic, FSCACellAlarm.class);
    }
}
