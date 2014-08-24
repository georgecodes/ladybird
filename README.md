[![Build Status](https://travis-ci.org/georgecodes/ladybird.png?branch=master)](https://travis-ci.org/georgecodes/ladybird)


# Ladybird - a fluent API over Apache Http client

[The Apache Http components](http://hc.apache.org/) client is great and all, but feels a bit clunky to work with. I like fluent APIs. So I built this one
around Apache Http.

## Getting hold of a client

All the hard work starts with an instance of com.elevenware.ladybird.client.LadybirdClient. There are a couple of options for obtaining one.

The original use case I had for this library, way back when it was part of another project, was for integration testing Java web apps. With that in mind, there's a factory method on the class for creating a client:

    LadybirdClient client = LadybirdClient.forLocalhost();
    client.get("/index.json"); // Will go to http://localhost:8080/index.json
    
Ladybird supports the creating of clients with a preset base Url

    LadybirdClient client = new LadybirdClient("http://myserver.com:8080");
    client.get("/index.json"); // Will go to http://myserver.com:8080/index.json
   
Or you can just create one with no base Url

    LadybirdClient client = new LadybirdClient();
    client.get("http://otherplace.com/foo.json"); // will go exactly where it's told
    
## Making requests

Making requests couldn't be simpler. The basic HTTP verbs are covered - GET, PUT, POST, DELETE. Other methods will be added later. The idea is, you call the method on the client, passing in the path and perhaps and entity, and get back a response.

### Very simple GET

    LadybirdClient client = LadybirdClient.forLocalhost();
    HttpResponse response = client.get("/resources/first");
    String body = response.getEntity();
     
### PUT

    LadybirdClient client = LadybirdClient.forLocalhost();
    HttpResponse response = client.put("/resources/first", "Hello");

### POST

    LadybirdClient client = LadybirdClient.forLocalhost();
    HttpResponse response = client.post("/resources/first", "Goodbye");

### DELETE

    LadybirdClient client = LadybirdClient.forLocalhost();
    HttpResponse response = client.delete("/resources/first");

All very straightforward. The HttpResponse contains the returned entity, if any, as a string, but also - as we'll see later - as other types.

## The fluent API

The fluent interface is the big win with Ladybird. Simplicity, readability, no cruft: that's the name of the game

### Specifying content types

You can easily specify a number of common accept header types with no fuss

    HttpResponse response = LadybirdClient.forLocalhost()
            .acceptJson()
            .get("/myjsonresource/first");

Same with content types. The sendJson() method doesn't send anything, it merely adds a content type header.

    LadybirdClient.forLocalhost()
            .sendJson()
            .put("/myjsonresource/first", jsonString);


Arbitrary headers can be added

    LadybirdClient.forLocalhost()
            .withHeader("X-token", "a98fdb9e9f2047abb3c")
            .acceptJson()
            .sendJson()
            .post("/resources/second", jsonString);

The Http verb is the terminating method, it must be called last. All the others - withHeader, acceptJson, sendJson et al - can be called in any order

    // perfectly valid
    LadybirdClient.forLocalhost()
            .sendJson()
            .withHeader("X-token", "a98fdb9e9f2047abb3c")
            .acceptJson()
            .post("/resources/second", jsonString);

## Working with JSON

I assume JSON needs no introduction, but just in case, it's a re-purposing of Javascript object notation as a data format

    { "name" : "Jim Reaper", "email" : "jim@reaper.com" }

It's so prevalent in writing modern RESTful web services, that Ladybird supports it natively. Here's an example of sending JSON, and receiving a JSON response

    LadybirdClient client = LadybirdClient.forLocalhost();
    Person person = new Person();
    person.setName("Jim Reaper");
    person.setEmail("jim@reaper.com");

    HttpResponse response = client.sendJson().put("/people/jimreaper", person);
    Person other = response.getEntity(Person.class);

    assertEquals(person.getName(), other.getName());
    assertEquals(person.getEmail(), other.getEmail());

Ladybird uses [Jackson](http://jackson.codehaus.org/) for JSON marshalling and unmarshalling. As you can see, it's generally trivial, and you don't have to do too much work to send and recieve objects as JSON.

## Working with other content types

The other popular format, of course, is XML. However, there are so many different ways that we can handle XML, that I've decided not to support XML directly in Ladybird. Instead, XML can be handled by including and registering a specific library for content handling. Writing these should be relatively trivial, I intend to provide a JAXB extension myself.