package com.jasmine.jasmine_core.Intergation.Masaccio;

import java.time.Instant;

public class MasaccioMessage {
    private String id;
    private String kindId;
    private String value;
    private Instant instant;

    public MasaccioMessage(String id, String kindId, String value, Instant instant) {
        this.id = id;
        this.kindId = kindId;
        this.value = value;
        this.instant = instant;
    }

    public String getId() {
        return id;
    }

    public String getKindId() {
        return kindId;
    }

    public String getValue() {
        return value;
    }

    public Instant getInstant() {
        return instant;
    }
}
