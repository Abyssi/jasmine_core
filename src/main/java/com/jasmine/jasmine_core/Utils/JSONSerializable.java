package com.jasmine.jasmine_core.Utils;

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JSONSerializable {

    public JSONSerializable() {
    }

    public static <T extends JSONSerializable> T FromJSONStringE(String jsonString, Class<T> typeClass) throws IOException {
        return new ObjectMapper().readValue(jsonString, typeClass);
    }

    public static <T extends JSONSerializable> T FromJSONString(String jsonString, Class<T> typeClass) {
        try {
            return JSONSerializable.FromJSONStringE(jsonString, typeClass);
        } catch (Exception e) {
            return null;
        }
    }

    public String toJSONString() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }

    @Override
    public String toString() {
        try {
            return this.toJSONString();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Cannot serialize object";
        }
    }
}
