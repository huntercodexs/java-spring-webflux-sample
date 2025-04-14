package com.webflux.sample.service;

import com.webflux.sample.model.PersonRequest;
import reactor.core.publisher.Mono;

public interface SecurityService {
    Mono<PersonRequest> fakeAuthentication();
}
