package com.spring.pfe.models;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomListSerializerQuiz extends StdSerializer<List<Quiz>> {

    public CustomListSerializerQuiz() {
        this(null);
    }

    public CustomListSerializerQuiz(Class<List<Quiz>> t) {
        super(t);
    }

    @Override
    public void serialize(
            List<Quiz> items,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException, JsonProcessingException {

        List<Quiz> ids = new ArrayList<>();
        for (Quiz item : items) {
            ids.add(item);
        }
        generator.writeObject(ids);
    }
}