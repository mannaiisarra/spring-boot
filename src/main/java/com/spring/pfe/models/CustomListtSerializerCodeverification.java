package com.spring.pfe.models;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomListtSerializerCodeverification extends StdSerializer<List<CodeVerification>> {

    public CustomListtSerializerCodeverification() {
        this(null);
    }

    public CustomListtSerializerCodeverification(Class<List<CodeVerification>> t) {
        super(t);
    }

    @Override
    public void serialize(
            List<CodeVerification> items,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException, JsonProcessingException {

        List<CodeVerification> ids = new ArrayList<>();
        for (CodeVerification item : items) {
            ids.add(item);
        }
        generator.writeObject(ids);
    }
}