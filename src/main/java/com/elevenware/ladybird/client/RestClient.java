package com.elevenware.ladybird.client;

import com.elevenware.ladybird.ObjectResponse;
import com.elevenware.ladybird.RestResponse;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RestClient {

    private InnerRestClient delegate;

    public RestClient() {
        this("");
    }

    public RestClient(String base) {

        this.delegate = new InnerRestClient(base);
    }

    public RestResponse get(String path) {
        return new BuildableRestRequest(delegate)
            .get(path);
    }

    public RestResponse post(String path, String entity) {
        return new BuildableRestRequest(delegate)
                .post(path, entity);
    }

    public RestResponse put(String path, String entity) {
        return new BuildableRestRequest(delegate)
                .put(path, entity);
    }

    public RestResponse delete(String path) {
        return new BuildableRestRequest(delegate)
                .delete(path);
    }

    public BuildableRestRequest withHeader(String headerName, String header) {
        return new BuildableRestRequest(delegate)
            .addHeader(headerName, header);
    }

    public BuildableRestRequest acceptJson() {
        return new BuildableRestRequest(delegate)
           .acceptJson();
    }

    public BuildableRestRequest acceptXml() {
        return new BuildableRestRequest(delegate)
            .acceptXml();
    }

    public BuildableRestRequest sendJson() {
        return new BuildableRestRequest(delegate)
                .sendJson();
    }

    public BuildableRestRequest sendXml() {
        return new BuildableRestRequest(delegate)
                .sendXml();
    }

    static class InnerRestClient {

        private final String base;
        private final CloseableHttpClient httpClient;

        InnerRestClient(String base) {
            if(!base.endsWith("/")) {
                base = base.concat("/");
            }
            this.httpClient = HttpClients.createDefault();
            this.base = "".concat(base);
        }

        public RestResponse doGet(BuildableRestRequest request) {
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
                return new ObjectResponse(response.getStatusLine().getStatusCode(), body);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        public RestResponse doPost(BuildableRestRequest request, String body) {
        String path = request.getPath();
        HttpPost post = new HttpPost(path);
        request.populateHeaders(post);
        HttpEntity entity = new StringEntity(body, ContentType.parse(request.getContentType()));
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
            return new ObjectResponse(response.getStatusLine().getStatusCode(), buf.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

        public RestResponse doPut(BuildableRestRequest request, String body) {
            String path = request.getPath();
            HttpPut put = new HttpPut(path);
            request.populateHeaders(put);
            HttpEntity entity = new StringEntity(body, ContentType.parse(request.getContentType()));
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
                return new ObjectResponse(response.getStatusLine().getStatusCode(), buf.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        public RestResponse doDelete(BuildableRestRequest request) {
            String path = request.getPath();
            HttpDelete delete = new HttpDelete(path);
            request.populateHeaders(delete);
            try {
                CloseableHttpResponse response = httpClient.execute(delete);
                return new ObjectResponse(response.getStatusLine().getStatusCode(), null);
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
