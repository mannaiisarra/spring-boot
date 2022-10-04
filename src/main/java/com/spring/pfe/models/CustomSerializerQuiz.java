package com.spring.pfe.models;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class CustomSerializerQuiz  extends StdSerializer<Quiz> {

    public CustomSerializerQuiz() {
        this(null);
    }

    public CustomSerializerQuiz(Class<Quiz> t) {
        super(t);
    }

    @Override
    public void serialize(
            Quiz item,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException, JsonProcessingException {
        Quiz theme=new Quiz();
        theme.setId(item.getId());

        theme.setDescription(item.getDescription());
        theme.setActive(item.isActive());
        theme.setMaxMarks(item.getMaxMarks());
        theme.setNumberOfQuestions(item.getNumberOfQuestions());
        theme.setTitle(item.getTitle());

        generator.writeObject(theme);
    }
}