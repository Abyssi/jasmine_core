package com.jasmine.jasmine_core.Utils;

public class Identified<T> extends Decorable<T> {
    private String id;

    public Identified(T base, String id) {
        super(base);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
