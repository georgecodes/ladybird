package com.elevenware.ladybird.kit;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class RequestRecordingJettyHandler extends AbstractHandler {

    private Deque<RecordableHttpRequest> requests;

    public RequestRecordingJettyHandler() {
        requests = new ArrayDeque<>();
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RecordableHttpRequest recording = new RecordableHttpRequest();
        recording.setPathInfo(request.getPathInfo());
        recording.setMethod(request.getMethod());
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            recording.addHeader(headerName, request.getHeader(headerName));
        }
        recording.setParameterMap(request.getParameterMap());
        recording.setQueryString(request.getQueryString());
        recording.recordInputStream(request.getInputStream());
        requests.push(recording);

        baseRequest.setHandled(false);
    }

    public Iterable<RecordableHttpRequest> getRequests() {
        return requests;
    }

    public RecordableHttpRequest getLast() {
        return requests.removeLast();
    }
}
