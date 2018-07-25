package com.jasmine.jasmine_core.Utils;

public abstract class Decorable<T> {
    private T base;

    public Decorable(T base) {
        this.base = base;
    }

    public T getBase() {
        return base;
    }

    public void setBase(T base) {
        this.base = base;
    }
}
