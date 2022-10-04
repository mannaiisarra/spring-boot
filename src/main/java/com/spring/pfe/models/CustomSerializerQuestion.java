package com.spring.pfe.models;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class CustomSerializerQuestion extends StdSerializer<Question> {

    public CustomSerializerQuestion() {
        this(null);
    }

    public CustomSerializerQuestion(Class<Question> t) {
        super(t);
    }

    @Override
    public void serialize(
            Question item,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException, JsonProcessingException {
        Question theme=new Question();
        theme.setQuesId(item.getQuesId());

        theme.setCorrect(item.getCorrect());
        theme.setGivenAnswer(item.getGivenAnswer());
        theme.setQuestion(item.getQuestion());
        theme.setResponse1(item.getResponse1());
        theme.setResponse2(item.getResponse2());
        theme.setResponse3(item.getResponse3());

        generator.writeObject(theme);
    }
}