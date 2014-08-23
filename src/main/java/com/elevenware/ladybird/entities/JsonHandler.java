package com.elevenware.ladybird.entities;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonHandler implements ContentHandler {
    @Override
    public <T> T unmarshal(String payload, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return (T) mapper.readValue(payload, clazz);
        } catch (IOException e) {
            throw new UnmarshallingException();
        }
    }
}
