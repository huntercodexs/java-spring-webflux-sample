package com.webflux.sample.controller;

import com.webflux.sample.exception.BadRequestExceptionReactor;
import com.webflux.sample.exception.ConflictExceptionReactor;
import com.webflux.sample.exception.InternalErrorExceptionReactor;
import com.webflux.sample.exception.NotFoundExceptionReactor;
import com.webflux.sample.service.PersonService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

import static com.webflux.sample.DataBuilder.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@WebFluxTest(PersonController.class)
class PersonControllerTest extends BaseControllerTest {

    @MockBean
    private PersonService personService;

    @Test
    @DisplayName("POST /person - Should Create One Person Successfully")
    @WithAnonymousUser
    void shouldCreateOnePersonSuccessfully() {
        when(personService.create(any())).thenReturn(Mono.just(buildPersonCreatedResponseBodyForTests()));

        webTestClient.post()
                .uri(BASE_URL+API_PREFIX+"/person")
                .contentType(APPLICATION_JSON)
                .body(fromValue(buildPersonRequestBodyForTests()))
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.id").isEqualTo(PERSON_ID);
    }

    @Test
    @DisplayName("POST /person - Should NOT Create One Person")
    void shouldNotCreateOnePerson() {
        when(personService.create(any())).thenReturn(Mono.error(new BadRequestExceptionReactor("Some Error")));

        webTestClient.post()
                .uri(BASE_URL+API_PREFIX+"/person")
                .contentType(APPLICATION_JSON)
                .body(null)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    @DisplayName("GET /person/{personId} - Should Read One Person")
    @WithAnonymousUser
    void shouldReadOnePersonSuccessfully() {
        when(personService.read(anyString())).thenReturn(Mono.just(buildPersonReadResponseBodyForTests()));

        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/person/"+PERSON_ID)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.id").isEqualTo(PERSON_ID)
                .jsonPath("$.name").isEqualTo(PERSON_NAME)
                .jsonPath("$.email").isEqualTo(PERSON_EMAIL)
                .jsonPath("$.active").isEqualTo(true);
    }

