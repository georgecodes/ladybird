package com.elevenware.ladybird;

public class ObjectResponse implements RestResponse {
    private final int status;
    private final Object payload;

    public ObjectResponse(int statusCode, Object payload) {
        this.status = statusCode;
        this.payload = payload;
    }

    @Override
    public int statusCode() {
        return status;
    }

    @Override
    public <T> T getEntity() {
        return (T) payload;
    }
}
