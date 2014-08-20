package com.elevenware.ladybird;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;

public class RestClient {

    private final CloseableHttpClient httpClient;
    private final String base;

    public RestClient() {
        this("");
    }

    public RestClient(String base) {
        this.httpClient = HttpClients.createDefault();
        this.base = "".concat(base);
    }

    public RestResponse get(String path) {
        HttpGet get = new HttpGet(base.concat(path));
        try {
            CloseableHttpResponse response = httpClient.execute(get);
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

    public RestResponse getJson(String path, Class clazz) {
        HttpGet get = new HttpGet(base.concat(path));
        get.setHeader("Accept", "application/json");
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(get);
            HttpEntity entity = response.getEntity();

            ObjectMapper mapper = new ObjectMapper();
            Object payload = mapper.readValue(entity.getContent(), clazz);
            return new ObjectResponse(response.getStatusLine().getStatusCode(), payload);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public RestResponse postJson(String path, Object body) {
        Class clazz = body.getClass();
        HttpPost post = new HttpPost(base.concat(path));
        post.setHeader("Accept", "application/json");
        post.setHeader("ContentType", "application/json");
        StringWriter writer = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(writer, body);
            HttpEntity entity = new StringEntity(writer.toString(), ContentType.APPLICATION_JSON);
            post.setEntity(entity);
            CloseableHttpResponse response = httpClient.execute(post);
            Object ret =  mapper.readValue(response.getEntity().getContent(), clazz);
            return new ObjectResponse(response.getStatusLine().getStatusCode(), ret);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public RestResponse post(String path, String body) {

        HttpPost post = new HttpPost(base.concat(path));
        post.setHeader("Accept", "text/plain");
        post.setHeader("ContentType", "text/plain");
        HttpEntity entity = new StringEntity(body, ContentType.TEXT_PLAIN);
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

    public RestResponse put(String path, String body) {
        HttpPut post = new HttpPut(base.concat(path));
        post.setHeader("Accept", "text/plain");
        post.setHeader("ContentType", "text/plain");
        HttpEntity entity = new StringEntity(body, ContentType.TEXT_PLAIN);
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

    public RestResponse delete(String path) {
        HttpDelete delete = new HttpDelete(base.concat(path));
        try {
            CloseableHttpResponse response = httpClient.execute(delete);
            return new ObjectResponse(response.getStatusLine().getStatusCode(), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
