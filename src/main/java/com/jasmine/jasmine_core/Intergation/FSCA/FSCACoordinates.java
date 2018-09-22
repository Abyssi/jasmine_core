package com.jasmine.jasmine_core.Intergation.FSCA;

public class FSCACoordinates {
    private Double latitude;
    private Double longitude;

    public FSCACoordinates() {
    }

    public FSCACoordinates(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
