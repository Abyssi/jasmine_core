package com.jasmine.jasmine_core.Intergation.FSCA;

import com.jasmine.jasmine_core.Connectors.MQTT.JNJSONMQTTSource;
import com.jasmine.jasmine_core.Connectors.MQTT.MQTTConnector;

public class FSCARouteSource extends JNJSONMQTTSource<FSCARoute> {
    public FSCARouteSource(MQTTConnector connector, String topic) {
        super(connector, topic, FSCARoute.class);
    }
}
