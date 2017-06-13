package com.mengka.springboot.moxie_01.componet;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Map;

public class TaskCreateReqSerializer extends StdSerializer<TaskCreateReq> {

    public TaskCreateReqSerializer() {
        this(null);
    }

    protected TaskCreateReqSerializer(Class<TaskCreateReq> t) {
        super(t);
    }

    @Override
    public void serialize(TaskCreateReq taskCreateReq, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        Map<String, String> params = taskCreateReq.getParams();
        jsonGenerator.writeStartObject();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            jsonGenerator.writeStringField(entry.getKey(), entry.getValue());
        }
        jsonGenerator.writeEndObject();
    }
}
