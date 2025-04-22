package com.webflux.sample.service;

import com.webflux.sample.model.PersonCreatedResponseBody;
import com.webflux.sample.model.PersonReadResponseBody;
import com.webflux.sample.model.PersonRequestBody;
import com.webflux.sample.model.PersonsReadResponseBody;
import reactor.core.publisher.Mono;

public interface PersonService {
    Mono<PersonCreatedResponseBody> create(Mono<PersonRequestBody> createPersonRequest);
    Mono<PersonReadResponseBody> find(String personId);
    Mono<PersonsReadResponseBody> findAll();
    Mono<PersonReadResponseBody> update(String personId, Mono<PersonRequestBody> updatePersonRequest);
    Mono<Void> delete(String personId);
}
