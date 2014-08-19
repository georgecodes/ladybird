package com.elevenware.ladybird;

public interface RestResponse {

    int statusCode();
    <T> T getEntity();

}
