package com.elevenware.ladybird.kit;

import com.elevenware.ladybird.http.HttpStatus;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintStream;

public class SayHelloJettyHandler extends AbstractHandler {

    private final String message;

    public SayHelloJettyHandler() {
        this("Hello");
    }

    public SayHelloJettyHandler(String message){
        this.message = message;
    }
    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setStatus(HttpStatus.SC_OK);
        PrintStream out = new PrintStream(response.getOutputStream());
        out.println(message);
        response.flushBuffer();
        baseRequest.setHandled(true);
    }
}
