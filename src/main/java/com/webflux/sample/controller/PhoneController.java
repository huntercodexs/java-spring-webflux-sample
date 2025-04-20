package com.webflux.sample.controller;

import com.webflux.sample.model.PhoneCreatedResponseBody;
import com.webflux.sample.model.PhoneReadResponseBody;
import com.webflux.sample.model.PhoneRequestBody;
import com.webflux.sample.person_details.api.PhoneApi;
import com.webflux.sample.service.PhoneService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Log4j2
@RestController
@AllArgsConstructor
public class PhoneController implements BaseController, PhoneApi {

    PhoneService phoneService;

    @Override
    public Mono<ResponseEntity<PhoneCreatedResponseBody>> createPhone(
            Mono<PhoneRequestBody> phoneRequestBody,
            ServerWebExchange exchange
    ) {
        log.info("[MARKER:createPhone] - START");

        Mono<ResponseEntity<PhoneCreatedResponseBody>> response = phoneService
                .create(phoneRequestBody)
                .doFirst(() -> log.info(">>> Create phone started"))
                .doOnTerminate(() -> log.info(">>> Create phone finished"))
                .map(ResponseEntity::ok)
                .doOnSuccess(result -> log.info(">>> The createPhone result is {}", result))
                .doOnError(error -> log.error(">>> The createPhone error is {}", String.valueOf(error)));

        log.info("[MARKER:createPhone] - STOP");

        return response;
    }

    @Override
    public Mono<ResponseEntity<PhoneReadResponseBody>> findPhone(
            String phone,
            ServerWebExchange exchange
    ) {
        return null;
    }
}
