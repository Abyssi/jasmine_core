package com.jasmine.jasmine_core.Intergation.FSCA;

public class FSCALatLonCoordinates {
    private Double lat;
    private Double lon;

    public FSCALatLonCoordinates() {
    }

    public FSCALatLonCoordinates(Double lat, Double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }
}
