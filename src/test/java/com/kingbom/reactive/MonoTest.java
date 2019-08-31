package com.kingbom.reactive;

import org.junit.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class MonoTest {

    @Test
    public void whenMonoCarIsBMW_ShouldBeExpectBMW() {
        Mono<String> car = Mono.just("BMW");
        StepVerifier.create(car)
                .expectNext("BMW")
                .verifyComplete();
    }

    @Test
    public void whenMonoCarIsBMW_ShouldBeExpectRuntimeException() {
        Mono<String> car = Mono.error(new RuntimeException("RuntimeException error"));
        StepVerifier.create(car)
                .expectError(RuntimeException.class)
                .verify();
    }
}
