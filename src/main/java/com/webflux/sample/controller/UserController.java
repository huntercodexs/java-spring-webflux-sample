package com.webflux.sample.controller;

import com.webflux.sample.dto.UserRequest;
import com.webflux.sample.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Log4j2
@RestController
@AllArgsConstructor
public class UserController implements BaseController {

    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = RequestMethod.POST, value = "/user/create")
    public Mono<ResponseEntity<Void>> userCreate(@RequestBody Mono<UserRequest> userRequest, ServerWebExchange exchange) {
        return userService.create(userRequest)
                .doFirst(() -> log.info("User Create is starting {}", userRequest))
                .map(body -> ResponseEntity.status(CREATED).build());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/user/login")
    public Mono<ResponseEntity<String>> userLogin(@RequestBody UserRequest userRequest, ServerWebExchange exchange) {
        return userService.search(userRequest)
                .doFirst(() -> log.info("User Login is starting {}", userRequest))
                .map(response -> ResponseEntity.status(OK).body(response));
    }

}
