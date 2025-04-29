package com.webflux.sample.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;

import static org.mockito.Mockito.mock;
import static org.mockito.MockitoAnnotations.openMocks;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
class ZipcodeIntegrationTest {

    private static WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
    private static WebClient.RequestBodySpec bodySpec = mock(WebClient.RequestBodySpec.class);
    private static WebClient webClient = mock(WebClient.class);
    private static WebClient.RequestHeadersUriSpec headersUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
    private static WebClient.RequestBodyUriSpec bodyUriSpec = mock(WebClient.RequestBodyUriSpec.class);
    private static WebClient.RequestHeadersSpec requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);

    @BeforeEach
    void setup() {
        openMocks(this);
    }

    @AfterEach
    void renew() {
        responseSpec = mock(WebClient.ResponseSpec.class);
        bodySpec = mock(WebClient.RequestBodySpec.class);
        webClient = mock(WebClient.class);
        headersUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        bodyUriSpec = mock(WebClient.RequestBodyUriSpec.class);
        requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
    }

}
