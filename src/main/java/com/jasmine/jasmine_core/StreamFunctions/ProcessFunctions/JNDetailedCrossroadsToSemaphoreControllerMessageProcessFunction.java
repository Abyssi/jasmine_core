package com.jasmine.jasmine_core.StreamFunctions.ProcessFunctions;

import com.jasmine.jasmine_core.Models.JNDetailedCrossroads;
import com.jasmine.jasmine_core.Models.JNSemaphoreControllerMessage;
import com.jasmine.jasmine_core.Utils.FlinkParameters;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;

public class JNDetailedCrossroadsToSemaphoreControllerMessageProcessFunction extends ProcessFunction<JNDetailedCrossroads, JNSemaphoreControllerMessage> {
    private static final long serialVersionUID = 1L;

    @Override
    public void processElement(JNDetailedCrossroads detailedCrossroads, Context context, Collector<JNSemaphoreControllerMessage> out) throws Exception {
        ParameterTool parameterTool = FlinkParameters.getParameters();

        Double streetMaxCapacity = parameterTool.getDouble("controller.street.max.capacity", 175); // Street geometric characteristics dependant max capacity
        Double lostTime = parameterTool.getDouble("controller.lost.time", 4); // Sum of starting time and leave time
        Double cycleDuration = parameterTool.getDouble("controller.cycle.duration", 200); // Duration of the entire semaphore cycle phase
        Double yellowDuration = parameterTool.getDouble("controller.yellow.duration", 4); // Yellow duration
        Double allRedDuration = parameterTool.getDouble("controller.all.red.duration", 2); // Duration of the static phase when all semaphore are red simultaneously
        double changeTime = yellowDuration + allRedDuration;

        double maxEven = Double.MIN_VALUE;
        for (int i = 0; i < detailedCrossroads.getSemaphoreList().size(); i += 2)
            if (maxEven < detailedCrossroads.getSemaphoreList().get(i).f2)
                maxEven = detailedCrossroads.getSemaphoreList().get(i).f2;

        double maxOdd = Double.MIN_VALUE;
        for (int i = 1; i < detailedCrossroads.getSemaphoreList().size(); i += 2)
            if (maxOdd < detailedCrossroads.getSemaphoreList().get(i).f2)
                maxOdd = detailedCrossroads.getSemaphoreList().get(i).f2;

        double max = Double.max(maxEven, maxOdd);

        double effectiveGreen = ((max / streetMaxCapacity) / ((maxEven / streetMaxCapacity) + (maxOdd / streetMaxCapacity))) * (cycleDuration - lostTime * 2);

        double greenDuration = effectiveGreen + lostTime - changeTime;
        double redDuration = cycleDuration - (greenDuration + changeTime);

        for (int i = 0; i < detailedCrossroads.getSemaphoreList().size(); i += 2)
            out.collect(new JNSemaphoreControllerMessage(detailedCrossroads.getId(), detailedCrossroads.getSemaphoreList().get(i).f0, Math.toIntExact(Math.round(greenDuration))));

        for (int i = 1; i < detailedCrossroads.getSemaphoreList().size(); i += 2)
            out.collect(new JNSemaphoreControllerMessage(detailedCrossroads.getId(), detailedCrossroads.getSemaphoreList().get(i).f0, Math.toIntExact(Math.round(redDuration - yellowDuration))));
    }
}
