package com.elevenware.ladybird;

public interface HttpResponse {

    int statusCode();
    <T> T getEntity();
    <T> T getEntity(Class<T> clazz);

}
