package com.spring.pfe.models;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomListtSerializerUsers extends StdSerializer<List<User>> {

    public CustomListtSerializerUsers() {
        this(null);
    }

    public CustomListtSerializerUsers(Class<List<User>> t) {
        super(t);
    }

    @Override
    public void serialize(
            List<User> items,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException, JsonProcessingException {

        List<User> ids = new ArrayList<>();
        for (User item : items) {
            ids.add(item);
        }
        generator.writeObject(ids);
    }
}