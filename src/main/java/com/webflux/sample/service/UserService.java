package com.webflux.sample.service;

import com.webflux.sample.user.model.*;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<UserCreatedResponseBody> create(Mono<UserRequestBody> createUserRequest);
    Mono<UserReadResponseBody> read(String userId);
    Mono<UsersReadResponseBody> readAll();
    Mono<UserReadResponseBody> update(String userId, Mono<UserRequestBody> updateUserRequest);
    Mono<GenericsResponseBody> delete(String userId);
    Mono<GenericsResponseBody> patch(String userId, Mono<UserPatchRequestBody> patchRequestBodyMono);
    Mono<GenericsResponseBody> patchByPath(String userId, String fieldName, Object fieldValue);
}
