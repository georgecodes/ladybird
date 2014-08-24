package com.elevenware.ladybird.entities;

import org.apache.http.entity.ContentType;

import java.util.HashMap;
import java.util.Map;

public class ContentHandlers {

    private static final ContentHandlers INSTANCE = new ContentHandlers();

    private Map<String, ContentHandler> handlers;
    private final ContentHandler DEFAULT_HANDLER = new StringHandler();

    private ContentHandlers() {
        handlers = new HashMap<>();
        handlers.put(ContentType.APPLICATION_JSON.getMimeType(), new JsonHandler());
    }

    public static ContentHandlers getInstance() {
        return INSTANCE;
    }

    public ContentHandler forType(ContentType contentType, ContentType accept) {
        ContentHandler handler = handlers.get(contentType.getMimeType());
        if(handler == null && accept != null) {
            handler = handlers.get(accept.getMimeType());
        }
        if(handler == null) {
            handler = DEFAULT_HANDLER;
        }
        return handler;
    }

    public void registerHandler(ContentType contentType, ContentHandler handler) {
        handlers.put(contentType.getMimeType(), handler);
    }

}
