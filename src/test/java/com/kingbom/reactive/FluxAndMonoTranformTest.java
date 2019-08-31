package com.kingbom.reactive;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static reactor.core.scheduler.Schedulers.parallel;

public class FluxAndMonoTranformTest {

    List<String> cars = Arrays.asList("BMW", "HONDA", "TOYOTA");

    @Test
    public void fluxUsingMap_toLowerCase() {
        Flux<String> fluxCars = Flux.fromIterable(cars)
                                    .map(car -> car.toLowerCase());

        StepVerifier.create(fluxCars)
                    .expectNext("bmw", "honda", "toyota")
                    .verifyComplete();
    }

    @Test
    public void fluxUsingMap_Length() {
        Flux<Integer> fluxCars = Flux.fromIterable(cars)
                                     .map(car -> car.length());

        StepVerifier.create(fluxCars)
                .expectNext(3, 5, 6)
                .verifyComplete();
    }

    @Test
    public void fluxUsingMap_Length_Repeat() {
        Flux<Integer> fluxCars = Flux.fromIterable(cars)
                                     .map(car -> car.length())
                                     .repeat(1);

        StepVerifier.create(fluxCars)
                .expectNext(3, 5, 6,3, 5, 6)
                .verifyComplete();
    }

    @Test
    public void fluxUsingMap_Filter_toLowerCase() {
        Flux<String> fluxCars = Flux.fromIterable(cars)
                                    .filter(car -> car.length() > 5)
                                    .map(car -> car.toLowerCase());

        StepVerifier.create(fluxCars)
                .expectNext("toyota")
                .verifyComplete();
    }

    @Test
    public void fluxUsingFlatMap() {
        Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("A", "B", "C" ,"D" ,"E", "F"))
                                      .flatMap( s-> Flux.fromIterable(convertToList(s))).log();//db or external server call that returns a flux String

        StepVerifier.create(stringFlux)
                    .expectNextCount(12)
                    .verifyComplete();
        //19:25:07 - 19:25:13 = 6 sec
    }

    @Test
    public void fluxUsingFlatMap_usingParallel() {
        Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("A", "B", "C" ,"D" ,"E", "F"))
                                      .window(2) //Flux<String> -> (A,B), (C,D) ,(E,F)
                                      .flatMap((s) -> s.map(this::convertToList).subscribeOn(parallel()))
                                      .flatMap(s-> Flux.fromIterable(s))
                                      .log();//db or external server call that returns a flux String

        StepVerifier.create(stringFlux)
                .expectNextCount(12)
                .verifyComplete();

        //19:25:47 - 19:25:51 = 4 sec

    }

    @Test
    public void fluxUsingconcatMap_usingParallel_maintain_order() {
        Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("A", "B", "C" ,"D" ,"E", "F"))
                                      .window(2) //Flux<String> -> (A,B), (C,D) ,(E,F)
                                      .concatMap((s) -> s.map(this::convertToList).subscribeOn(parallel()))
                                      .flatMap(s-> Flux.fromIterable(s))
                                      .log();//db or external server call that returns a flux String

        StepVerifier.create(stringFlux)
                .expectNextCount(12)
                .verifyComplete();

        //19:25:47 - 19:25:51 = 4 sec

    }

    @Test
    public void fluxUsingFlatMapSequential_usingParallel_maintain_order() {
        Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("A", "B", "C" ,"D" ,"E", "F"))
                                      .window(2) //Flux<String> -> (A,B), (C,D) ,(E,F)
                                      .flatMapSequential((s) -> s.map(this::convertToList).subscribeOn(parallel()))
                                       .flatMap(s-> Flux.fromIterable(s))
                                       .log();//db or external server call that returns a flux String

        StepVerifier.create(stringFlux)
                .expectNextCount(12)
                .verifyComplete();

        //19:25:47 - 19:25:51 = 4 sec

    }

    private List<String> convertToList(String s) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Arrays.asList(s, "newValue");
    }
}
