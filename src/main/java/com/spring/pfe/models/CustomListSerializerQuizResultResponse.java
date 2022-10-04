package com.spring.pfe.models;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomListSerializerQuizResultResponse extends StdSerializer<List<QuizResultResponse>> {

    public CustomListSerializerQuizResultResponse() {
        this(null);
    }

    public CustomListSerializerQuizResultResponse(Class<List<QuizResultResponse>> t) {
        super(t);
    }

    @Override
    public void serialize(
            List<QuizResultResponse> items,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException, JsonProcessingException {

        List<QuizResultResponse> ids = new ArrayList<>();
        for (QuizResultResponse item : items) {
            ids.add(item);
        }
        generator.writeObject(ids);
    }
}