package com.webflux.sample.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.blockhound.BlockHound;

import java.time.Duration;

import static org.mockito.MockitoAnnotations.openMocks;

@TestPropertySource(locations = "classpath:application-default.properties")
@TestPropertySource(properties = "spring.main.lazy-initialization=true")
//@EnableAspectJAutoProxy(proxyTargetClass = true)
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@WithMockUser(roles = {"AUTH-BASIC"})
//@AutoConfigureWebTestClient(timeout = "PT36S")
//@AutoConfigureWebTestClient(timeout = "36000")
public abstract class BaseControllerTest {

    @Autowired
    protected WebTestClient webTestClient;

    @BeforeEach
    public void setUp() {
        BlockHound.install();
        openMocks(this);
        //This setup fix the error: java.lang.IllegalStateException: Timeout on blocking read for 5000000000 NANOSECONDS
        webTestClient = webTestClient.mutate()
                .responseTimeout(Duration.ofMillis(36000))
                .build();
    }

}
