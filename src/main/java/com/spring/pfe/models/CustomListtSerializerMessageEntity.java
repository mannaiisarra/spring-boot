package com.spring.pfe.models;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomListtSerializerMessageEntity extends StdSerializer<List<MessageEntity>> {

    public CustomListtSerializerMessageEntity() {
        this(null);
    }

    public CustomListtSerializerMessageEntity(Class<List<MessageEntity>> t) {
        super(t);
    }

    @Override
    public void serialize(
            List<MessageEntity> items,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException, JsonProcessingException {

        List<MessageEntity> ids = new ArrayList<>();
        for (MessageEntity item : items) {
            ids.add(item);
        }
        generator.writeObject(ids);
    }
}