package com.elevenware.ladybird.client;

import com.elevenware.ladybird.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestBuilder {

    private static final String CONTENT_TYPE = "ContentType";
    private static final String ACCEPT = "Accept";

    private final LadybirdClient.HttpClientDelegate client;
    private String path;
    private Map<String, String> headers;

    public HttpRequestBuilder(LadybirdClient.HttpClientDelegate client) {
        this.client = client;
        this.headers = new HashMap<>();
        addHeader(ACCEPT, ContentType.WILDCARD.getMimeType());
        addHeader(CONTENT_TYPE, ContentType.TEXT_PLAIN.getMimeType());
    }

    public HttpRequestBuilder setPath(String path) {
        this.path = normalise(path);
        return this;
    }

    public String getPath() {
        return path;
    }

    public HttpRequestBuilder addHeader(String headerName, String header) {
        headers.put(headerName, header);
        return this;
    }

    public HttpResponse get(String path) {
        setPath(path);
        return client.doGet(this);
    }

    public HttpResponse post(String path, String body) {
        setPath(path);
        return client.doPost(this, body);
    }

    public HttpResponse put(String path, String body) {
        setPath(path);
        return client.doPut(this, body);
    }

    public HttpResponse delete(String path) {
        setPath(path);
        return client.doDelete(this);
    }

    private String normalise(String path) {
        if(path.startsWith("/")) {
            path = path.substring(1);
        }
        return client.getFullPath(path);
    }

    public void populateHeaders(HttpRequestBase method) {
        for(String name: headers.keySet()) {
            method.addHeader(name, headers.get(name));
        }
    }

    public HttpRequestBuilder acceptJson() {
        return addHeader(ACCEPT, ContentType.APPLICATION_JSON.getMimeType());
    }

    public HttpRequestBuilder acceptXml() {
        return addHeader(ACCEPT, ContentType.APPLICATION_XML.getMimeType());
    }


    public ContentType getContentType() {
        return ContentType.parse(headers.get(CONTENT_TYPE));
    }

    public HttpRequestBuilder sendJson() {
        return addHeader(CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
    }

    public HttpRequestBuilder sendXml() {
        return addHeader(CONTENT_TYPE, ContentType.APPLICATION_XML.getMimeType());
    }


}
