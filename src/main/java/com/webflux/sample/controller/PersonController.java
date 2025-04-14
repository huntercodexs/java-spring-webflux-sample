package com.webflux.sample.controller;

import com.webflux.sample.model.PersonCreatedResponse;
import com.webflux.sample.model.PersonListResponse;
import com.webflux.sample.model.PersonRequest;
import com.webflux.sample.person.api.PersonApi;
import com.webflux.sample.service.PersonService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Log4j2
@RestController
@AllArgsConstructor
public class PersonController implements BaseController, PersonApi {

    private PersonService personService;

    @Override
    public Mono<ResponseEntity<PersonCreatedResponse>> createPerson(
            Mono<PersonRequest> createPersonRequest,
            ServerWebExchange exchange
    ) {
        log.info("[MARKER:createPerson] - START");

        Mono<ResponseEntity<PersonCreatedResponse>> response = personService.create(createPersonRequest)
                .doFirst(() -> log.info(">>> Create person started"))
                .doOnTerminate(() -> log.info(">>> Create person finished"))
                .map(ResponseEntity::ok)
                .doOnSuccess(result -> log.info(">>> The result is {}", result))
                .doOnError(error -> log.error(">>> The error is {}", String.valueOf(error)));

        log.info("[MARKER:createPerson] - STOP");

        return response;
    }

    @Override
    public Mono<ResponseEntity<PersonListResponse>> getPersons(
            Integer limit,
            Integer offset,
            String sort,
            ServerWebExchange exchange
    ) {
        return null;
    }
}
