package com.elevenware.ladybird.entities;

public interface ContentHandler {
    <T> T unmarshal(String payload, Class<T> clazz);
}