    @Test
    @DisplayName("GET /person/1234567890 - Should NOT Read One Person")
    @WithAnonymousUser
    void shouldNotReadOnePersonSuccessfully() {
        when(personService.read(anyString())).thenReturn(Mono.error(new NotFoundExceptionReactor("Person Not Found")));

        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/person/1234567890")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.message").isEqualTo("Person Not Found");
    }

    @Test
    @DisplayName("GET /person - Should Read All Person")
    @WithAnonymousUser
    void shouldReadAllPersonSuccessfully() {
        when(personService.readAll()).thenReturn(Mono.just(buildPersonsReadResponseBodyForTests()));

        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/person")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.persons").isEqualTo(new ArrayList<>());
    }

    @Test
    @DisplayName("GET /person - Should NOT Read All Person")
    @WithAnonymousUser
    void shouldNotReadAllPersonSuccessfully() {
        when(personService.readAll()).thenReturn(Mono.error(new InternalErrorExceptionReactor("Some Error")));

        webTestClient.get()
                .uri(BASE_URL+API_PREFIX+"/person")
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.message").isEqualTo("Some Error");
    }

    @Test
    @DisplayName("PUT /person/{personId} - Should Update One Person Successfully")
    @WithAnonymousUser
    void shouldUpdateOnePersonSuccessfully() {
        when(personService.update(anyString(), any())).thenReturn(Mono.just(buildPersonReadResponseBodyForTests()));

        webTestClient.put()
                .uri(BASE_URL+API_PREFIX+"/person/"+PERSON_ID)
                .contentType(APPLICATION_JSON)
                .body(fromValue(buildPersonRequestBodyForTests()))
                .exchange()
                .expectStatus().isAccepted()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.id").isEqualTo(PERSON_ID)
                .jsonPath("$.name").isEqualTo(PERSON_NAME)
                .jsonPath("$.email").isEqualTo(PERSON_EMAIL)
                .jsonPath("$.active").isEqualTo(true);
    }

    @Test
    @DisplayName("PUT /person/{personId} - Should NOT Update One Person Successfully")
    @WithAnonymousUser
    void shouldNotUpdateOnePersonSuccessfully() {
        when(personService.update(anyString(), any())).thenReturn(Mono.error(new BadRequestExceptionReactor("Some Bad Request")));

        webTestClient.put()
                .uri(BASE_URL+API_PREFIX+"/person/"+PERSON_ID)
                .contentType(APPLICATION_JSON)
                .body(fromValue(buildPersonRequestBodyForTests()))
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.message").isEqualTo("Some Bad Request");
    }

    @Test
    @DisplayName("DELETE /person/{personId} - Should Delete One Person")
    @WithAnonymousUser
    void shouldDeleteOnePersonSuccessfully() {
        when(personService.delete(anyString())).thenReturn(Mono.just(buildGenericsResponseBodyForTests("User deleted successfully")));

        webTestClient.delete()
                .uri(BASE_URL+API_PREFIX+"/person/"+PERSON_ID)
                .exchange()
                .expectStatus().isNoContent()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.message").isEqualTo("User deleted successfully");
    }

    @Test
    @DisplayName("DELETE /person/1234567890 - Should NOT Delete One Person")
    @WithAnonymousUser
    void shouldNotDeleteOnePersonSuccessfully() {
        when(personService.delete(anyString())).thenReturn(Mono.error(new NotFoundExceptionReactor("Person Not Found to Delete")));

        webTestClient.delete()
                .uri(BASE_URL+API_PREFIX+"/person/1234567890")
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.message").isEqualTo("Person Not Found to Delete");
    }

    @Test
    @DisplayName("PATCH /person/{personId} - Should Patch One Person Successfully")
    @WithAnonymousUser
    void shouldPatchOnePersonSuccessfully() {
        when(personService.patch(anyString(), any())).thenReturn(Mono.just(buildGenericsResponseBodyForTests("User Patched Successfully")));

        webTestClient.patch()
                .uri(BASE_URL+API_PREFIX+"/person/"+PERSON_ID)
                .contentType(APPLICATION_JSON)
                .body(fromValue(buildPersonRequestBodyForTests()))
                .exchange()
                .expectStatus().isAccepted()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.message").isEqualTo("User Patched Successfully");
    }

    @Test
    @DisplayName("PATCH /person/{personId} - Should NOT Patch One Person Successfully")
    @WithAnonymousUser
    void shouldNotPatchOnePersonSuccessfully() {
        when(personService.patch(anyString(), any())).thenReturn(Mono.error(new BadRequestExceptionReactor("Some Bad Request for Patch")));

        webTestClient.patch()
                .uri(BASE_URL+API_PREFIX+"/person/"+PERSON_ID)
                .contentType(APPLICATION_JSON)
                .body(fromValue(buildPersonRequestBodyForTests()))
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.message").isEqualTo("Some Bad Request for Patch");
    }

    @Test
    @DisplayName("PATCH /person/{personId}/{fieldName}/{fieldValue} - Should Patch by Path One Person Successfully")
    @WithAnonymousUser
    void shouldPatchByPathOnePersonSuccessfully() {
        when(personService.patchByPath(anyString(), anyString(), any())).thenReturn(Mono.just(buildGenericsResponseBodyForTests("User Patched Successfully")));

        webTestClient.patch()
                .uri(BASE_URL+API_PREFIX+"/person/"+PERSON_ID+"/"+FIELD_NAME+"/"+FIELD_VALUE)
                .contentType(APPLICATION_JSON)
                .body(null)
                .exchange()
                .expectStatus().isAccepted()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.message").isEqualTo("User Patched Successfully");
    }

    @Test
    @DisplayName("PATCH /person/{personId}/{fieldName}/{fieldValue} - Should NOT Patch by Path One Person Successfully")
    @WithAnonymousUser
    void shouldNotPatchByPathOnePersonSuccessfully() {
        when(personService.patchByPath(anyString(), anyString(), any())).thenReturn(Mono.error(new ConflictExceptionReactor("Something went wrong")));

        webTestClient.patch()
                .uri(BASE_URL+API_PREFIX+"/person/"+PERSON_ID+"/"+FIELD_NAME+"/"+FIELD_VALUE)
                .contentType(APPLICATION_JSON)
                .body(null)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.message").isEqualTo("Something went wrong");
    }

}
