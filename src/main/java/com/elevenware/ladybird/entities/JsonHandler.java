package com.elevenware.ladybird.entities;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

public class JsonHandler implements ContentHandler {
    @Override
    public <T> T unmarshal(String payload, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return (T) mapper.readValue(payload, clazz);
        } catch (IOException e) {
            StringBuilder builder = new StringBuilder("Unable to unmarshall response to")
                    .append(clazz)
                    .append(" as this body does not appear to be valid JSON:\n")
                    .append(payload);
            throw new UnmarshallingException(builder.toString(), e);
        }
    }

    @Override
    public String marshal(Object payload) {
        ObjectMapper mapper = new ObjectMapper();
        StringWriter writer = new StringWriter();
        try {
            mapper.writeValue(writer, payload);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }

    @Override
    public <T> T unmarshal(InputStream inputStream, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return (T) mapper.readValue(inputStream, clazz);
        } catch (IOException e) {
            StringBuilder builder = new StringBuilder("Unable to unmarshall response to")
                    .append(clazz)
                    .append(" as the body does not appear to be valid JSON");

            throw new UnmarshallingException(builder.toString(), e);
        }
    }

}
