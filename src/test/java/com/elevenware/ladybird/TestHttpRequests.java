package com.elevenware.ladybird;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TestHttpRequests {

    @Test
    public void httpGetWorks() throws Exception {

        final Map responseRecording = new HashMap();

        final String message = "This is the response message";
        Server jettyServer = new Server(8080);
        jettyServer.setHandler(new AbstractHandler() {
            @Override
            public void handle(String target,
                               Request baseRequest,
                               HttpServletRequest request,
                               HttpServletResponse response) throws IOException, ServletException {

//                RecordedRequest recorded = RecordedRequest.fromHttpRequest(request);
//
//                Yaml yaml = new Yaml();
//                yaml.dump(recorded, new PrintWriter(System.out));


                response.setStatus(HttpStatusCodes.SC_OK);
                PrintStream out = new PrintStream(response.getOutputStream());
                out.println(message);
                response.flushBuffer();

            }
        });
        jettyServer.start();

        RestClient client = new RestClient();
        RestResponse response = client.get("http://localhost:8080/test");

        assertEquals(HttpStatusCodes.SC_OK, response.statusCode());
        assertEquals(message, response.getEntity());

        jettyServer.stop();

    }

}
