package com.elevenware.ladybird.client;

import com.elevenware.ladybird.RestResponse;
import com.elevenware.ladybird.http.MimeTypes;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BuildableRestRequest {

    private static final String CONTENT_TYPE = "ContentType";
    private static final String ACCEPT = "Accept";

    private final RestClient.InnerRestClient client;
    private String path;
    private Map<String, String> headers;

    public BuildableRestRequest(RestClient.InnerRestClient client) {
        this.client = client;
        this.headers = new HashMap<>();
        addHeader(ACCEPT, "*/*");
        addHeader(CONTENT_TYPE, MimeTypes.TEXT_PLAIN);
    }

    public BuildableRestRequest setPath(String path) {
        this.path = normalise(path);
        return this;
    }

    public String getPath() {
        return path;
    }

    public BuildableRestRequest addHeader(String headerName, String header) {
        headers.put(headerName, header);
        return this;
    }

    public RestResponse get(String path) {
        setPath(path);
        return client.doGet(this);
    }

    public RestResponse post(String path, String body) {
        setPath(path);
        return client.doPost(this, body);
    }

    public RestResponse put(String path, String body) {
        setPath(path);
        return client.doPut(this, body);
    }

    public RestResponse delete(String path) {
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

    public BuildableRestRequest acceptJson() {
        return addHeader(ACCEPT, MimeTypes.APPLICATION_JSON);
    }

    public BuildableRestRequest acceptXml() {
        return addHeader(ACCEPT, MimeTypes.APPLICATION_XML);
    }


    public String getContentType() {
        return headers.get(CONTENT_TYPE);
    }

    public BuildableRestRequest sendJson() {
        return addHeader(CONTENT_TYPE, MimeTypes.APPLICATION_JSON);
    }

    public BuildableRestRequest sendXml() {
        return addHeader(CONTENT_TYPE, MimeTypes.APPLICATION_XML);
    }


}
