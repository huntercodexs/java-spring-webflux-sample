package com.webflux.sample.service;

import com.webflux.sample.model.*;
import reactor.core.publisher.Mono;

public interface PersonService {
    Mono<PersonCreatedResponseBody> create(Mono<PersonRequestBody> createPersonRequest);
    Mono<PersonReadResponseBody> read(String personId);
    Mono<PersonsReadResponseBody> readAll();
    Mono<PersonReadResponseBody> update(String personId, Mono<PersonRequestBody> updatePersonRequest);
    Mono<GenericsResponseBody> delete(String personId);
    Mono<GenericsResponseBody> patch(String personId, Mono<PersonPatchRequestBody> patchRequestBodyMono);
    Mono<GenericsResponseBody> patchByPath(String personId, String fieldName, Object fieldValue);
}
