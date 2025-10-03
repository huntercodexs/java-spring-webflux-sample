package com.webflux.sample.service;

import com.webflux.sample.dto.LoginRequest;
import reactor.core.publisher.Mono;

public interface LoginService {
    Mono<Void> create(Mono<LoginRequest> userRequest);
    Mono<String> search(LoginRequest loginRequest);
}
