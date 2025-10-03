package com.webflux.sample.service;

import com.webflux.sample.user_details.model.AddressCreatedResponseBody;
import com.webflux.sample.user_details.model.AddressReadResponseBody;
import com.webflux.sample.user_details.model.AddressRequestBody;
import reactor.core.publisher.Mono;

public interface AddressService {
    Mono<AddressCreatedResponseBody> create(String userId, Mono<AddressRequestBody> createAddressRequest);
    Mono<AddressReadResponseBody> find(String userId);
}
