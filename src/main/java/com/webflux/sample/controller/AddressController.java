package com.webflux.sample.controller;

import com.webflux.sample.model.AddressCreatedResponseBody;
import com.webflux.sample.model.AddressReadResponseBody;
import com.webflux.sample.model.AddressRequestBody;
import com.webflux.sample.person_details.api.AddressApi;
import com.webflux.sample.service.AddressService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Log4j2
@RestController
@AllArgsConstructor
public class AddressController implements BaseController, AddressApi {

    AddressService addressService;

    @Override
    public Mono<ResponseEntity<AddressCreatedResponseBody>> createAddress(
            Mono<AddressRequestBody> addressRequestBody,
            ServerWebExchange exchange
    ) {
        log.info("[MARKER:createAddress] - START");

        Mono<ResponseEntity<AddressCreatedResponseBody>> response = addressService
                .create(addressRequestBody)
                .doFirst(() -> log.info(">>> Create address started"))
                .doOnTerminate(() -> log.info(">>> Create address finished"))
                .map(ResponseEntity::ok)
                .doOnSuccess(result -> log.info(">>> The createAddress result is {}", result))
                .doOnError(error -> log.error(">>> The createAddress error is {}", String.valueOf(error)));

        log.info("[MARKER:createAddress] - STOP");

        return response;
    }

    @Override
    public Mono<ResponseEntity<AddressReadResponseBody>> findAddress(
            String street,
            String zipcode,
            ServerWebExchange exchange
    ) {
        return null;
    }

}
