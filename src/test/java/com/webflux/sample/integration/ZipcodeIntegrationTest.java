package com.webflux.sample.integration;

import com.google.common.base.Predicate;
import com.webflux.sample.model.ZipcodeModel;
import com.webflux.sample.service.WebClientSampleService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
class ZipcodeIntegrationTest {

    private static WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
    private static WebClient.RequestBodySpec bodySpec = mock(WebClient.RequestBodySpec.class);
    private static WebClient webClient = mock(WebClient.class);
    private static WebClient.RequestHeadersUriSpec headersUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
    private static WebClient.RequestBodyUriSpec bodyUriSpec = mock(WebClient.RequestBodyUriSpec.class);
    private static WebClient.RequestHeadersSpec requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);

    @InjectMocks
    private WebClientSampleService webClientSampleService;

    @BeforeEach
    void setUp() {
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

    @Test
    @DisplayName("GET /test/webclient/get - Should Retrieve the Zipcode Model Successfully")
    @WithAnonymousUser
    void shouldRetrieveTheZipcodeModelSuccessfully() {
        when(webClient.get()).thenReturn(headersUriSpec);
        when(headersUriSpec.uri(anyString())).thenReturn(bodySpec);
        when(bodySpec.header(anyString(), any())).thenReturn(bodySpec);
        when(bodySpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(ZipcodeModel.class)).thenReturn(Mono.just(new ZipcodeModel()));
        when(responseSpec.onStatus(any(Predicate.class), any())).thenReturn(responseSpec);

        var response = webClientSampleService.get();

        StepVerifier.create(response)
                .consumeNextWith(Assertions::assertNotNull)
                .verifyComplete();
    }

}
