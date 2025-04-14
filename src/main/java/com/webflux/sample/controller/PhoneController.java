package com.webflux.sample.controller;

import com.webflux.sample.model.PhoneCreatedResponse;
import com.webflux.sample.model.PhoneListResponse;
import com.webflux.sample.person_details.api.PhoneApi;
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

    @Override
    public Mono<ResponseEntity<PhoneCreatedResponse>> createPhone(
            ServerWebExchange exchange
    ) {
        return null;
    }

    @Override
    public Mono<ResponseEntity<PhoneListResponse>> findPhone(
            String phone,
            ServerWebExchange exchange
    ) {
        return null;
    }
}
