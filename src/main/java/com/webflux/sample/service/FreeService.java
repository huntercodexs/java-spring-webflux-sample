package com.webflux.sample.service;

import com.webflux.sample.user_credentials_integration.api.UserCredentialsApi;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

@Log4j2
@Service
@AllArgsConstructor
public class FreeService {

    private final UserCredentialsApi userCredentialsApi;

    public void tryConnection(ServerWebExchange exchange) {
        log.info("Trying to connect to user-credentials-integration service...");
        var response = userCredentialsApi.getCredential("123", exchange);
        log.info("Response from user-credentials-integration service: {}", response);
    }

}
