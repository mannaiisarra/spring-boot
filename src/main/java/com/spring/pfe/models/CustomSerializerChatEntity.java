package com.spring.pfe.models;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class CustomSerializerChatEntity extends StdSerializer<ChatEntity> {

    public CustomSerializerChatEntity() {
        this(null);
    }

    public CustomSerializerChatEntity(Class<ChatEntity> t) {
        super(t);
    }

    @Override
    public void serialize(
            ChatEntity item,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException, JsonProcessingException {
        ChatEntity theme=new ChatEntity();
        theme.setChat_id(item.getChat_id());
        theme.setName(item.getName());



        generator.writeObject(theme);
    }
}