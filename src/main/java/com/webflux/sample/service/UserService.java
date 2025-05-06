package com.webflux.sample.service;

import com.webflux.sample.dto.UserRequest;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<Void> create(Mono<UserRequest> userRequest);
    Mono<String> search(UserRequest userRequest);
}
