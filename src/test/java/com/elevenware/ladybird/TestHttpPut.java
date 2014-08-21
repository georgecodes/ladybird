package com.elevenware.ladybird;

import com.elevenware.ladybird.client.RestClient;
import com.elevenware.ladybird.http.MimeTypes;
import com.elevenware.ladybird.kit.AbstractHttpRecordingTestCase;
import com.elevenware.ladybird.kit.RecordableHttpRequest;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TestHttpPut extends AbstractHttpRecordingTestCase {

    @Test
    public void works() throws IOException, InterruptedException {

        String path = "/body";

        RestClient client = new RestClient("http://localhost:8080");
        client.put(path, "Hello");

        RecordableHttpRequest requestRecording = getLast();

        assertEquals(path, requestRecording.getPathInfo());
        assertEquals("PUT", requestRecording.getMethod());

        String bodyAsString = requestRecording.consumeBodyAsString();
        assertEquals("Hello", bodyAsString);

    }

    @Test
    public void queryParamsArePassed() {

        String path = "/testpath";
        String params = "param1=hello";

        RestClient client = new RestClient("http://localhost:8080");
        client.put(path.concat("?").concat(params), "Hello there");

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
        client.withHeader("Foo", "Bar").put(path, "Hello");

        RecordableHttpRequest requestRecording = getLast();

        assertEquals("Bar", requestRecording.getHeader("Foo"));

    }

    @Test
    public void putJson() {

        String path = "/testpath";

        RestClient client = new RestClient("http://localhost:8080");
        client.sendJson().put(path, "Hello");

        RecordableHttpRequest requestRecording = getLast();

        assertEquals(MimeTypes.APPLICATION_JSON, requestRecording.getHeader("ContentType"));

    }

    @Test
    public void putJsonWithHeaders() {

        String path = "/testpath";

        RestClient client = new RestClient("http://localhost:8080");
        client.withHeader("Foo", "Bar").sendJson().put(path, "Hello");

        RecordableHttpRequest requestRecording = getLast();

        assertEquals("Bar", requestRecording.getHeader("Foo"));
        assertEquals(MimeTypes.APPLICATION_JSON, requestRecording.getHeader("ContentType"));

    }

    @Test
    public void putXml() {

        String path = "/testpath";

        RestClient client = new RestClient("http://localhost:8080");
        client.sendXml().put(path, "Hello");

        RecordableHttpRequest requestRecording = getLast();

        assertEquals(MimeTypes.APPLICATION_XML, requestRecording.getHeader("ContentType"));

    }

    @Test
    public void putXmlWithHeaders() {

        String path = "/testpath";

        RestClient client = new RestClient("http://localhost:8080");
        client.withHeader("Foo", "Bar").sendXml().put(path, "Hello");

        RecordableHttpRequest requestRecording = getLast();

        assertEquals(MimeTypes.APPLICATION_XML, requestRecording.getHeader("ContentType"));
        assertEquals("Bar", requestRecording.getHeader("Foo"));

    }

}
