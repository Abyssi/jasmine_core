package com.jasmine.jasmine_core.Connectors.Messages;

import com.jasmine.jasmine_core.Models.JNBaseSemaphoreMessage;
import com.jasmine.jasmine_core.Models.JNCoordinates;
import com.jasmine.jasmine_core.Models.JNLightBulb;

import java.util.List;

public class JNSemaphoreMessage extends JNBaseSemaphoreMessage {
    private List<JNLightBulb> lightBulbs;
    private int greenDuration;

    public JNSemaphoreMessage() {
    }

    public JNSemaphoreMessage(String crossroadsId, String semaphoreId, JNCoordinates position, long timestamp, int greenDuration, int vehiclesCount, double averageSpeed, List<JNLightBulb> lightBulbs) {
        super(crossroadsId, semaphoreId, position, timestamp, vehiclesCount, averageSpeed);
        this.greenDuration = greenDuration;
        this.lightBulbs = lightBulbs;
    }

    /*
        Getter and Setter
     */

    public List<JNLightBulb> getLightBulbs() {
        return this.lightBulbs;
    }

    public void setLightBulbs(List<JNLightBulb> lightBulbs) {
        this.lightBulbs = lightBulbs;
    }

    public int getGreenDuration() {
        return this.greenDuration;
    }

    public void setGreenDuration(int greenDuration) {
        this.greenDuration = greenDuration;
    }

}
