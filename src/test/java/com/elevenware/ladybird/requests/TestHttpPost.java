package com.elevenware.ladybird.requests;

import com.elevenware.ladybird.client.LadybirdClient;
import com.elevenware.ladybird.kit.AbstractHttpRecordingTestCase;
import com.elevenware.ladybird.kit.RecordableHttpRequest;
import org.apache.http.entity.ContentType;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TestHttpPost extends AbstractHttpRecordingTestCase {

    @Test
    public void works() throws IOException, InterruptedException {

        String path = "/body";

        LadybirdClient client = new LadybirdClient("http://localhost:8080");
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

        LadybirdClient client = new LadybirdClient("http://localhost:8080");
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

        LadybirdClient client = new LadybirdClient("http://localhost:8080");
        client.withHeader("Foo", "Bar").post(path, "Hello");

        RecordableHttpRequest requestRecording = getLast();

        assertEquals("Bar", requestRecording.getHeader("Foo"));

    }

    @Test
    public void postJson() {

        String path = "/testpath";

        LadybirdClient client = new LadybirdClient("http://localhost:8080");
        client.sendJson().post(path, "Hello");

        RecordableHttpRequest requestRecording = getLast();

        assertEquals(ContentType.APPLICATION_JSON.getMimeType(), requestRecording.getHeader("ContentType"));

    }

    @Test
    public void postJsonWithHeaders() {

        String path = "/testpath";

        LadybirdClient client = new LadybirdClient("http://localhost:8080");
        client.withHeader("Foo", "Bar").sendJson().post(path, "Hello");

        RecordableHttpRequest requestRecording = getLast();

        assertEquals("Bar", requestRecording.getHeader("Foo"));
        assertEquals(ContentType.APPLICATION_JSON.getMimeType(), requestRecording.getHeader("ContentType"));

    }

    @Test
    public void postXml() {

        String path = "/testpath";

        LadybirdClient client = new LadybirdClient("http://localhost:8080");
        client.sendXml().post(path, "Hello");

        RecordableHttpRequest requestRecording = getLast();

        assertEquals(ContentType.APPLICATION_XML.getMimeType(), requestRecording.getHeader("ContentType"));

    }

    @Test
    public void postXmlWithHeaders() {

        String path = "/testpath";

        LadybirdClient client = new LadybirdClient("http://localhost:8080");
        client.withHeader("Foo", "Bar").sendXml().post(path, "Hello");

        RecordableHttpRequest requestRecording = getLast();

        assertEquals(ContentType.APPLICATION_XML.getMimeType(), requestRecording.getHeader("ContentType"));
        assertEquals("Bar", requestRecording.getHeader("Foo"));

    }

}
