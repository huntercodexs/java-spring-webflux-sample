package com.webflux.sample.service.impl;

import com.webflux.sample.service.SecurityService;
import com.webflux.sample.user.model.UserRequestBody;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SecurityServiceImpl implements SecurityService {

    private SecurityService securityService;

    @Override
    public Mono<UserRequestBody> fakeAuthentication() {
        return Mono.empty();
    }

}
