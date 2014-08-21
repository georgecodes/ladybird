package com.elevenware.ladybird;

import com.elevenware.ladybird.client.RestClient;
import com.elevenware.ladybird.http.MimeTypes;
import com.elevenware.ladybird.kit.AbstractHttpRecordingTestCase;
import com.elevenware.ladybird.kit.RecordableHttpRequest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestHttpGet extends AbstractHttpRecordingTestCase {

    @Test
    public void works() throws Exception {

        String path = "/testpath";

        RestClient client = new RestClient("http://localhost:8080");
        client.get(path);

        RecordableHttpRequest requestRecording = getLast();

        assertEquals(path, requestRecording.getPathInfo());
        assertEquals("GET", requestRecording.getMethod());

    }

    @Test
    public void queryParamsArePassed() throws InterruptedException {

        String path = "/testpath";
        String params = "param1=hello";

        RestClient client = new RestClient("http://localhost:8080");
        client.get(path.concat("?").concat(params));

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
        client.withHeader("Foo", "Bar").get(path);

        RecordableHttpRequest requestRecording = getLast();

        assertEquals("Bar", requestRecording.getHeader("Foo"));

    }

    @Test
    public void getJson() {

        String path = "/testpath";

        RestClient client = new RestClient("http://localhost:8080");
        client.acceptJson().get(path);

        RecordableHttpRequest requestRecording = getLast();

        assertEquals(MimeTypes.APPLICATION_JSON, requestRecording.getHeader("Accept"));

    }

    @Test
    public void getJsonWithHeaders() {

        String path = "/testpath";

        RestClient client = new RestClient("http://localhost:8080");
        client.withHeader("Foo", "Bar").acceptJson().get(path);

        RecordableHttpRequest requestRecording = getLast();

        assertEquals("Bar", requestRecording.getHeader("Foo"));
        assertEquals(MimeTypes.APPLICATION_JSON, requestRecording.getHeader("Accept"));

    }

    @Test
    public void getXml() {

        String path = "/testpath";

        RestClient client = new RestClient("http://localhost:8080");
        client.acceptXml().get(path);

        RecordableHttpRequest requestRecording = getLast();

        assertEquals(MimeTypes.APPLICATION_XML, requestRecording.getHeader("Accept"));

    }

    @Test
    public void getXmlWithHeaders() {

        String path = "/testpath";

        RestClient client = new RestClient("http://localhost:8080");
        client.withHeader("Foo", "Bar").acceptXml().get(path);

        RecordableHttpRequest requestRecording = getLast();

        assertEquals("Bar", requestRecording.getHeader("Foo"));
        assertEquals(MimeTypes.APPLICATION_XML, requestRecording.getHeader("Accept"));

    }

}
