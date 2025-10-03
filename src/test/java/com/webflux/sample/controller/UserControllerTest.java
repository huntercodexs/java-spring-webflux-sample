package com.webflux.sample.controller;

import com.webflux.sample.exception.BadRequestExceptionReactor;
import com.webflux.sample.exception.ConflictExceptionReactor;
import com.webflux.sample.exception.InternalErrorExceptionReactor;
import com.webflux.sample.exception.NotFoundExceptionReactor;
import com.webflux.sample.repository.LoginRepository;
import com.webflux.sample.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

import static com.webflux.sample.DataBuilder.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@WebFluxTest(UserController.class)
class UserControllerTest extends BaseControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private LoginRepository loginRepository;

    @Test
    @DisplayName("POST /user - Should Create One User Successfully")
    @WithMockUser
    void shouldCreateOneUserSuccessfully() {
        when(userService.create(any())).thenReturn(Mono.just(buildUserCreatedResponseBodyForTests()));

        webTestClient.post()
                .uri(BASE_URL+API_PREFIX+"/user")
                .contentType(APPLICATION_JSON)
                .body(fromValue(buildUserRequestBodyForTests()))
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.id").isEqualTo(USER_ID);
    }

    @Test
    @DisplayName("POST /user - Should NOT Create One User")
    void shouldNotCreateOneUser() {
        when(userService.create(any())).thenReturn(Mono.error(new BadRequestExceptionReactor("Some Error")));

        webTestClient.post()
                .uri(BASE_URL+API_PREFIX+"/user")
                .contentType(APPLICATION_JSON)
                .body(null)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    @DisplayName("GET /user/{userId} - Should Read One User")
    @WithMockUser
    void shouldReadOneUserSuccessfully() {
        when(userService.read(anyString())).thenReturn(Mono.just(buildUserReadResponseBodyForTests()));

        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/user/"+ USER_ID)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.id").isEqualTo(USER_ID)
                .jsonPath("$.name").isEqualTo(USER_NAME)
                .jsonPath("$.email").isEqualTo(USER_EMAIL)
                .jsonPath("$.active").isEqualTo(true);
    }

    @Test
    @DisplayName("GET /user/1234567890 - Should NOT Read One User")
    @WithMockUser
    void shouldNotReadOneUserSuccessfully() {
        when(userService.read(anyString())).thenReturn(Mono.error(new NotFoundExceptionReactor("User Not Found")));

        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/user/1234567890")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.message").isEqualTo("User Not Found");
    }

    @Test
    @DisplayName("GET /user - Should Read All User")
    @WithMockUser
    void shouldReadAllUserSuccessfully() {
        when(userService.readAll()).thenReturn(Mono.just(buildUsersReadResponseBodyForTests()));

        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/user")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.users").isEqualTo(new ArrayList<>());
    }

    @Test
    @DisplayName("GET /user - Should NOT Read All User")
    @WithMockUser
    void shouldNotReadAllUserSuccessfully() {
        when(userService.readAll()).thenReturn(Mono.error(new InternalErrorExceptionReactor("Some Error")));

        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/user")
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.message").isEqualTo("Some Error");
    }

    @Test
    @DisplayName("PUT /user/{userId} - Should Update One User Successfully")
    @WithMockUser
    void shouldUpdateOneUserSuccessfully() {
        when(userService.update(anyString(), any())).thenReturn(Mono.just(buildUserReadResponseBodyForTests()));

        webTestClient.put()
                .uri(BASE_URL+API_PREFIX+"/user/"+ USER_ID)
                .contentType(APPLICATION_JSON)
                .body(fromValue(buildUserRequestBodyForTests()))
                .exchange()
                .expectStatus().isAccepted()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.id").isEqualTo(USER_ID)
                .jsonPath("$.name").isEqualTo(USER_NAME)
                .jsonPath("$.email").isEqualTo(USER_EMAIL)
                .jsonPath("$.active").isEqualTo(true);
    }

    @Test
    @DisplayName("PUT /user/{userId} - Should NOT Update One User Successfully")
    @WithMockUser
    void shouldNotUpdateOneUserSuccessfully() {
        when(userService.update(anyString(), any())).thenReturn(Mono.error(new BadRequestExceptionReactor("Some Bad Request")));

        webTestClient.put()
                .uri(BASE_URL+API_PREFIX+"/user/"+ USER_ID)
                .contentType(APPLICATION_JSON)
                .body(fromValue(buildUserRequestBodyForTests()))
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.message").isEqualTo("Some Bad Request");
    }

    @Test
    @DisplayName("DELETE /user/{userId} - Should Delete One User")
    @WithMockUser
    void shouldDeleteOneUserSuccessfully() {
        when(userService.delete(anyString())).thenReturn(Mono.just(buildGenericsResponseBodyForTests("User deleted successfully")));

        webTestClient.delete()
                .uri(BASE_URL+API_PREFIX+"/user/"+ USER_ID)
                .exchange()
                .expectStatus().isNoContent()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.message").isEqualTo("User deleted successfully");
    }

    @Test
    @DisplayName("DELETE /user/1234567890 - Should NOT Delete One User")
    @WithMockUser
    void shouldNotDeleteOneUserSuccessfully() {
        when(userService.delete(anyString())).thenReturn(Mono.error(new NotFoundExceptionReactor("User Not Found to Delete")));

        webTestClient.delete()
                .uri(BASE_URL+API_PREFIX+"/user/1234567890")
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.message").isEqualTo("User Not Found to Delete");
    }

    @Test
    @DisplayName("PATCH /user/{userId} - Should Patch One User Successfully")
    @WithMockUser
    void shouldPatchOneUserSuccessfully() {
        when(userService.patch(anyString(), any())).thenReturn(Mono.just(buildGenericsResponseBodyForTests("User Patched Successfully")));

        webTestClient.patch()
                .uri(BASE_URL+API_PREFIX+"/user/"+ USER_ID)
                .contentType(APPLICATION_JSON)
                .body(fromValue(buildUserRequestBodyForTests()))
                .exchange()
                .expectStatus().isAccepted()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.message").isEqualTo("User Patched Successfully");
    }

    @Test
    @DisplayName("PATCH /user/{userId} - Should NOT Patch One User Successfully")
    @WithMockUser
    void shouldNotPatchOneUserSuccessfully() {
        when(userService.patch(anyString(), any())).thenReturn(Mono.error(new BadRequestExceptionReactor("Some Bad Request for Patch")));

        webTestClient.patch()
                .uri(BASE_URL+API_PREFIX+"/user/"+ USER_ID)
                .contentType(APPLICATION_JSON)
                .body(fromValue(buildUserRequestBodyForTests()))
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.message").isEqualTo("Some Bad Request for Patch");
    }

    @Test
    @DisplayName("PATCH /user/{userId}/{fieldName}/{fieldValue} - Should Patch by Path One User Successfully")
    @WithMockUser
    void shouldPatchByPathOneUserSuccessfully() {
        when(userService.patchByPath(anyString(), anyString(), any())).thenReturn(Mono.just(buildGenericsResponseBodyForTests("User Patched Successfully")));

        webTestClient.patch()
                .uri(BASE_URL+API_PREFIX+"/user/"+ USER_ID +"/"+FIELD_NAME+"/"+FIELD_VALUE)
                .contentType(APPLICATION_JSON)
                .body(null)
                .exchange()
                .expectStatus().isAccepted()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.message").isEqualTo("User Patched Successfully");
    }

    @Test
    @DisplayName("PATCH /user/{userId}/{fieldName}/{fieldValue} - Should NOT Patch by Path One User Successfully")
    @WithMockUser
    void shouldNotPatchByPathOneUserSuccessfully() {
        when(userService.patchByPath(anyString(), anyString(), any())).thenReturn(Mono.error(new ConflictExceptionReactor("Something went wrong")));

        webTestClient.patch()
                .uri(BASE_URL+API_PREFIX+"/user/"+ USER_ID +"/"+FIELD_NAME+"/"+FIELD_VALUE)
                .contentType(APPLICATION_JSON)
                .body(null)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.message").isEqualTo("Something went wrong");
    }

}
