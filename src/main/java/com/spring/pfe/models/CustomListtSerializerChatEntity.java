package com.spring.pfe.models;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomListtSerializerChatEntity   extends StdSerializer<List<ChatEntity>> {

    public CustomListtSerializerChatEntity() {
        this(null);
    }

    public CustomListtSerializerChatEntity(Class<List<ChatEntity>> t) {
        super(t);
    }

    @Override
    public void serialize(
            List<ChatEntity> items,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException, JsonProcessingException {

        List<ChatEntity> ids = new ArrayList<>();
        for (ChatEntity item : items) {
            ids.add(item);
        }
        generator.writeObject(ids);
    }
}