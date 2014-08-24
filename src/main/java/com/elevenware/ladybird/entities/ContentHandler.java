package com.elevenware.ladybird.entities;

import java.io.InputStream;

public interface ContentHandler {
    <T> T unmarshal(String payload, Class<T> clazz);
    String marshal(Object payload);
    <T> T unmarshal(InputStream inputStream, Class<T> clazz);
}
