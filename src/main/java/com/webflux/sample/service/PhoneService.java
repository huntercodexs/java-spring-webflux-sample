package com.webflux.sample.service;

import com.webflux.sample.model.PhoneCreatedResponseBody;
import com.webflux.sample.model.PhoneReadResponseBody;
import com.webflux.sample.model.PhoneRequestBody;
import reactor.core.publisher.Mono;

public interface PhoneService {
    Mono<PhoneCreatedResponseBody> create(String personId, Mono<PhoneRequestBody> createPhoneRequest);
    Mono<PhoneReadResponseBody> find(String id);
}
