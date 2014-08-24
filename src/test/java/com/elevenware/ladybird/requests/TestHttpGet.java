package com.elevenware.ladybird.requests;

import com.elevenware.ladybird.client.LadybirdClient;
import com.elevenware.ladybird.kit.AbstractHttpRecordingTestCase;
import com.elevenware.ladybird.kit.RecordableHttpRequest;
import org.apache.http.entity.ContentType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestHttpGet extends AbstractHttpRecordingTestCase {

    @Test
    public void works() throws Exception {

        String path = "/testpath";

        LadybirdClient client = LadybirdClient.forLocalhost();
        client.get(path);

        RecordableHttpRequest requestRecording = getLast();

        assertEquals(path, requestRecording.getPathInfo());
        assertEquals("GET", requestRecording.getMethod());

    }

    @Test
    public void queryParamsArePassed() throws InterruptedException {

        String path = "/testpath";
        String params = "param1=hello";

        LadybirdClient client = LadybirdClient.forLocalhost();
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

        LadybirdClient client = LadybirdClient.forLocalhost();
        client.withHeader("Foo", "Bar").get(path);

        RecordableHttpRequest requestRecording = getLast();

        assertEquals("Bar", requestRecording.getHeader("Foo"));

    }

    @Test
    public void getJson() {

        String path = "/testpath";

        LadybirdClient client = LadybirdClient.forLocalhost();
        client.acceptJson().get(path);

        RecordableHttpRequest requestRecording = getLast();

        assertEquals(ContentType.APPLICATION_JSON.getMimeType(), requestRecording.getHeader("Accept"));

    }

    @Test
    public void getJsonWithHeaders() {

        String path = "/testpath";

        LadybirdClient client = LadybirdClient.forLocalhost();
        client.withHeader("Foo", "Bar").acceptJson().get(path);

        RecordableHttpRequest requestRecording = getLast();

        assertEquals("Bar", requestRecording.getHeader("Foo"));
        assertEquals(ContentType.APPLICATION_JSON.getMimeType(), requestRecording.getHeader("Accept"));

    }

    @Test
    public void getXml() {

        String path = "/testpath";

        LadybirdClient client = LadybirdClient.forLocalhost();
        client.acceptXml().get(path);

        RecordableHttpRequest requestRecording = getLast();

        assertEquals(ContentType.APPLICATION_XML.getMimeType(), requestRecording.getHeader("Accept"));

    }

    @Test
    public void getXmlWithHeaders() {

        String path = "/testpath";

        LadybirdClient client = LadybirdClient.forLocalhost();
        client.withHeader("Foo", "Bar").acceptXml().get(path);

        RecordableHttpRequest requestRecording = getLast();

        assertEquals("Bar", requestRecording.getHeader("Foo"));
        assertEquals(ContentType.APPLICATION_XML.getMimeType(), requestRecording.getHeader("Accept"));

    }

    @Test
    public void canProvideBasePathAtConstruction() {

        LadybirdClient client = new LadybirdClient("http://localhost:8080");
        client.get("");

        RecordableHttpRequest requestRecording = getLast();

        assertEquals("/", requestRecording.getPathInfo());

    }

    @Test
    public void canNotProvideBasePathAtConstruction() {

        LadybirdClient client = new LadybirdClient();
        client.get("http://localhost:8080");

        RecordableHttpRequest requestRecording = getLast();

        assertEquals("/", requestRecording.getPathInfo());

    }

}
