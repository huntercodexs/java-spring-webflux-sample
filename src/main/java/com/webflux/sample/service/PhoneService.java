package com.webflux.sample.service;

import com.webflux.sample.user_details.model.PhoneCreatedResponseBody;
import com.webflux.sample.user_details.model.PhoneReadResponseBody;
import com.webflux.sample.user_details.model.PhoneRequestBody;
import reactor.core.publisher.Mono;

public interface PhoneService {
    Mono<PhoneCreatedResponseBody> create(String userId, Mono<PhoneRequestBody> createPhoneRequest);
    Mono<PhoneReadResponseBody> find(String id);
}
