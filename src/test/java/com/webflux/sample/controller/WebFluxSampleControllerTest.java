package com.webflux.sample.controller;

import com.webflux.sample.repository.UserRepository;
import com.webflux.sample.service.WebFluxSampleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import static com.webflux.sample.DataBuilder.API_PREFIX;
import static com.webflux.sample.DataBuilder.BASE_URL;

@WebFluxTest(WebFluxSampleController.class)
class WebFluxSampleControllerTest extends BaseControllerTest {

    @Mock
    private WebFluxSampleService webFluxSampleService;

    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("GET /test/mono - Should Execute WebFlux Sample Mono Successfully")
    @WithMockUser
    void shouldExecuteWebFluxSampleMonoSuccessfully() {
        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/test/mono")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    @DisplayName("GET /test/flux - Should Execute WebFlux Sample Flux Successfully")
    @WithMockUser
    void shouldExecuteWebSampleFluxFluxSuccessfully() {
        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/test/flux")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    @DisplayName("GET /test/map - Should Execute WebFlux Sample Map Successfully")
    @WithMockUser
    void shouldExecuteWebSampleFluxMapSuccessfully() {
        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/test/map")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    @DisplayName("GET /test/flatMap - Should Execute WebFlux Sample FlatMap Successfully")
    @WithMockUser
    void shouldExecuteWebSampleFluxFlatMapSuccessfully() {
        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/test/flatMap")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    @DisplayName("GET /test/flatMapAndZip - Should Execute WebFlux Sample FlatMapAndZip Successfully")
    @WithMockUser
    void shouldExecuteWebSampleFluxFlatMapAndZipSuccessfully() {
        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/test/flatMapAndZip")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    @DisplayName("GET /test/then - Should Execute WebFlux Sample Then Successfully")
    @WithMockUser
    void shouldExecuteWebSampleFluxThenSuccessfully() {
        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/test/then")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    @DisplayName("GET /test/thenReturn - Should Execute WebFlux Sample ThenReturn Successfully")
    @WithMockUser
    void shouldExecuteWebSampleFluxThenReturnSuccessfully() {
        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/test/thenReturn")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    @DisplayName("GET /test/switchIfEmpty - Should Execute WebFlux Sample SwitchIfEmpty Successfully")
    @WithMockUser
    void shouldExecuteWebSampleFluxSwitchIfEmptySuccessfully() {
        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/test/switchIfEmpty")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    @DisplayName("GET /test/zip - Should Execute WebFlux Sample Zip Successfully")
    @WithMockUser
    void shouldExecuteWebSampleFluxZipSuccessfully() {
        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/test/zip")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    @DisplayName("GET /test/zipWith - Should Execute WebFlux Sample ZipWith Successfully")
    @WithMockUser
    void shouldExecuteWebSampleFluxZipWithSuccessfully() {
        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/test/zipWith")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    @DisplayName("GET /test/zipWhen - Should Execute WebFlux Sample ZipWhen Successfully")
    @WithMockUser
    void shouldExecuteWebSampleFluxZipWhenSuccessfully() {
        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/test/zipWhen")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    @DisplayName("GET /test/subscribe - Should Execute WebFlux Sample Subscribe Successfully")
    @WithMockUser
    void shouldExecuteWebSampleFluxSubscribeSuccessfully() {
        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/test/subscribe")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println);
    }

}
