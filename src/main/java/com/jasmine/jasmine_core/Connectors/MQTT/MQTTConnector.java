package com.jasmine.jasmine_core.Connectors.MQTT;

import org.fusesource.mqtt.client.*;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;

public class MQTTConnector implements Serializable {

    // Required
    private String host;
    private int port;
    // Optional
    private String username;
    private String password;
    // Runtime
    private transient BlockingConnection blockingConnection;

    public MQTTConnector(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public MQTTConnector(String host, int port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public void connect() throws Exception {
        MQTT mqtt = new MQTT();
        mqtt.setHost(host, port);
        if (username != null) mqtt.setUserName(username);
        if (password != null) mqtt.setPassword(password);
        blockingConnection = mqtt.blockingConnection();
        blockingConnection.connect();
    }

    public void close() throws Exception {
        blockingConnection.disconnect();
    }

    public BlockingConnection getConnection() {
        return blockingConnection;
    }

    public void publish(String topic, byte[] payload) throws Exception {
        this.blockingConnection.publish(topic, payload, QoS.AT_LEAST_ONCE, false);
        System.out.println("MQTT Sent: (" + topic + ") " + new String(payload, StandardCharsets.ISO_8859_1));
    }

    public void subscribe(String topic, MQTTMessageListener listener) throws Exception {
        blockingConnection.subscribe(new Topic[]{new Topic(topic, QoS.AT_LEAST_ONCE)});
        while (blockingConnection.isConnected()) {
            Message message = blockingConnection.receive();
            System.out.println("MQTT Received: (" + topic + ") " + new String(message.getPayload(), StandardCharsets.ISO_8859_1));
            listener.messageArrived(message.getPayload());
            message.ack();
        }
        blockingConnection.disconnect();
    }

    public interface MQTTMessageListener {
        void messageArrived(byte[] payload) throws Exception;
    }
}
