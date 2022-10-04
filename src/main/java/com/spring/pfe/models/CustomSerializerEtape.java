package com.spring.pfe.models;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class CustomSerializerEtape extends StdSerializer<Etape> {

    public CustomSerializerEtape() {
        this(null);
    }

    public CustomSerializerEtape(Class<Etape> t) {
        super(t);
    }

    @Override
    public void serialize(
            Etape item,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException, JsonProcessingException {
        Etape theme=new Etape();
        theme.setIdEtape(item.getIdEtape());
        theme.setDescription(item.getDescription());
        theme.setDate(item.getDate());


        generator.writeObject(theme);
    }
}