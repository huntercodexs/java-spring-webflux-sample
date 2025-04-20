package com.webflux.sample.service;

import com.webflux.sample.model.PersonRequestBody;
import reactor.core.publisher.Mono;

public interface SecurityService {
    Mono<PersonRequestBody> fakeAuthentication();
}
