package com.webflux.sample.service.impl;

import com.webflux.sample.model.PersonRequestBody;
import com.webflux.sample.service.SecurityService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SecurityServiceImpl implements SecurityService {

    private SecurityService securityService;

    @Override
    public Mono<PersonRequestBody> fakeAuthentication() {
        return Mono.empty();
    }

}
