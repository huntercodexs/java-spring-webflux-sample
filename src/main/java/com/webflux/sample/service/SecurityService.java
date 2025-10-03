package com.webflux.sample.service;

import com.webflux.sample.user.model.UserRequestBody;
import reactor.core.publisher.Mono;

public interface SecurityService {
    Mono<UserRequestBody> fakeAuthentication();
}
