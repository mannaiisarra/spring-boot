package com.spring.pfe.models;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomListSerializerFormation extends StdSerializer<List<Formation>> {

    public CustomListSerializerFormation() {
        this(null);
    }

    public CustomListSerializerFormation(Class<List<Formation>> t) {
        super(t);
    }

    @Override
    public void serialize(
            List<Formation> items,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException, JsonProcessingException {

        List<Formation> ids = new ArrayList<>();
        for (Formation item : items) {
            ids.add(item);
        }
        generator.writeObject(ids);
    }
}