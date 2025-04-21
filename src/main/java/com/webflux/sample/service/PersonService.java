package com.webflux.sample.service;

import com.webflux.sample.model.PersonCreatedResponseBody;
import com.webflux.sample.model.PersonReadResponseBody;
import com.webflux.sample.model.PersonRequestBody;
import com.webflux.sample.model.PersonsReadResponseBody;
import reactor.core.publisher.Mono;

public interface PersonService {
    Mono<PersonCreatedResponseBody> create(Mono<PersonRequestBody> createPersonRequest);
    Mono<PersonReadResponseBody> find(String id);
    Mono<PersonsReadResponseBody> findAll();
}
