[![Build Status](https://travis-ci.org/georgecodes/ladybird.png?branch=master)](https://travis-ci.org/georgecodes/ladybird)


# Ladybird - a fluent API over Apache Http client

[The Apache Http components](http://hc.apache.org/) client is great and all, but feels a bit clunky to work with. I like fluent APIs. So I built this one
around Apache Http.

## Outline

All the hard work starts with an instance of com.elevenware.ladybird.client.LadybirdClient. There are a couple of options for obtaining one.

The original use case I had for this library, way back when it was part of another project, was for integration testing Java web apps. With that in mind, there's a factory method on the class for creating a client:

    LadybirdClient client = LadybirdClient.forLocal();
    client.get("/index.json"); // Will go to http://localhost:8080/index.json
    
Ladybird supports the creating of clients with a preset base Url

    LadybirdClient client = new LadybirdClient("http://myserver.com:8080");
    client.get("/index.json"); // Will go to http://myserver.com:8080/index.json
   
Or you can just create one with no base Url

    LadybirdClient client = new LadybirdClient();
    client.get("http://otherplace.com/foo.json"); // will go exactly where it's told
    
## Making requests

Making requests couldn't be simpler. The basic HTTP verbs are covered - GET, PUT, POST, DELETE. Other methods will be added later. The idea is, you call the method on the client, passing in the path and perhaps and entity, and get back a response.

### Very simple

    LadybirdClient client = LadybirdClient.forLocal();
    HttpResponse response = client.get("/");
    String body = response.getEntity();
     

