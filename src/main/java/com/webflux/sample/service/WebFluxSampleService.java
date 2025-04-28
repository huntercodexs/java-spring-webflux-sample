package com.webflux.sample.service;

import com.webflux.sample.exception.InternalErrorExceptionReactor;
import com.webflux.sample.model.WebFluxSampleModel;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@AllArgsConstructor
public class WebFluxSampleService {

    public Mono<Void> mono() {
        return Mono.empty();
    }

    public Flux<Void> flux() {
        return Flux.empty();
    }

    public Mono<Void> map() {
        return Mono.empty().map(mapper -> null);
    }

    public Mono<Void> flatMap() {
        return this.createSampleProduct("Shorts", 100.00,3)
                .doOnSuccess(success -> log.info("[ASYNC] --> flatMap OK"))
                .flatMap(product -> this.flatMapRun(product, 1, 1, 2))
                .flatMap(product -> this.flatMapRun(product, 2, 1, 2))
                .flatMap(product -> this.flatMapRun(product, 3, 1, 2))
                .map(check -> {
                    if ((check.getSampleProduct().getLast().getStock() == 12)) {
                        log.info("[ASYNC][SYNC] QUANTITY IS OK");
                    } else {
                        log.error("[ASYNC][SYNC] QUANTITY IS NOK {}", check.getSampleProduct().getLast().getStock());
                    }
                    return check;
                })
                .then();
    }

    public Mono<Void> flatMapAndZip() {
        log.info("[SYNC] #### => Calling Flat Map 1");
        Mono<WebFluxSampleModel> flatMap1 = flatMap1();
        log.info("[SYNC] #### => Calling Flat Map 2");
        Mono<WebFluxSampleModel> flatMap2 = flatMap2(this.extractProduct(flatMap1));
        log.info("[SYNC] #### => Calling Flat Map 3");
        Mono<WebFluxSampleModel> flatMap3 = flatMap3(this.extractProduct(flatMap2));
        return Mono.zip(flatMap1, flatMap2, flatMap3)
                .map(tuples -> {
                    List<WebFluxSampleModel.SampleProduct> productList = new ArrayList<>();
                    productList.add(tuples.getT1().getSampleProduct().getFirst());
                    productList.add(tuples.getT2().getSampleProduct().getFirst());
                    productList.add(tuples.getT3().getSampleProduct().getFirst());

                    WebFluxSampleModel webFluxSampleModel = new WebFluxSampleModel();
                    webFluxSampleModel.setSampleProduct(productList);

                    log.info("[RETURN] >>> Final Result {}", webFluxSampleModel);

                    return webFluxSampleModel;
                })
                .then();
    }

    public Mono<Void> then() {
        return Mono.just(new Object())
                .then(Mono.empty()
                        .then(Mono.just(new Object())
                                .then()));
    }

    public Mono<Object> thenReturn() {
        return this.createSampleProduct("Shorts", 100.00,3)
                .doOnSuccess(success -> log.info("[ASYNC] --> thenReturn OK"))
                .flatMap(product -> this.flatMapRun(product, 1, 1, 2))
                .thenReturn(Mono.empty());
    }

    public Mono<WebFluxSampleModel> switchIfEmpty() {
        return this.findProduct(false)
                .doOnSuccess(success -> log.info("[ASYNC] --> switchIfEmpty OK"))
                .flatMap(product -> this.flatMapRun(product, 1, 1, 2))
                .switchIfEmpty(this.findProduct(true))
                .map(found -> {
                    log.info("[SWITCH IF EMPTY] {}", found);
                    return found;
                });
    }

    public Mono<WebFluxSampleModel> zip() {
        Mono<WebFluxSampleModel> flatMap1 = flatMap1();
        Mono<WebFluxSampleModel> flatMap2 = flatMap2(this.extractProduct(flatMap1));
        Mono<WebFluxSampleModel> flatMap3 = flatMap3(this.extractProduct(flatMap2));
        return Mono.zip(flatMap1, flatMap2, flatMap3).map(tuples -> {

            List<WebFluxSampleModel.SampleProduct> productList = new ArrayList<>();
            productList.add(tuples.getT1().getSampleProduct().getFirst());
            productList.add(tuples.getT2().getSampleProduct().getFirst());
            productList.add(tuples.getT3().getSampleProduct().getFirst());

            WebFluxSampleModel webFluxSampleModel = new WebFluxSampleModel();
            webFluxSampleModel.setSampleProduct(productList);

            return webFluxSampleModel;
        });
    }

    public Mono<WebFluxSampleModel> zipWith() {
        Mono<WebFluxSampleModel> flatMap1 = flatMap1();
        Mono<WebFluxSampleModel> flatMap2 = flatMap2(this.extractProduct(flatMap1));
        return flatMap1.zipWith(flatMap2).map(tuples -> {

            List<WebFluxSampleModel.SampleProduct> productList = new ArrayList<>();
            productList.add(tuples.getT1().getSampleProduct().getFirst());
            productList.add(tuples.getT2().getSampleProduct().getFirst());

            WebFluxSampleModel webFluxSampleModel = new WebFluxSampleModel();
            webFluxSampleModel.setSampleProduct(productList);

            return webFluxSampleModel;
        });
    }

    public Mono<WebFluxSampleModel> zipWhen() {
        Mono<WebFluxSampleModel> flatMap1 = flatMap1();
        return flatMap1.zipWhen(name -> {
            return this.findProductByName(name.getSampleProduct().getFirst().getName(), false);
        }).map(tuples -> {

            List<WebFluxSampleModel.SampleProduct> productList = new ArrayList<>();
            productList.add(tuples.getT1().getSampleProduct().getFirst());

            WebFluxSampleModel webFluxSampleModel = new WebFluxSampleModel();
            webFluxSampleModel.setSampleProduct(productList);

            return webFluxSampleModel;
        });
    }

