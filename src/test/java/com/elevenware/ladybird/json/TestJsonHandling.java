package com.elevenware.ladybird.json;

import com.elevenware.ladybird.HttpResponse;
import com.elevenware.ladybird.client.LadybirdClient;
import com.elevenware.ladybird.entities.ContentHandler;
import com.elevenware.ladybird.entities.ContentHandlers;
import com.elevenware.ladybird.kit.AbstractHttpServingTestCase;
import com.elevenware.ladybird.model.Person;
import org.apache.http.entity.ContentType;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.Assert.*;

public class TestJsonHandling extends AbstractHttpServingTestCase {

    @Test
    public void canSendAndRecieveJson() {

        setRealHandler(new AbstractHandler() {
            @Override
            public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

                ContentHandler jsonHandler = ContentHandlers.getInstance().forType(ContentType.APPLICATION_JSON, null);

                Person person = jsonHandler.unmarshal(request.getInputStream(), Person.class);

                String other = jsonHandler.marshal(person);

                response.setContentType("application/json");
                response.getOutputStream().println(other);
                response.flushBuffer();
                baseRequest.setHandled(true);


            }
        });

        LadybirdClient client = LadybirdClient.forLocalhost();
        Person person = new Person();
        person.setName("Jim Reaper");
        person.setEmail("jim@reaper.com");

        HttpResponse response = client.sendJson().put("/people/jimreaper", person);
        Person other = response.getEntity(Person.class);

        assertEquals(person.getName(), other.getName());
        assertEquals(person.getEmail(), other.getEmail());


    }

}
