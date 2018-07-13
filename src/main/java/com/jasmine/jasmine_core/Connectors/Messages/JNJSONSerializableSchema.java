package com.jasmine.jasmine_core.Connectors.Messages;

import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JNJSONSerializableSchema<T> implements DeserializationSchema<T>, SerializationSchema<T> {

    private static final long serialVersionUID = 6154188370181669758L;
    private final Class<T> tClass;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JNJSONSerializableSchema(Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    public byte[] serialize(T obj) {
        try {
            return objectMapper.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public T deserialize(byte[] message) {
        try {
            return objectMapper.readValue(message, tClass);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean isEndOfStream(T nextElement) {
        return false;
    }

    @Override
    public TypeInformation<T> getProducedType() {
        return TypeInformation.of(tClass);
    }
}
