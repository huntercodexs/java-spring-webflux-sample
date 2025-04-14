package com.webflux.sample.service.impl;

import com.webflux.sample.model.PersonRequest;
import com.webflux.sample.service.SecurityService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SecurityServiceImpl implements SecurityService {

    private SecurityService securityService;

    @Override
    public Mono<PersonRequest> fakeAuthentication() {
        return Mono.empty();
    }

}
