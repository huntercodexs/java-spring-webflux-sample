package com.webflux.sample.controller;

import com.webflux.sample.model.AddressCreatedResponse;
import com.webflux.sample.model.AddressListResponse;
import com.webflux.sample.model.AddressRequest;
import com.webflux.sample.person_details.api.AddressApi;
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

    @Override
    public Mono<ResponseEntity<AddressCreatedResponse>> createAddress(
            Mono<AddressRequest> addressRequest,
            ServerWebExchange exchange
    ) {
        return null;
    }

    @Override
    public Mono<ResponseEntity<AddressListResponse>> findAddress(
            String street,
            String postalcode,
            ServerWebExchange exchange
    ) {
        return null;
    }
}
