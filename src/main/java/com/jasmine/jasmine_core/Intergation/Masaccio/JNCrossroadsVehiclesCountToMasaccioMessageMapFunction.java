package com.jasmine.jasmine_core.Intergation.Masaccio;

import com.jasmine.jasmine_core.Models.JNCrossroads;
import org.apache.flink.api.common.functions.MapFunction;

import java.time.Instant;

public class JNCrossroadsVehiclesCountToMasaccioMessageMapFunction implements MapFunction<JNCrossroads, MasaccioMessage> {
    private static final long serialVersionUID = 1L;

    @Override
    public MasaccioMessage map(JNCrossroads crossroads) throws Exception {
        //return new MasaccioMessage(crossroads.getId().replaceAll("[^0-9]", ""), "1", String.valueOf(crossroads.getMedianVehiclesCount()), Instant.now());
        return new MasaccioMessage("9", "", String.valueOf(crossroads.getMedianVehiclesCount()), Instant.now());
    }
}
