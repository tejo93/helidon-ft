package io.helidon.examples.quickstart.mp;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.faulttolerance.Asynchronous;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.concurrent.CompletableFuture;

@RegisterRestClient(configKey = "someClient")
@Retry
@Timeout
@Asynchronous
public interface SomeClient {
    @GET
    @Produces("application/json")
    @Path("/somepath")
    CompletableFuture<Response> get();
}
