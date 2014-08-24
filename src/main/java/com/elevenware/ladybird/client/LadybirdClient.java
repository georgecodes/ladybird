package com.elevenware.ladybird.client;

import com.elevenware.ladybird.HttpResponse;
import com.elevenware.ladybird.ObjectResponse;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LadybirdClient {

    private HttpClientDelegate delegate;

    public LadybirdClient() {
        this("");
    }

    public LadybirdClient(String base) {

        this.delegate = new HttpClientDelegate(base);
    }

    public static LadybirdClient forLocalhost() {
        return new LadybirdClient("http://localhost:8080");
    }

    public HttpResponse get(String path) {
        return new HttpRequestBuilder(delegate)
            .get(path);
    }

    public HttpResponse post(String path, String entity) {
        return new HttpRequestBuilder(delegate)
                .post(path, entity);
    }

    public HttpResponse put(String path, String entity) {
        return new HttpRequestBuilder(delegate)
                .put(path, entity);
    }

    public HttpResponse delete(String path) {
        return new HttpRequestBuilder(delegate)
                .delete(path);
    }

    public HttpRequestBuilder withHeader(String headerName, String header) {
        return new HttpRequestBuilder(delegate)
            .addHeader(headerName, header);
    }

    public HttpRequestBuilder acceptJson() {
        return new HttpRequestBuilder(delegate)
           .acceptJson();
    }

    public HttpRequestBuilder acceptXml() {
        return new HttpRequestBuilder(delegate)
            .acceptXml();
    }

    public HttpRequestBuilder acceptHtml() {
        return new HttpRequestBuilder(delegate)
            .acceptHtml();
    }

    public HttpRequestBuilder sendJson() {
        return new HttpRequestBuilder(delegate)
                .sendJson();
    }

    public HttpRequestBuilder sendXml() {
        return new HttpRequestBuilder(delegate)
                .sendXml();
    }

    public HttpRequestBuilder sendHtml() {
        return new HttpRequestBuilder(delegate)
                .sendHtml();
    }

    static class HttpClientDelegate {

        private final String base;
        private final CloseableHttpClient httpClient;

        HttpClientDelegate(String base) {
            if(base.length() != 0 && !base.endsWith("/")) {
                base = base.concat("/");
            }
            this.httpClient = HttpClients.createDefault();
            this.base = "".concat(base);
        }

        public HttpResponse doGet(HttpRequestBuilder request) {
            String path = request.getPath();
            HttpGet get = new HttpGet(path);
            request.populateHeaders(get);
            CloseableHttpResponse response = null;
            try {
                response = httpClient.execute(get);
                HttpEntity entity1 = response.getEntity();
                BufferedReader reader = new BufferedReader(new InputStreamReader(entity1.getContent()));
                StringBuilder buf = new StringBuilder();
                String line = "";
                while((line = reader.readLine()) != null) {
                    buf.append(line);
                }
                String body = buf.toString();
                return new ObjectResponse(response, body, request.getAccept());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        public HttpResponse doPost(HttpRequestBuilder request, String body) {
        String path = request.getPath();
        HttpPost post = new HttpPost(path);
        request.populateHeaders(post);
        HttpEntity entity = new StringEntity(body, request.getContentType());
        post.setEntity(entity);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(post);
            entity = response.getEntity();

            BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
            StringBuilder buf = new StringBuilder();
            String line = "";
            while((line = reader.readLine()) != null) {
                buf.append(line);
            }
            return new ObjectResponse(response, buf.toString(), request.getAccept());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

        public HttpResponse doPut(HttpRequestBuilder request, String body) {
            String path = request.getPath();
            HttpPut put = new HttpPut(path);
            request.populateHeaders(put);
            HttpEntity entity = new StringEntity(body, request.getContentType());
            put.setEntity(entity);
            CloseableHttpResponse response = null;
            try {
                response = httpClient.execute(put);
                entity = response.getEntity();

                BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
                StringBuilder buf = new StringBuilder();
                String line = "";
                while((line = reader.readLine()) != null) {
                    buf.append(line);
                }
                return new ObjectResponse(response, buf.toString(), request.getAccept());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        public HttpResponse doDelete(HttpRequestBuilder request) {
            String path = request.getPath();
            HttpDelete delete = new HttpDelete(path);
            request.populateHeaders(delete);
            try {
                CloseableHttpResponse response = httpClient.execute(delete);
                return new ObjectResponse(response, null, request.getAccept());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        public String getFullPath(String context) {
            return base.concat(context);
        }
    }
}
