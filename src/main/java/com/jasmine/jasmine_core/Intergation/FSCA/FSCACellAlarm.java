package com.jasmine.jasmine_core.Intergation.FSCA;

import java.time.Instant;

public class FSCACellAlarm {
    private FSCACustomCoordinates position;
    private Instant timestamp;

    public FSCACellAlarm(FSCACustomCoordinates position, Instant timestamp) {
        this.position = position;
        this.timestamp = timestamp;
    }

    public FSCACustomCoordinates getPosition() {
        return position;
    }

    public void setPosition(FSCACustomCoordinates position) {
        this.position = position;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
