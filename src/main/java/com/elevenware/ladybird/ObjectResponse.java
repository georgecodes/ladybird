package com.elevenware.ladybird;

import org.apache.http.client.methods.CloseableHttpResponse;

public class ObjectResponse implements HttpResponse {
    private final CloseableHttpResponse response;
    private final Object payload;

    public ObjectResponse(CloseableHttpResponse response, Object payload) {
        this.response = response;
        this.payload = payload;
    }

    @Override
    public int statusCode() {
        return response.getStatusLine().getStatusCode();
    }

    @Override
    public <T> T getEntity() {
        return (T) payload;
    }
}
