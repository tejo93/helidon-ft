
package io.helidon.examples.quickstart.mp;

import io.helidon.microprofile.testing.junit5.AddConfig;
import io.helidon.microprofile.testing.junit5.HelidonTest;
import io.specto.hoverfly.junit.core.Hoverfly;
import io.specto.hoverfly.junit.core.SimulationSource;
import io.specto.hoverfly.junit5.HoverflyExtension;
import io.specto.hoverfly.junit5.api.HoverflyConfig;
import io.specto.hoverfly.junit5.api.HoverflyCore;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static io.specto.hoverfly.junit.dsl.HoverflyDsl.service;
import static io.specto.hoverfly.junit.dsl.ResponseCreators.success;

@HelidonTest
@AddConfig(key = "someClient/mp-rest/url", value = "http://localhost:9999")
@HoverflyCore(config = @HoverflyConfig(webServer = true, proxyPort = 9999))
@ExtendWith(HoverflyExtension.class)
class SomeOtherTest {

    @Inject
    private WebTarget target;
    @Inject
    @RestClient
    SomeClient someClient;

    @Test
    void testHelloWorld(Hoverfly hoverfly) throws ExecutionException, InterruptedException {
        hoverfly.simulate(SimulationSource.dsl(
                service("http://localhost:9999")
                        .get("/somepath")
                        .willReturn(success()
                                .withFixedDelay(2, TimeUnit.SECONDS))));
        CompletableFuture<Response> response = someClient.get();
        Assertions.assertEquals(response.get().getStatus(), 200);
    }
}
