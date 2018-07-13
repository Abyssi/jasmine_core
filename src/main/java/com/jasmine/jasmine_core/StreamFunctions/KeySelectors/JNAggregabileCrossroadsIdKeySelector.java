package com.jasmine.jasmine_core.StreamFunctions.KeySelectors;

import com.jasmine.jasmine_core.Models.JNAggregabileCrossroads;
import org.apache.flink.api.java.functions.KeySelector;

public class JNAggregabileCrossroadsIdKeySelector implements KeySelector<JNAggregabileCrossroads, String> {
    private static final long serialVersionUID = 1L;

    @Override
    public String getKey(JNAggregabileCrossroads aggregabileCrossroads) {
        return aggregabileCrossroads.getId();
    }
}
