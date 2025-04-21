package com.webflux.sample.service;

import com.webflux.sample.model.AddressCreatedResponseBody;
import com.webflux.sample.model.AddressReadResponseBody;
import com.webflux.sample.model.AddressRequestBody;
import reactor.core.publisher.Mono;

public interface AddressService {
    Mono<AddressCreatedResponseBody> create(String personId, Mono<AddressRequestBody> createAddressRequest);
    Mono<AddressReadResponseBody> find(String personId);
}
