package com.webflux.sample.controller;

import com.webflux.sample.service.AddressService;
import com.webflux.sample.user_details.api.AddressesApi;
import com.webflux.sample.user_details.model.AddressCreatedResponseBody;
import com.webflux.sample.user_details.model.AddressReadResponseBody;
import com.webflux.sample.user_details.model.AddressRequestBody;
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
public class AddressController implements BaseController, AddressesApi {

    AddressService addressService;

    @Override
    public Mono<ResponseEntity<AddressCreatedResponseBody>> createAddress(
            String userId,
            Mono<AddressRequestBody> addressRequestBody,
            ServerWebExchange exchange
    ) {
        log.info("[MARKER:createAddress] - START");

        Mono<ResponseEntity<AddressCreatedResponseBody>> response = addressService
                .create(userId, addressRequestBody)
                .doFirst(() -> log.info(">>> Create address started"))
                .doOnTerminate(() -> log.info(">>> Create address finished"))
                .doOnSuccess(result -> log.info(">>> The createAddress result is {}", result))
                .doOnError(error -> log.error(">>> The createAddress error is {}", String.valueOf(error)))
                .map(body -> ResponseEntity.status(CREATED).body(body));

        log.info("[MARKER:createAddress] - STOP");

        return response;
    }

    @Override
    public Mono<ResponseEntity<AddressReadResponseBody>> findAddresses(
            String userId,
            ServerWebExchange exchange
    ) {
        log.info("[MARKER:findAddresses] - START");

        Mono<ResponseEntity<AddressReadResponseBody>> response = addressService.find(userId)
                .doFirst(() -> log.info(">>> findAddresses started"))
                .doOnTerminate(() -> log.info(">>> findAddresses finished"))
                .doOnSuccess(result -> log.info(">>> The findAddresses result is {}", result))
                .doOnError(error -> log.error(">>> The findAddresses error is {}", String.valueOf(error)))
                .map(body -> ResponseEntity.status(OK).body(body));

        log.info("[MARKER:findAddresses] - STOP");

        return response;
    }

}
