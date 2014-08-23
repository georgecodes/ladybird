package com.elevenware.ladybird.responses;

import com.elevenware.ladybird.HttpResponse;
import com.elevenware.ladybird.client.LadybirdClient;
import com.elevenware.ladybird.entities.UnmarshallingException;
import com.elevenware.ladybird.kit.AbstractHttpServingTestCase;
import com.elevenware.ladybird.kit.CannedResponseHandler;
import com.elevenware.ladybird.model.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.entity.ContentType;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestHttpGetResponses extends AbstractHttpServingTestCase {

    @Test
    public void getPlainText() throws Exception {

        final String responseMessage = "This is the expected response";

        ContextHandler handler =  new ContextHandler("/plain");
        handler.setHandler(new CannedResponseHandler(responseMessage));
        setRealHandler(handler);

        LadybirdClient client = LadybirdClient.forLocalhost();
        HttpResponse response = client.withHeader("Accept", ContentType.TEXT_PLAIN.getMimeType()).get("/plain");

        assertEquals(responseMessage, response.getEntity());

    }

    @Test
    public void getJson() throws Exception {

        final String responseMessage = "{\"name\":\"George McIntosh\",\"email\":\"george@elevenware.com\"}";

        ContextHandler handler =  new ContextHandler("/json");
        handler.setHandler(new CannedResponseHandler(responseMessage, ContentType.APPLICATION_JSON));
        setRealHandler(handler);

        LadybirdClient client = LadybirdClient.forLocalhost();
        HttpResponse response = client.acceptJson().get("/json");
        Person person = response.getEntity(Person.class);

        assertEquals("George McIntosh", person.getName());
        assertEquals("george@elevenware.com", person.getEmail());

    }

    @Test
    public void contentTypeCanBeOverriddenByAcceptHeader() throws Exception {

        final String responseMessage = "{\"name\":\"George McIntosh\",\"email\":\"george@elevenware.com\"}";

        ContextHandler handler =  new ContextHandler("/json");
        handler.setHandler(new CannedResponseHandler(responseMessage));
        setRealHandler(handler);

        LadybirdClient client = LadybirdClient.forLocalhost();
        HttpResponse response = client.acceptJson().get("/json");
        Person person = response.getEntity(Person.class);

        assertEquals("George McIntosh", person.getName());
        assertEquals("george@elevenware.com", person.getEmail());

    }

    @Test
    public void acceptHeaderIsNeededForUnmarshallingToWork() throws Exception {

        final String responseMessage = "{\"name\":\"George McIntosh\",\"email\":\"george@elevenware.com\"}";

        ContextHandler handler =  new ContextHandler("/json");
        handler.setHandler(new CannedResponseHandler(responseMessage));
        setRealHandler(handler);

        LadybirdClient client = LadybirdClient.forLocalhost();
        HttpResponse response = client.get("/json");
        Object person = response.getEntity(Person.class);

        assertEquals(responseMessage, person);

    }

    @Test( expected = UnmarshallingException.class)
    public void throwsExceptionIfContentIsNotActuallyJson() {

        final String responseMessage = "So this is not JSON";

        ContextHandler handler =  new ContextHandler("/json");
        handler.setHandler(new CannedResponseHandler(responseMessage));
        setRealHandler(handler);

        LadybirdClient client = LadybirdClient.forLocalhost();
        HttpResponse response = client.acceptJson().get("/json");
        Person person = response.getEntity(Person.class);

    }

}
