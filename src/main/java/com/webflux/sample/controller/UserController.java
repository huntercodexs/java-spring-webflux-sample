package com.webflux.sample.controller;

import com.webflux.sample.model.*;
import com.webflux.sample.service.UserService;
import com.webflux.sample.user.api.UserApi;
import com.webflux.sample.user.model.*;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.*;

@Log4j2
@RestController
@AllArgsConstructor
public class UserController implements BaseController, UserApi {

    private UserService userService;

    @Override
    public Mono<ResponseEntity<UserCreatedResponseBody>> createUser(
            Mono<UserRequestBody> createUserRequestBody,
            ServerWebExchange exchange
    ) {
        log.info("[MARKER:createUser] - START");

        Mono<ResponseEntity<UserCreatedResponseBody>> response = userService.create(createUserRequestBody)
                .doFirst(() -> log.info(">>> createUser started"))
                .doOnTerminate(() -> log.info(">>> createUser finished"))
                .doOnSuccess(success -> log.info(">>> The createUser result is {}", success))
                .doOnError(error -> log.error(">>> The createUser error is {}", String.valueOf(error)))
                .map(body -> ResponseEntity.status(CREATED).body(body));

        log.info("[MARKER:createUser] - STOP");

        return response;
    }

    @Override
    public Mono<ResponseEntity<UserReadResponseBody>> readOneUser(
            String userId,
            ServerWebExchange exchange
    ) {
        log.info("[MARKER:findUserById] - START");

        Mono<ResponseEntity<UserReadResponseBody>> response = userService.read(userId)
                .doFirst(() -> log.info(">>> findUserById started"))
                .doOnTerminate(() -> log.info(">>> findUserById finished"))
                .doOnSuccess(result -> log.info(">>> The findUserById result is {}", result))
                .doOnError(error -> log.error(">>> The findUserById error is {}", String.valueOf(error)))
                .map(body -> ResponseEntity.status(OK).body(body));

        log.info("[MARKER:findUserById] - STOP");

        return response;
    }

    @Override
    public Mono<ResponseEntity<UserReadResponseBody>> readOneUserMe(String userId, ServerWebExchange exchange) {
        return null;
    }

    @Override
    public Mono<ResponseEntity<UsersReadResponseBody>> readAllUsers(ServerWebExchange exchange) {
        log.info("[MARKER:findUsers] - START");

        Mono<ResponseEntity<UsersReadResponseBody>> response = userService.readAll()
                .doFirst(() -> log.info(">>> findUsers users started"))
                .doOnTerminate(() -> log.info(">>> findUsers users finished"))
                .doOnSuccess(result -> log.info(">>> The findUsers result is {}", result))
                .doOnError(error -> log.error(">>> The findUsers error is {}", String.valueOf(error)))
                .map(body -> ResponseEntity.status(OK).body(body));

        log.info("[MARKER:findUsers] - STOP");

        return response;
    }

    @Override
    public Mono<ResponseEntity<UserReadResponseBody>> updateUser(
            String userId,
            Mono<UserRequestBody> userRequestBody,
            ServerWebExchange exchange
    ) {
        log.info("[MARKER:updateUserById] - START");

        Mono<ResponseEntity<UserReadResponseBody>> response = userService.update(userId, userRequestBody)
                .doFirst(() -> log.info(">>> updateUserById started"))
                .doOnTerminate(() -> log.info(">>> updateUserById finished"))
                .doOnSuccess(result -> log.info(">>> The updateUserById result is {}", result))
                .doOnError(error -> log.error(">>> The updateUserById error is {}", String.valueOf(error)))
                .map(body -> ResponseEntity.status(ACCEPTED).body(body));

        log.info("[MARKER:updateUserById] - STOP");

        return response;
    }

    @Override
    public Mono<ResponseEntity<GenericsResponseBody>> deleteUser(String userId, ServerWebExchange exchange) {
        log.info("[MARKER:deleteUserById] - START");

        Mono<ResponseEntity<GenericsResponseBody>> response = userService.delete(userId)
                .doFirst(() -> log.info(">>> deleteUserById started"))
                .doOnTerminate(() -> log.info(">>> deleteUserById finished"))
                .doOnSuccess(result -> log.info(">>> The deleteUserById result is {}", result))
                .doOnError(error -> log.error(">>> The deleteUserById error is {}", String.valueOf(error)))
                .map(body -> ResponseEntity.status(NO_CONTENT).body(body));

        log.info("[MARKER:deleteUserById] - STOP");

        return response;
    }

    @Override
    public Mono<ResponseEntity<GenericsResponseBody>> patchUser(
            String userId,
            Mono<UserPatchRequestBody> patchRequestBodyMono,
            ServerWebExchange exchange
    ) {
        log.info("[MARKER:patchUser] - START");

        Mono<ResponseEntity<GenericsResponseBody>> response = userService.patch(userId, patchRequestBodyMono)
                .doFirst(() -> log.info(">>> patchUser started"))
                .doOnTerminate(() -> log.info(">>> patchUser finished"))
                .doOnSuccess(result -> log.info(">>> The patchUser result is {}", result))
                .doOnError(error -> log.error(">>> The patchUser error is {}", String.valueOf(error)))
                .map(body -> ResponseEntity.status(ACCEPTED).body(body));

        log.info("[MARKER:patchUser] - STOP");

        return response;
    }

    @Override
    public Mono<ResponseEntity<GenericsResponseBody>> patchUserByPath(
            String userId,
            String fieldName,
            Object fieldValue,
            ServerWebExchange exchange
    ) {
        log.info("[MARKER:patchUserByPath] - START");

        Mono<ResponseEntity<GenericsResponseBody>> response = userService.patchByPath(userId, fieldName, fieldValue)
                .doFirst(() -> log.info(">>> patchUserByPath started"))
                .doOnTerminate(() -> log.info(">>> patchUserByPath finished"))
                .doOnSuccess(result -> log.info(">>> The patchUserByPath result is {}", result))
                .doOnError(error -> log.error(">>> The patchUserByPath error is {}", String.valueOf(error)))
                .map(body -> ResponseEntity.status(ACCEPTED).body(body));

        log.info("[MARKER:patchUserByPath] - STOP");

        return response;
    }

}
