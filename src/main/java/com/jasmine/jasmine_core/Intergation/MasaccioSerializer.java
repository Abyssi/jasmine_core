package com.jasmine.jasmine_core.Intergation;

import org.apache.flink.api.common.serialization.SerializationSchema;

public class MasaccioSerializer implements SerializationSchema<MasaccioMessage> {

    private static final long serialVersionUID = 6154188370181669758L;

    @Override
    public byte[] serialize(MasaccioMessage msg) {
        return (msg.getId()+msg.getKindId()+"@"+msg.getValue()+String.valueOf(msg.getInstant().getEpochSecond())).getBytes();
    }
}
