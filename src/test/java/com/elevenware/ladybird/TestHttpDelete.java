package com.elevenware.ladybird;

import com.elevenware.ladybird.client.RestClient;
import com.elevenware.ladybird.http.MimeTypes;
import com.elevenware.ladybird.kit.AbstractHttpRecordingTestCase;
import com.elevenware.ladybird.kit.RecordableHttpRequest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestHttpDelete extends AbstractHttpRecordingTestCase {

    @Test
    public void works() throws Exception {

        String path = "/testpath";

        RestClient client = new RestClient("http://localhost:8080");
        client.delete(path);

        RecordableHttpRequest requestRecording = getLast();

        assertEquals(path, requestRecording.getPathInfo());
        assertEquals("DELETE", requestRecording.getMethod());

    }

    @Test
    public void queryParamsArePassed() throws InterruptedException {

        String path = "/testpath";
        String params = "param1=hello";

        RestClient client = new RestClient("http://localhost:8080");
        client.delete(path.concat("?").concat(params));

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
        client.withHeader("Foo", "Bar").delete(path);

        RecordableHttpRequest requestRecording = getLast();

        assertEquals("Bar", requestRecording.getHeader("Foo"));

    }

}
