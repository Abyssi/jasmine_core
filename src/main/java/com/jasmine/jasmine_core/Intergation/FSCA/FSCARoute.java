package com.jasmine.jasmine_core.Intergation.FSCA;

import java.util.List;

public class FSCARoute {
    private List<List<Double>> route;

    public FSCARoute(List<List<Double>> route) {
        this.route = route;
    }

    public List<List<Double>> getRoute() {
        return route;
    }

    public void setRoute(List<List<Double>> route) {
        this.route = route;
    }
}
