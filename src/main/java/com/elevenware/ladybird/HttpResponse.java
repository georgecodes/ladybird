package com.elevenware.ladybird;

public interface HttpResponse {

    int statusCode();
    <T> T getEntity();

}
