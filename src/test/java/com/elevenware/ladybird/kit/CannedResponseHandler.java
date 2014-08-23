package com.elevenware.ladybird.kit;

import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintStream;

public class CannedResponseHandler extends AbstractHandler {

    private final String message;
    private final ContentType contentType;

    public CannedResponseHandler() {
        this("Hello");
    }

    public CannedResponseHandler(String message){
        this(message, ContentType.TEXT_PLAIN);
    }
    public CannedResponseHandler(String message, ContentType contentType){
        this.message = message;
        this.contentType = contentType;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setStatus(HttpStatus.SC_OK);
        response.setContentType(contentType.getMimeType());
        PrintStream out = new PrintStream(response.getOutputStream());
        out.println(message);
        response.flushBuffer();
        baseRequest.setHandled(true);
    }
}
