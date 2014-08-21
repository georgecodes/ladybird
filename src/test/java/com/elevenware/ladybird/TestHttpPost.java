package com.elevenware.ladybird;

import com.elevenware.ladybird.client.RestClient;
import com.elevenware.ladybird.http.MimeTypes;
import com.elevenware.ladybird.kit.AbstractHttpRecordingTestCase;
import com.elevenware.ladybird.kit.RecordableHttpRequest;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TestHttpPost extends AbstractHttpRecordingTestCase {

    @Test
    public void works() throws IOException, InterruptedException {

        String path = "/body";

        RestClient client = new RestClient("http://localhost:8080");
        client.post(path, "Hello");

        RecordableHttpRequest requestRecording = getLast();

        assertEquals(path, requestRecording.getPathInfo());
        assertEquals("POST", requestRecording.getMethod());

        String bodyAsString = requestRecording.consumeBodyAsString();
        assertEquals("Hello", bodyAsString);

    }

    @Test
    public void queryParamsArePassed() {

        String path = "/testpath";
        String params = "param1=hello";

        RestClient client = new RestClient("http://localhost:8080");
        client.post(path.concat("?").concat(params), "Hello there");

        RecordableHttpRequest requestRecording = getLast();
        assertEquals(path, requestRecording.getPathInfo());

        assertEquals(params, requestRecording.getQueryString());
        assertEquals(1, requestRecording.getParameterMap().size());
        assertEquals("hello", requestRecording.getParameter("param1"));

    }

    @Test
    public void headersArePassed() {

        String path = "/testpath";

        RestClient client = new RestClient("http://localhost:8080");
        client.withHeader("Foo", "Bar").post(path, "Hello");

        RecordableHttpRequest requestRecording = getLast();

        assertEquals("Bar", requestRecording.getHeader("Foo"));

    }

    @Test
    public void postJson() {

        String path = "/testpath";

        RestClient client = new RestClient("http://localhost:8080");
        client.sendJson().post(path, "Hello");

        RecordableHttpRequest requestRecording = getLast();

        assertEquals(MimeTypes.APPLICATION_JSON, requestRecording.getHeader("ContentType"));

    }

    @Test
    public void postJsonWithHeaders() {

        String path = "/testpath";

        RestClient client = new RestClient("http://localhost:8080");
        client.withHeader("Foo", "Bar").sendJson().post(path, "Hello");

        RecordableHttpRequest requestRecording = getLast();

        assertEquals("Bar", requestRecording.getHeader("Foo"));
        assertEquals(MimeTypes.APPLICATION_JSON, requestRecording.getHeader("ContentType"));

    }

    @Test
    public void postXml() {

        String path = "/testpath";

        RestClient client = new RestClient("http://localhost:8080");
        client.sendXml().post(path, "Hello");

        RecordableHttpRequest requestRecording = getLast();

        assertEquals(MimeTypes.APPLICATION_XML, requestRecording.getHeader("ContentType"));

    }

    @Test
    public void postXmlWithHeaders() {

        String path = "/testpath";

        RestClient client = new RestClient("http://localhost:8080");
        client.withHeader("Foo", "Bar").sendXml().post(path, "Hello");

        RecordableHttpRequest requestRecording = getLast();

        assertEquals(MimeTypes.APPLICATION_XML, requestRecording.getHeader("ContentType"));
        assertEquals("Bar", requestRecording.getHeader("Foo"));

    }

}
