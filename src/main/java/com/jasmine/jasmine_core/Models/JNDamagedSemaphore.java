package com.jasmine.jasmine_core.Models;

import java.util.List;

public class JNDamagedSemaphore {

    private String id;
    private String crossroadsId;

    private JNCoordinates position;
    private List<JNLightBulb> damagedLightBulbs;

    public JNDamagedSemaphore() {
    }

    public JNDamagedSemaphore(String crossroadsId, String id, JNCoordinates position, List<JNLightBulb> damagedLightBulbs) {
        this.crossroadsId = crossroadsId;
        this.id = id;
        this.position = position;
        this.damagedLightBulbs = damagedLightBulbs;
    }

    /*
        Getter and Setter
     */

    public String getCrossroadsId() {
        return this.crossroadsId;
    }

    public void setCrossroadsId(String crossroadsId) {
        this.crossroadsId = crossroadsId;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public JNCoordinates getPosition() {
        return this.position;
    }

    public void setPosition(JNCoordinates position) {
        this.position = position;
    }

    public List<JNLightBulb> getDamagedLightBulbs() {
        return this.damagedLightBulbs;
    }

    public void setDamagedLightBulbs(List<JNLightBulb> damagedLightBulbs) {
        this.damagedLightBulbs = damagedLightBulbs;
    }
}
