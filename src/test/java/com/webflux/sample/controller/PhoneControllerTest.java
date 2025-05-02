package com.webflux.sample.controller;

import com.webflux.sample.exception.InternalErrorExceptionReactor;
import com.webflux.sample.exception.NotFoundExceptionReactor;
import com.webflux.sample.service.PhoneService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import reactor.core.publisher.Mono;

import static com.webflux.sample.DataBuilder.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@WebFluxTest(PhoneController.class)
class PhoneControllerTest extends BaseControllerTest {

    @MockBean
    private PhoneService phoneService;

    @Test
    @DisplayName("POST /phones/{personId} - Should Create One Phone for One Person")
    @WithAnonymousUser
    void shouldCreateOnePhoneSuccessfully() {
        when(phoneService.create(anyString(), any())).thenReturn(Mono.just(buildPhoneCreatedResponseBodyForTests()));

        webTestClient.post()
                .uri(BASE_URL+API_PREFIX+"/phones/"+PERSON_ID)
                .contentType(APPLICATION_JSON)
                .body(fromValue(buildPhoneRequestBodyForTests()))
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.id").isEqualTo(PERSON_ID);
    }

    @Test
    @DisplayName("POST /phones/{personId} - Should NOT Create One Phone")
    void shouldNotCreateOnePhoneSuccessfully() {
        when(phoneService.create(anyString(), any())).thenReturn(Mono.error(new InternalErrorExceptionReactor("Some Error")));

        webTestClient.post()
                .uri(BASE_URL+API_PREFIX+"/phones/"+PERSON_ID)
                .contentType(APPLICATION_JSON)
                .body(fromValue(buildPhoneRequestBodyForTests()))
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    @DisplayName("GET /phones/{personId} - Should Read All Phone for One Person")
    @WithAnonymousUser
    void shouldReadAllPhoneForOnePersonSuccessfully() {
        when(phoneService.find(anyString())).thenReturn(Mono.just(buildPhoneReadResponseBodyForTests()));

        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/phones/"+PERSON_ID)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    @DisplayName("GET /phones/{personId} - Should NOT Read All Phone for One Person")
    @WithAnonymousUser
    void shouldNotReadAllPhoneForOnePersonSuccessfully() {
        when(phoneService.find(anyString())).thenReturn(Mono.error(new NotFoundExceptionReactor("Some Error")));

        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/phones/"+PERSON_ID)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .consumeWith(System.out::println);
    }



}
