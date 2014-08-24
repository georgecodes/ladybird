package com.elevenware.ladybird.entities;

import java.io.*;

public class StringHandler implements ContentHandler {
    @Override
    public <T> T unmarshal(String payload, Class<T> clazz) {
        return (T) payload;
    }

    @Override
    public String marshal(Object payload) {
        return String.valueOf(payload);
    }

    @Override
    public <T> T unmarshal(InputStream inputStream, Class<T> clazz) {
        StringWriter writer = new StringWriter();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder builder = new StringBuilder();
        String line = "";
        try {
            while((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (T) builder.toString();
    }
}
