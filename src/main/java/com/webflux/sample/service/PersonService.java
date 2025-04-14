package com.webflux.sample.service;

import com.webflux.sample.model.PersonCreatedResponse;
import com.webflux.sample.model.PersonRequest;
import reactor.core.publisher.Mono;

public interface PersonService {
    Mono<PersonCreatedResponse> create(Mono<PersonRequest> createPersonRequest);
}
