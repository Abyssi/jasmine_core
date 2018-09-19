package com.jasmine.jasmine_core.Connectors.MQTT;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.Serializable;

public class MQTTConnector implements Serializable {

    // Required
    private String url;
    private String clientId;

    // Optional
    private String username;
    private String password;

    // Runtime
    private transient MqttClient client;

    public MQTTConnector(String url) {
        this(url, MqttClient.generateClientId());
    }

    public MQTTConnector(String url, String username, String password) {
        this(url, MqttClient.generateClientId(), username, password);
    }

    public MQTTConnector(String url, String clientId) {
        this(url, clientId, null, null);
    }

    public MQTTConnector(String url, String clientId, String username, String password) {
        this.url = url;
        this.clientId = clientId;
        this.username = username;
        this.password = password;

        try {
            this.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void connect() throws Exception {
        MqttConnectOptions connectOptions = new MqttConnectOptions();

        if (username != null) connectOptions.setUserName(username);
        if (password != null) connectOptions.setPassword(password.toCharArray());

        client = new MqttClient(this.url, this.clientId);
        client.connect(connectOptions);
    }

    public void close() {
        try {
            if (client != null)
                client.disconnect();
        } catch (MqttException ignored) {
        }
    }

    public MqttClient getClient() {
        return client;
    }
}
