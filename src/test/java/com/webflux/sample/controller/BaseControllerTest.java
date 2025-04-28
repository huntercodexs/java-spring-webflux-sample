package com.webflux.sample.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockito.MockitoAnnotations.openMocks;

@TestPropertySource(locations = "classpath:application-default.properties")
@TestPropertySource(properties = "spring.main.lazy-initialization=true")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@WithMockUser(roles = {"AUTH-BASIC"})
public abstract class BaseControllerTest {

    @Autowired
    protected WebTestClient webClient;

    @BeforeEach
    public void init() {
        openMocks(this);
    }

}
