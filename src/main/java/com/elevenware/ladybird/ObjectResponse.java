package com.elevenware.ladybird;

import com.elevenware.ladybird.entities.ContentHandler;
import com.elevenware.ladybird.entities.ContentHandlers;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.ContentType;

public class ObjectResponse implements HttpResponse {
    private final CloseableHttpResponse response;
    private final String payload;
    private final ContentType originalAccept;

    public ObjectResponse(CloseableHttpResponse response, String payload, ContentType accept) {
        this.response = response;
        this.payload = payload;
        this.originalAccept = accept;
    }

    @Override
    public int statusCode() {
        return response.getStatusLine().getStatusCode();
    }

    @Override
    public <T> T getEntity() {
        return (T) payload;
    }

    @Override
    public <T> T getEntity(Class<T> clazz) {
        ContentType contentType = ContentType.parse(response.getEntity().getContentType().getValue());
        ContentHandler handler = ContentHandlers.getInstance().forType(contentType,originalAccept);
        return handler.unmarshal(payload, clazz);
    }
}
