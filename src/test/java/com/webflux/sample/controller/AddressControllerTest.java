package com.webflux.sample.controller;

import com.webflux.sample.exception.InternalErrorExceptionReactor;
import com.webflux.sample.exception.NotFoundExceptionReactor;
import com.webflux.sample.repository.UserRepository;
import com.webflux.sample.service.AddressService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import reactor.core.publisher.Mono;

import static com.webflux.sample.DataBuilder.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@WebFluxTest(AddressController.class)
class AddressControllerTest extends BaseControllerTest {

    @MockBean
    private AddressService addressService;

    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("POST /addresses/{personId} - Should Create One Address for One Person")
    @WithMockUser
    void shouldCreateOneAddressSuccessfully() {
        when(addressService.create(anyString(), any())).thenReturn(Mono.just(buildAddressCreatedResponseBodyForTests()));

        webTestClient.post()
                .uri(BASE_URL+API_PREFIX+"/addresses/"+PERSON_ID)
                .contentType(APPLICATION_JSON)
                .body(fromValue(buildAddressRequestBodyForTests()))
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.id").isEqualTo(PERSON_ID);
    }

    @Test
    @DisplayName("POST /addresses/{personId} - Should NOT Create One Address")
    void shouldNotCreateOneAddressSuccessfully() {
        when(addressService.create(anyString(), any())).thenReturn(Mono.error(new InternalErrorExceptionReactor("Some Error")));

        webTestClient.post()
                .uri(BASE_URL+API_PREFIX+"/addresses/"+PERSON_ID)
                .contentType(APPLICATION_JSON)
                .body(fromValue(buildAddressRequestBodyForTests()))
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    @DisplayName("GET /addresses/{personId} - Should Read All Address for One Person")
    @WithMockUser
    void shouldReadAllAddressForOnePersonSuccessfully() {
        when(addressService.find(anyString())).thenReturn(Mono.just(buildAddressReadResponseBodyForTests()));

        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/addresses/"+PERSON_ID)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    @DisplayName("GET /addresses/{personId} - Should NOT Read All Address for One Person")
    @WithMockUser
    void shouldNotReadAllAddressForOnePersonSuccessfully() {
        when(addressService.find(anyString())).thenReturn(Mono.error(new NotFoundExceptionReactor("Some Error")));

        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/addresses/"+PERSON_ID)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .consumeWith(System.out::println);
    }

}
