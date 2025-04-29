package com.webflux.sample.controller;

import com.webflux.sample.service.AddressService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@WebFluxTest(AddressController.class)
class AddressControllerTest extends BaseControllerTest {

    @MockBean
    private AddressService addressService;

    @Test
    @DisplayName("should Create One Address POST - /webflux-sample/v1/addresses/{personId}")
    void shouldCreateOneAddressSuccessfully() {
        when(addressService.create(anyString(), any())).thenReturn(Mono.just(null));

        webClient.post()
                .uri("/path")
                .contentType(APPLICATION_JSON)
                .body(null)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.personId")
                .isEqualTo(null);
    }

    @Test
    @DisplayName("should NOT Create One Address POST - /webflux-sample/v1/addresses/{personId}")
    void shouldNotCreateOneAddressSuccessfully() {
        when(addressService.create(anyString(), any())).thenReturn(Mono.just(null));

        webClient.post()
                .uri("/path")
                .contentType(APPLICATION_JSON)
                .body(null)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.personId")
                .isEqualTo(null);
    }

    @Test
    @DisplayName("should Read All Address for One Person GET - /webflux-sample/v1/addresses/{personId}")
    @WithAnonymousUser
    void shouldReadAllAddressForOnePersonSuccessfully() {
        webClient.get()
                .uri("/path")
                .exchange()
                .expectStatus().isUnauthorized()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    @DisplayName("should NOT Read All Address for One Person GET - /webflux-sample/v1/addresses/{personId}")
    @WithAnonymousUser
    void shouldNotReadAllAddressForOnePersonSuccessfully() {
        webClient.get()
                .uri("/path")
                .exchange()
                .expectStatus().isUnauthorized()
                .expectBody()
                .consumeWith(System.out::println);
    }

}
