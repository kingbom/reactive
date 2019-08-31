package com.kingbom.reactive;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxTest {

    @Test
    public void whenFluxValueElementsIs_BMW_HONDA_TOYOTA_ShouldBePrintAll() {
        Flux<String> cars = Flux.just("BMW", "HONDA", "TOYOTA");
        cars.subscribe(System.out::println ,
                       (e) -> System.out.println("Exception : " + e),
                       () -> System.out.println("Completed"));
    }

    @Test
    public void whenFluxValueElementsIs_BMW_HONDA_TOYOTA_ShouldBeExpectNextCorrectAllAndExpectComplete() {
        Flux<String> cars = Flux.just("BMW", "HONDA", "TOYOTA");
        StepVerifier.create(cars)
                    .expectNext("BMW")
                    .expectNext("HONDA")
                    .expectNext("TOYOTA")
                    .verifyComplete();
    }

    @Test
    public void whenFluxValueElementsIs_BMW_HONDA_TOYOTA_ShouldBeRuntimeException() {
        Flux<String> cars = Flux.just("BMW", "HONDA", "TOYOTA")
                                .concatWith(Flux.error(new RuntimeException("RuntimeException error")));

        StepVerifier.create(cars)
                    .expectError(RuntimeException.class);
    }
}
