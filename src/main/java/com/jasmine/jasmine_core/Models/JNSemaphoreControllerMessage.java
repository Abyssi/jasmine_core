package com.jasmine.jasmine_core.Models;

public class JNSemaphoreControllerMessage {
    private String crossroadsId;
    private String semaphoreId;
    private int greenDuration;

    public JNSemaphoreControllerMessage() {
    }

    public JNSemaphoreControllerMessage(String crossroadsId, String semaphoreId, int greenDuration) {
        this.crossroadsId = crossroadsId;
        this.semaphoreId = semaphoreId;
        this.greenDuration = greenDuration;
    }

    /*
        Getter and Setter
     */

    public String getCrossroadsId() {
        return crossroadsId;
    }

    public void setCrossroadsId(String crossroadsId) {
        this.crossroadsId = crossroadsId;
    }

    public String getSemaphoreId() {
        return semaphoreId;
    }

    public void setSemaphoreId(String semaphoreId) {
        this.semaphoreId = semaphoreId;
    }

    public int getGreenDuration() {
        return greenDuration;
    }

    public void setGreenDuration(int greenDuration) {
        this.greenDuration = greenDuration;
    }


}
