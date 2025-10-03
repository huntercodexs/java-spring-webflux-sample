package com.webflux.sample.controller;

import com.webflux.sample.repository.LoginRepository;
import com.webflux.sample.service.ExceptionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import static com.webflux.sample.DataBuilder.API_PREFIX;
import static com.webflux.sample.DataBuilder.BASE_URL;

@WebFluxTest(ExceptionController.class)
class ExceptionControllerTest extends BaseControllerTest {

    @MockBean
    private ExceptionService exceptionService;

    @MockBean
    private LoginRepository loginRepository;

    @Test
    @DisplayName("GET /test/exception/custom - Should Test Exception Custom")
    @WithMockUser
    void shouldTestExceptionCustom() {
        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/test/exception/custom")
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    @DisplayName("GET /test/exception/bad-request - Should Test Exception Bad Request")
    @WithMockUser
    void shouldTestExceptionBadRequest() {
        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/test/exception/bad-request")
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    @DisplayName("GET /test/exception/bad-request-reactor - Should Test Exception Bad Request Reactor")
    @WithMockUser
    void shouldTestExceptionBadRequestReactor() {
        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/test/exception/bad-request-reactor")
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    @DisplayName("GET /test/exception/not-found-reactor1 - Should Test Exception Not Found Reactor1")
    @WithMockUser
    void shouldTestExceptionNotFoundReactor() {
        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/test/exception/not-found-reactor1")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    @DisplayName("GET /test/exception/internal-exception-reactor - Should Test Exception Internal Error Reactor")
    @WithMockUser
    void shouldTestExceptionInternalErrorReactor() {
        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/test/exception/internal-exception-reactor")
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    @DisplayName("GET /test/exception/runtime-exception-reactor - Should Test Exception Runtime Exception")
    @WithMockUser
    void shouldTestExceptionRuntimeReactor() {
        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/test/exception/runtime-exception-reactor")
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    @DisplayName("GET /test/exception/illegal-argument - Should Test IllegalArgumentException")
    @WithMockUser
    void shouldTestIllegalArgumentException() {
        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/test/exception/illegal-argument")
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    @DisplayName("GET /test/exception/null-pointer - Should Test NullPointerException")
    @WithMockUser
    void shouldTestNullPointerException() {
        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/test/exception/null-pointer")
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    @DisplayName("GET /test/exception/illegal-access - Should Test IllegalAccessException")
    @WithMockUser
    void shouldTestIllegalAccessException() {
        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/test/exception/illegal-access")
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    @DisplayName("GET /test/exception/http-exception - Should Test HttpException")
    @WithMockUser
    void shouldTestHttpException() {
        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/test/exception/http-exception")
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    @DisplayName("GET /test/exception/index-out-of-bounds - Should Test IndexOutOfBoundsException")
    @WithMockUser
    void shouldTestIndexOutOfBoundsException() {
        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/test/exception/index-out-of-bounds")
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    @DisplayName("GET /test/exception/not-found-reactor - Should Test NotFoundExceptionReactor")
    @WithMockUser
    void shouldTestNotFoundExceptionReactor() {
        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/test/exception/not-found-reactor")
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    @DisplayName("GET /test/exception/not-found-swagger - Should Test NotFoundException")
    @WithMockUser
    void shouldTestNotFoundException() {
        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/test/exception/not-found-swagger")
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    @DisplayName("GET /test/exception/error - Should Test Error")
    @WithMockUser
    void shouldTestError() {
        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/test/exception/error")
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    @DisplayName("GET /test/exception/internal-error - Should Test InternalError")
    @WithMockUser
    void shouldTestInternalError() {
        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/test/exception/internal-error")
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    @DisplayName("GET /test/exception/internal-error-exception - Should Test InternalErrorException")
    @WithMockUser
    void shouldTestInternalErrorException() {
        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/test/exception/internal-error-exception")
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .consumeWith(System.out::println);
    }

}
