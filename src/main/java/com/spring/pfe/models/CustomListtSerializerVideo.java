package com.spring.pfe.models;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomListtSerializerVideo extends StdSerializer<List<Video>> {

    public CustomListtSerializerVideo() {
        this(null);
    }

    public CustomListtSerializerVideo(Class<List<Video>> t) {
        super(t);
    }

    @Override
    public void serialize(
            List<Video> items,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException, JsonProcessingException {

        List<Video> ids = new ArrayList<>();
        for (Video item : items) {
            ids.add(item);
        }
        generator.writeObject(ids);
    }
}