package com.webflux.sample.controller;

import com.webflux.sample.service.PhoneService;
import com.webflux.sample.user_details.api.PhonesApi;
import com.webflux.sample.user_details.model.PhoneCreatedResponseBody;
import com.webflux.sample.user_details.model.PhoneReadResponseBody;
import com.webflux.sample.user_details.model.PhoneRequestBody;
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
public class PhoneController implements BaseController, PhonesApi {

    PhoneService phoneService;

    @Override
    public Mono<ResponseEntity<PhoneCreatedResponseBody>> createPhone(
            String userId,
            Mono<PhoneRequestBody> phoneRequestBody,
            ServerWebExchange exchange
    ) {
        log.info("[MARKER:createPhone] - START");

        Mono<ResponseEntity<PhoneCreatedResponseBody>> response = phoneService
                .create(userId, phoneRequestBody)
                .doFirst(() -> log.info(">>> Create phone started"))
                .doOnTerminate(() -> log.info(">>> Create phone finished"))
                .doOnSuccess(result -> log.info(">>> The createPhone result is {}", result))
                .doOnError(error -> log.error(">>> The createPhone error is {}", String.valueOf(error)))
                .map(body -> ResponseEntity.status(CREATED).body(body));

        log.info("[MARKER:createPhone] - STOP");

        return response;
    }

    @Override
    public Mono<ResponseEntity<PhoneReadResponseBody>> findPhones(
            String userId,
            ServerWebExchange exchange
    ) {
        log.info("[MARKER:findPhones] - START");

        Mono<ResponseEntity<PhoneReadResponseBody>> response = phoneService.find(userId)
                .doFirst(() -> log.info(">>> findPhones started"))
                .doOnTerminate(() -> log.info(">>> findPhones finished"))
                .doOnSuccess(result -> log.info(">>> The findPhones result is {}", result))
                .doOnError(error -> log.error(">>> The findPhones error is {}", String.valueOf(error)))
                .map(body -> ResponseEntity.status(OK).body(body));

        log.info("[MARKER:findPhones] - STOP");

        return response;
    }
}
