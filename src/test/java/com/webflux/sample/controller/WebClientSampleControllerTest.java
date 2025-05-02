package com.webflux.sample.controller;

import com.webflux.sample.exception.BadRequestExceptionReactor;
import com.webflux.sample.service.WebClientSampleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import reactor.core.publisher.Mono;

import static com.webflux.sample.DataBuilder.*;
import static org.mockito.Mockito.when;

@WebFluxTest(WebClientSampleController.class)
class WebClientSampleControllerTest extends BaseControllerTest {

    @MockBean
    private WebClientSampleService webClientSampleService;

    @Test
    @DisplayName("GET /test/webclient/get - Should Execute One HTTP GET Request Successfully")
    @WithAnonymousUser
    void shouldExecuteOneHttpGetRequestSuccessfully() {
        when(webClientSampleService.get()).thenReturn(Mono.just(buildZipcodeModelForTests()));

        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/test/webclient/get")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .json(buildZipcodeModelJsonForTests());
    }

    @Test
    @DisplayName("GET /test/webclient/get - Should NOT Execute One HTTP GET Request Successfully")
    @WithAnonymousUser
    void shouldNotExecuteOneHttpGetRequestSuccessfully() {
        when(webClientSampleService.get()).thenReturn(Mono.error(new BadRequestExceptionReactor("Some Bad Request")));

        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/test/webclient/get")
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.message").isEqualTo("Some Bad Request");
    }

    @Test
    @DisplayName("GET /test/webclient/post - Should Execute One HTTP POST Request Successfully")
    @WithAnonymousUser
    void shouldExecuteOneHttpPostRequestSuccessfully() {
        when(webClientSampleService.post()).thenReturn(Mono.empty());

        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/test/webclient/post")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    @DisplayName("GET /test/webclient/put - Should Execute One HTTP PUT Request Successfully")
    @WithAnonymousUser
    void shouldExecuteOneHttpPutRequestSuccessfully() {
        when(webClientSampleService.put()).thenReturn(Mono.empty());

        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/test/webclient/put")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    @DisplayName("GET /test/webclient/delete - Should Execute One HTTP DELETE Request Successfully")
    @WithAnonymousUser
    void shouldExecuteOneHttpDeleteRequestSuccessfully() {
        when(webClientSampleService.delete()).thenReturn(Mono.empty());

        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/test/webclient/delete")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println);
    }

}
