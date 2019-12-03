package org.jboss.resteasy.test.microprofile.restclient;

import java.util.concurrent.CompletionStage;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.reactivex.Single;

@Path("/")
@Produces("text/plain")
@RegisterRestClient(baseUri = "http://localhost:8080/RestClientProxyTest")
@RegisterClientHeaders(HeaderPropagator.class)
public interface HelloClient {

    @GET
    @Path("/hello")
    String hello();

    @GET
    @Path("some/{id}")
    Single<String> single(@PathParam("id") String id);

    @GET
    @Path("cs/{id}")
    CompletionStage<String> cs(@PathParam("id") String id);

    @GET
    @Path("async-client-target")
    CompletionStage<String> asyncClientTarget();

    @GET
    @Path("async-client")
    @ClientHeaderParam(name = "X-Propagated", value = "got-a-value")
    @ClientHeaderParam(name = "X-Not-Propagated", value = "got-a-value")
    CompletionStage<String> asyncClient();

    @GET
    @Path("client-target")
    String clientTarget();

    @GET
    @Path("client")
    @ClientHeaderParam(name = "X-Propagated", value = "got-a-value")
    @ClientHeaderParam(name = "X-Not-Propagated", value = "got-a-value")
    String client();
}