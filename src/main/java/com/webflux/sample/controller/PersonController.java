package com.webflux.sample.controller;

import com.webflux.sample.model.PersonCreatedResponseBody;
import com.webflux.sample.model.PersonReadResponseBody;
import com.webflux.sample.model.PersonRequestBody;
import com.webflux.sample.model.PersonsReadResponseBody;
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
    public Mono<ResponseEntity<PersonCreatedResponseBody>> createPerson(
            Mono<PersonRequestBody> createPersonRequestBody,
            ServerWebExchange exchange
    ) {
        log.info("[MARKER:createPerson] - START");

        Mono<ResponseEntity<PersonCreatedResponseBody>> response = personService
                .create(createPersonRequestBody)
                .doFirst(() -> log.info(">>> Create person started"))
                .doOnTerminate(() -> log.info(">>> Create person finished"))
                .map(ResponseEntity::ok)
                .doOnSuccess(result -> log.info(">>> The createPerson result is {}", result))
                .doOnError(error -> log.error(">>> The createPerson error is {}", String.valueOf(error)));

        log.info("[MARKER:createPerson] - STOP");

        return response;
    }

    @Override
    public Mono<ResponseEntity<PersonReadResponseBody>> findPersonById(
            String personId,
            ServerWebExchange exchange
    ) {
        log.info("[MARKER:findPersonById] - START");

        Mono<ResponseEntity<PersonReadResponseBody>> response = personService
                .find(personId)
                .doFirst(() -> log.info(">>> findPersonById person started"))
                .doOnTerminate(() -> log.info(">>> findPersonById person finished"))
                .map(ResponseEntity::ok)
                .doOnSuccess(result -> log.info(">>> The findPersonById result is {}", result))
                .doOnError(error -> log.error(">>> The findPersonById error is {}", String.valueOf(error)));

        log.info("[MARKER:findPersonById] - STOP");

        return response;
    }

    @Override
    public Mono<ResponseEntity<PersonsReadResponseBody>> findPersons(
            Integer limit,
            Integer offset,
            String sort,
            ServerWebExchange exchange
    ) {
        return null;
    }

}
