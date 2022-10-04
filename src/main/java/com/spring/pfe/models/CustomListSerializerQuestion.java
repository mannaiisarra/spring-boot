package com.spring.pfe.models;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomListSerializerQuestion extends StdSerializer<List<Question>> {

    public CustomListSerializerQuestion() {
        this(null);
    }

    public CustomListSerializerQuestion(Class<List<Question>> t) {
        super(t);
    }

    @Override
    public void serialize(
            List<Question> items,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException, JsonProcessingException {

        List<Question> ids = new ArrayList<>();
        for (Question item : items) {
            ids.add(item);
        }
        generator.writeObject(ids);
    }
}