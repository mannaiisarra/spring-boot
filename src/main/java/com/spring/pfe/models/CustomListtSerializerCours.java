package com.spring.pfe.models;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomListtSerializerCours extends StdSerializer<List<Cours>> {

    public CustomListtSerializerCours() {
        this(null);
    }

    public CustomListtSerializerCours(Class<List<Cours>> t) {
        super(t);
    }

    @Override
    public void serialize(
            List<Cours> items,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException, JsonProcessingException {

        List<Cours> ids = new ArrayList<>();
        for (Cours item : items) {
            ids.add(item);
        }
        generator.writeObject(ids);
    }
}