package com.jasmine.jasmine_core.Intergation.FSCA;

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FSCACellStatistics {
    private FSCALatLonCoordinates centro;
    private String cella;
    private Double PM10;
    private Double QoA;
    private Long timestamp;
    private Integer danger;

    public FSCACellStatistics() {
    }

    public FSCACellStatistics(FSCALatLonCoordinates centro, String cella, Double PM10, Double qoA, Long timestamp, Integer danger) {
        this.centro = centro;
        this.cella = cella;
        this.PM10 = PM10;
        this.QoA = qoA;
        this.timestamp = timestamp;
        this.danger = danger;
    }

    public FSCALatLonCoordinates getCentro() {
        return centro;
    }

    public void setCentro(FSCALatLonCoordinates centro) {
        this.centro = centro;
    }

    public String getCella() {
        return cella;
    }

    public void setCella(String cella) {
        this.cella = cella;
    }

    public Double getPM10() {
        return PM10;
    }

    public void setPM10(Double PM10) {
        this.PM10 = PM10;
    }

    public Double getQoA() {
        return QoA;
    }

    public void setQoA(Double qoA) {
        QoA = qoA;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getDanger() {
        return danger;
    }

    public void setDanger(Integer danger) {
        this.danger = danger;
    }
}
