package com.elevenware.ladybird.entities;

public class StringHandler implements ContentHandler {
    @Override
    public <T> T unmarshal(String payload, Class<T> clazz) {
        return (T) payload;
    }
}
