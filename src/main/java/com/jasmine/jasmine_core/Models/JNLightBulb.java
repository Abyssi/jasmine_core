package com.jasmine.jasmine_core.Models;

public class JNLightBulb {

    private JNLightBulbColor color;
    private JNLightBulbStatus status;

    public JNLightBulb() {
    }

    public JNLightBulb(JNLightBulbColor color, JNLightBulbStatus status) {
        this.color = color;
        this.status = status;
    }

    /*
        Getter and Setter
     */

    public JNLightBulbColor getColor() {
        return this.color;
    }

    public void setColor(JNLightBulbColor color) {
        this.color = color;
    }

    public JNLightBulbStatus getStatus() {
        return this.status;
    }

    public void setStatus(JNLightBulbStatus status) {
        this.status = status;
    }

}
