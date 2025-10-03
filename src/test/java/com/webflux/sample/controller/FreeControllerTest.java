package com.webflux.sample.controller;

import com.webflux.sample.repository.LoginRepository;
import com.webflux.sample.service.FreeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;

import static com.webflux.sample.DataBuilder.API_PREFIX;
import static com.webflux.sample.DataBuilder.BASE_URL;

@WebFluxTest(FreeController.class)
class FreeControllerTest extends BaseControllerTest {

    @MockBean
    private FreeService freeService;

    @MockBean
    private LoginRepository loginRepository;

    @Test
    @DisplayName("GET /test-value-path/{testValue} - Should Return Value Path")
    @WithAnonymousUser
    void shouldReturnValuePathTest() {
        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/test-value-path/Some value for test path")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .consumeWith(System.out::println)
                .isEqualTo("The value for tests is: Some value for test path");
    }

}
