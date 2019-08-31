package com.kingbom.reactive;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class FluxAndMonoFactoryTest {

    List<String> cars = Arrays.asList("BMW", "HONDA", "TOYOTA");

    @Test
    public void fluxUsingIterable() {

        Flux<String> fluxCars  = Flux.fromIterable(cars);

        StepVerifier.create(fluxCars)
                .expectNext("BMW")
                .expectNext("HONDA")
                .expectNext("TOYOTA")
                .verifyComplete();
    }

    @Test
    public void fluxUsingArray() {
        String[] cars = new String[]{"BMW", "HONDA", "TOYOTA"};
        Flux<String> fluxCars  = Flux.fromArray(cars);

        StepVerifier.create(fluxCars)
                .expectNext("BMW")
                .expectNext("HONDA")
                .expectNext("TOYOTA")
                .verifyComplete();
    }

    @Test
    public void fluxUsingStream() {
        Flux<String> fluxCars  = Flux.fromStream(cars.stream());
        StepVerifier.create(fluxCars)
                .expectNext("BMW")
                .expectNext("HONDA")
                .expectNext("TOYOTA")
                .verifyComplete();

    }

    @Test
    public void fluxUsingRange() {
        Flux<Integer> integerFlux = Flux.range(1, 5);
        StepVerifier.create(integerFlux)
                    .expectNext(1, 2,3, 4,5)
                    .verifyComplete();


    }

    @Test
    public void monoUsingJustOrEmpty() {
        Mono<String> mono = Mono.justOrEmpty(null);
        StepVerifier.create(mono.log())
                .verifyComplete();
    }

    @Test
    public void monoUsingSupplier() {
        Supplier<String> stringSupplier = () -> "BMW";
        Mono<String> mono = Mono.fromSupplier(stringSupplier);
        StepVerifier.create(mono)
                    .expectNext("BMW")
                    .verifyComplete();
    }
}
