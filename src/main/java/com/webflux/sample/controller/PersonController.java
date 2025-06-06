package com.webflux.sample.controller;

import com.webflux.sample.model.*;
import com.webflux.sample.person.api.PersonApi;
import com.webflux.sample.service.PersonService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.*;

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

        Mono<ResponseEntity<PersonCreatedResponseBody>> response = personService.create(createPersonRequestBody)
                .doFirst(() -> log.info(">>> createPerson started"))
                .doOnTerminate(() -> log.info(">>> createPerson finished"))
                .doOnSuccess(success -> log.info(">>> The createPerson result is {}", success))
                .doOnError(error -> log.error(">>> The createPerson error is {}", String.valueOf(error)))
                .map(body -> ResponseEntity.status(CREATED).body(body));

        log.info("[MARKER:createPerson] - STOP");

        return response;
    }

    @Override
    public Mono<ResponseEntity<PersonReadResponseBody>> readOnePerson(
            String personId,
            ServerWebExchange exchange
    ) {
        log.info("[MARKER:findPersonById] - START");

        Mono<ResponseEntity<PersonReadResponseBody>> response = personService.read(personId)
                .doFirst(() -> log.info(">>> findPersonById started"))
                .doOnTerminate(() -> log.info(">>> findPersonById finished"))
                .doOnSuccess(result -> log.info(">>> The findPersonById result is {}", result))
                .doOnError(error -> log.error(">>> The findPersonById error is {}", String.valueOf(error)))
                .map(body -> ResponseEntity.status(OK).body(body));

        log.info("[MARKER:findPersonById] - STOP");

        return response;
    }

    @Override
    public Mono<ResponseEntity<PersonsReadResponseBody>> readAllPersons(ServerWebExchange exchange) {
        log.info("[MARKER:findPersons] - START");

        Mono<ResponseEntity<PersonsReadResponseBody>> response = personService.readAll()
                .doFirst(() -> log.info(">>> findPersons persons started"))
                .doOnTerminate(() -> log.info(">>> findPersons persons finished"))
                .doOnSuccess(result -> log.info(">>> The findPersons result is {}", result))
                .doOnError(error -> log.error(">>> The findPersons error is {}", String.valueOf(error)))
                .map(body -> ResponseEntity.status(OK).body(body));

        log.info("[MARKER:findPersons] - STOP");

        return response;
    }

    @Override
    public Mono<ResponseEntity<PersonReadResponseBody>> updatePerson(
            String personId,
            Mono<PersonRequestBody> personRequestBody,
            ServerWebExchange exchange
    ) {
        log.info("[MARKER:updatePersonById] - START");

        Mono<ResponseEntity<PersonReadResponseBody>> response = personService.update(personId, personRequestBody)
                .doFirst(() -> log.info(">>> updatePersonById started"))
                .doOnTerminate(() -> log.info(">>> updatePersonById finished"))
                .doOnSuccess(result -> log.info(">>> The updatePersonById result is {}", result))
                .doOnError(error -> log.error(">>> The updatePersonById error is {}", String.valueOf(error)))
                .map(body -> ResponseEntity.status(ACCEPTED).body(body));

        log.info("[MARKER:updatePersonById] - STOP");

        return response;
    }

    @Override
    public Mono<ResponseEntity<GenericsResponseBody>> deletePerson(String personId, ServerWebExchange exchange) {
        log.info("[MARKER:deletePersonById] - START");

        Mono<ResponseEntity<GenericsResponseBody>> response = personService.delete(personId)
                .doFirst(() -> log.info(">>> deletePersonById started"))
                .doOnTerminate(() -> log.info(">>> deletePersonById finished"))
                .doOnSuccess(result -> log.info(">>> The deletePersonById result is {}", result))
                .doOnError(error -> log.error(">>> The deletePersonById error is {}", String.valueOf(error)))
                .map(body -> ResponseEntity.status(NO_CONTENT).body(body));

        log.info("[MARKER:deletePersonById] - STOP");

        return response;
    }

    @Override
    public Mono<ResponseEntity<GenericsResponseBody>> patchPerson(
            String personId,
            Mono<PersonPatchRequestBody> patchRequestBodyMono,
            ServerWebExchange exchange
    ) {
        log.info("[MARKER:patchPerson] - START");

        Mono<ResponseEntity<GenericsResponseBody>> response = personService.patch(personId, patchRequestBodyMono)
                .doFirst(() -> log.info(">>> patchPerson started"))
                .doOnTerminate(() -> log.info(">>> patchPerson finished"))
                .doOnSuccess(result -> log.info(">>> The patchPerson result is {}", result))
                .doOnError(error -> log.error(">>> The patchPerson error is {}", String.valueOf(error)))
                .map(body -> ResponseEntity.status(ACCEPTED).body(body));

        log.info("[MARKER:patchPerson] - STOP");

        return response;
    }

    @Override
    public Mono<ResponseEntity<GenericsResponseBody>> patchPersonByPath(
            String personId,
            String fieldName,
            Object fieldValue,
            ServerWebExchange exchange
    ) {
        log.info("[MARKER:patchPersonByPath] - START");

        Mono<ResponseEntity<GenericsResponseBody>> response = personService.patchByPath(personId, fieldName, fieldValue)
                .doFirst(() -> log.info(">>> patchPersonByPath started"))
                .doOnTerminate(() -> log.info(">>> patchPersonByPath finished"))
                .doOnSuccess(result -> log.info(">>> The patchPersonByPath result is {}", result))
                .doOnError(error -> log.error(">>> The patchPersonByPath error is {}", String.valueOf(error)))
                .map(body -> ResponseEntity.status(ACCEPTED).body(body));

        log.info("[MARKER:patchPersonByPath] - STOP");

        return response;
    }

}
