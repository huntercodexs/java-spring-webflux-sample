package com.webflux.sample.service;

import com.webflux.sample.document.ExampleDocument;
import com.webflux.sample.dto.ExampleRequest;
import reactor.core.publisher.Mono;

public interface ExampleService {
    Mono<ExampleDocument> execute(Mono<ExampleRequest> exampleRequest);
}
