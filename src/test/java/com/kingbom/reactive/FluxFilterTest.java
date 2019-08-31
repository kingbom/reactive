package com.kingbom.reactive;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class FluxFilterTest {

    List<String> cars = Arrays.asList("BMW", "HONDA", "TOYOTA");

    @Test
    public void fluxFilterStartsWith() {
        Flux<String> fromIterable = Flux.fromIterable(cars)
                                        .filter(car -> car.startsWith("B"));

        StepVerifier.create(fromIterable)
                .expectNext("BMW")
                .verifyComplete();

    }

    @Test
    public void fluxFilterLength() {
        Flux<String> fromIterable = Flux.fromIterable(cars)
                                        .filter(car -> car.length() > 4);

        StepVerifier.create(fromIterable)
                .expectNext("HONDA")
                .expectNext("TOYOTA")
                .verifyComplete();

    }
}
