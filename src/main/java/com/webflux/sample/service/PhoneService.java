package com.webflux.sample.service;

import com.webflux.sample.model.PhoneCreatedResponseBody;
import com.webflux.sample.model.PhoneReadResponseBody;
import com.webflux.sample.model.PhoneRequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PhoneService {
    Mono<PhoneCreatedResponseBody> create(Mono<PhoneRequestBody> createPhoneRequest);
    Mono<PhoneReadResponseBody> find(String id);
    Flux<PhoneReadResponseBody> findAll();
}