    public Mono<Void> subscribe() {
        Flux<Double> doubles = Flux.just(Math.random(), Math.random(), Math.random(), Math.random(), Math.random())
                .map(n -> {
                    if (n < 0) {
                        throw new InternalErrorExceptionReactor("[double] Invalid Random Number " + n);
                    }
                    return n * 2;
                }).delaySubscription(Duration.ofSeconds(5));

        doubles.subscribe(
                value -> System.out.println(">> [double] Received: " + value),
                error -> System.err.println("[double] Error: " + error.getMessage()),
                () -> System.out.println("[double] Completed")
        );

        Flux<Integer> integers = Flux.just(Math.max(9, 100), Math.max(0, 100), Math.max(100,1000), Math.max(20,100))
                .map(n -> {
                    if (n < 10) {
                        throw new InternalErrorExceptionReactor("[integer] Invalid Random Number " + n);
                    }
                    return n * 2;
                }).delaySubscription(Duration.ofSeconds(2));

        integers.subscribe(
                value -> System.out.println(">> [integer] Received: " + value),
                error -> System.err.println("[integer] Error: " + error.getMessage()),
                () -> System.out.println("[integer] Completed")
        );

        Mono<String> text = Mono.just("text for tests")
                .map(n -> n + " processed").delaySubscription(Duration.ofSeconds(1));

        text.subscribe(
                value -> System.out.println(">> [text] Received: " + value),
                error -> System.err.println("[text] Error: " + error.getMessage()),
                () -> System.out.println("[text] Completed")
        );

        return Mono.empty();
    }

    private Mono<WebFluxSampleModel> flatMap1() {
        return this.createSampleProduct("T-Shirt", 15.00, 5)
                .doOnSuccess(success -> log.info("[ASYNC] --> Flat Map 1 OK"))
                .flatMap(obj -> this.flatMapRun(obj, 1, 1, 6))
                .flatMap(obj -> this.flatMapRun(obj, 2, 1, 6))
                .map(product -> product)
                .delaySubscription(Duration.ofSeconds(3));
    }

    private Mono<WebFluxSampleModel> flatMap2(WebFluxSampleModel webFluxSampleModel) {
        return this.addSampleProduct(webFluxSampleModel, "Shorts", 44.00, 5)
                .doOnSuccess(success -> log.info("[ASYNC] --> Flat Map 2 OK"))
                .flatMap(obj -> this.flatMapRun(obj, 1, 2, 4))
                .flatMap(obj -> this.flatMapRun(obj, 2, 2, 4))
                .flatMap(obj -> this.flatMapRun(obj, 3, 2, 4))
                .flatMap(obj -> this.flatMapRun(obj, 4, 2, 4))
                .flatMap(obj -> this.flatMapRun(obj, 5, 2, 4))
                .delaySubscription(Duration.ofSeconds(2));
    }

    private Mono<WebFluxSampleModel> flatMap3(WebFluxSampleModel webFluxSampleModel) {
        return this.addSampleProduct(webFluxSampleModel, "Pants", 60.00, 25)
                .doOnSuccess(success -> log.info("[ASYNC] --> Flat Map 3 OK"))
                .flatMap(obj -> this.flatMapRun(obj, 1, 3, 2))
                .flatMap(obj -> this.flatMapRun(obj, 2, 3, 2))
                .flatMap(obj -> this.flatMapRun(obj, 3, 3, 2))
                .flatMap(obj -> this.flatMapRun(obj, 4, 3, 2))
                .delaySubscription(Duration.ofSeconds(1));
    }

    private Mono<WebFluxSampleModel> flatMapRun(WebFluxSampleModel webFluxSampleModel, int step, int from, int seconds) {
        log.info("[ASYNC][STEP:{}][FROM:{}] >> Flat Map {}", step, from, webFluxSampleModel);
        return Mono.just(webFluxSampleModel).delaySubscription(Duration.ofSeconds(seconds));
    }

    private Mono<WebFluxSampleModel> createSampleProduct(String name, double price, int stock) {
        return this.addSampleProduct(new WebFluxSampleModel(), name, price, stock);
    }

    private Mono<WebFluxSampleModel> addSampleProduct(WebFluxSampleModel webFluxSampleModel, String name, double price, int stock) {
        WebFluxSampleModel.SampleProduct sampleProduct = new WebFluxSampleModel.SampleProduct();
        sampleProduct.setName(name);
        sampleProduct.setPrice(price);
        sampleProduct.setStock(stock);
        webFluxSampleModel.getSampleProduct().add(sampleProduct);
        return Mono.just(webFluxSampleModel).doFirst(() -> log.info(">>> GET {}", webFluxSampleModel.getSampleProduct()));
    }

    private WebFluxSampleModel extractProduct(Mono<WebFluxSampleModel> webFluxSampleModelMono) {
        WebFluxSampleModel model = new WebFluxSampleModel();
        webFluxSampleModelMono.map(mapper -> {
            model.setSampleProduct(mapper.getSampleProduct());
            return model;
        });
        return model;
    }

    private Mono<WebFluxSampleModel> findProduct(boolean shouldCreate) {
        if (!shouldCreate)
            return Mono.empty();
        return this.createSampleProduct("test", 100.00, 4);
    }

    private Mono<WebFluxSampleModel> findProductByName(String name, boolean simulateNotFound) {
        if (simulateNotFound)
            return Mono.empty();
        return this.createSampleProduct(name, 100.00, 4);
    }

}
