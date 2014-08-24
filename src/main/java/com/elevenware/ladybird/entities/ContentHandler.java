package com.elevenware.ladybird.entities;

import com.fasterxml.jackson.core.JsonGenerationException;

import java.io.IOException;
import java.io.InputStream;

public interface ContentHandler {
    <T> T unmarshal(String payload, Class<T> clazz);
    String marshal(Object payload);
    <T> T unmarshal(InputStream inputStream, Class<T> clazz);
}
