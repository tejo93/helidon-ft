
package io.helidon.examples.quickstart.mp;

import java.util.concurrent.atomic.AtomicReference;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * Provider for greeting message.
 */
@ApplicationScoped
public class GreetingProvider {
    private final AtomicReference<String> message = new AtomicReference<>();
    private final AtomicReference<Integer> counter = new AtomicReference<>();

    /**
     * Create a new greeting provider, reading the message from configuration.
     *
     * @param message greeting to use
     */
    @Inject
    public GreetingProvider(@ConfigProperty(name = "app.greeting") String message, @ConfigProperty(name = "counter") String counter) {
        this.message.set(message);
        this.counter.set(Integer.parseInt(counter));
    }

    String getMessage() {
        return message.get();
    }
    int counter() { return counter.get();}

    void setMessage(String message) {
        this.message.set(message);
    }
    void setCounter(int counter) {
        this.counter.set(counter);
    }
}
