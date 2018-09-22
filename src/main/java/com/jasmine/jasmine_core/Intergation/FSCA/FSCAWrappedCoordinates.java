package com.jasmine.jasmine_core.Intergation.FSCA;

public class FSCAWrappedCoordinates {
    private FSCALatLonCoordinates position;

    public FSCAWrappedCoordinates(FSCALatLonCoordinates position) {
        this.position = position;
    }

    public FSCALatLonCoordinates getPosition() {
        return position;
    }

    public void setPosition(FSCALatLonCoordinates position) {
        this.position = position;
    }
}
