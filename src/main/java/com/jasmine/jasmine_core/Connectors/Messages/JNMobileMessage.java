package com.jasmine.jasmine_core.Connectors.Messages;

import com.jasmine.jasmine_core.Models.JNCoordinates;
import com.jasmine.jasmine_core.Utils.JSONSerializable;

public class JNMobileMessage extends JSONSerializable {

    private String id;
    private long timestamp;
    private JNCoordinates position;
    private double speed;

    public JNMobileMessage() {
    }

    public JNMobileMessage(String id, long timestamp, JNCoordinates coordinates, double speed) {
        this.id = id;
        this.timestamp = timestamp;
        this.position = coordinates;
        this.speed = speed;
    }

    /*
        Getter and Setter
     */

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public JNCoordinates getPosition() {
        return this.position;
    }

    public void setPosition(JNCoordinates position) {
        this.position = position;
    }

    public double getSpeed() {
        return this.speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

}
