package com.jasmine.jasmine_core.StreamFunctions.KeySelectors;

import com.jasmine.jasmine_core.Utils.Identified;
import org.apache.flink.api.java.functions.KeySelector;

public class JNIdentifiedIdKeySelector<T> implements KeySelector<Identified<T>, String> {
    private static final long serialVersionUID = 1L;

    @Override
    public String getKey(Identified<T> identified) {
        return identified.getId();
    }

}
