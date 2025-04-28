package com.webflux.sample.service;

import com.webflux.sample.exception.ConflictExceptionReactor;
import com.webflux.sample.exception.InternalErrorExceptionReactor;
import com.webflux.sample.model.ZipcodeModel;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.util.function.Function;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@Log4j2
@Service
@AllArgsConstructor
public class WebClientSampleService {

    public Mono<ZipcodeModel> get() {
        return WebClient.builder()
                .baseUrl("https://viacep.com.br")
                .build()
                .get()
                .uri("/ws/12090002/json/")
                .header(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(ZipcodeModel.class)
                .doOnSuccess(success -> log.info("GET Success {}", success))
                .onErrorResume(error -> {
                    throw new InternalErrorExceptionReactor("GET Something went wrong: " + error);
                });
    }

    public Mono<Void> post() {
        return WebClient.builder()
                .baseUrl("https://appdemo.test.com")
                .build()
                .post()
                .uri("/api/example/v1")
                .header(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, "Basic 123")
                .body(Mono.just(new Object()), Object.class)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, handlerExceptionFunction("4xx"))
                .onStatus(HttpStatusCode::is5xxServerError, handlerExceptionFunction("5xx"))
                .bodyToMono(String.class)
                .retryWhen(Retry.max(3).filter(throwable -> throwable instanceof RuntimeException))
                .doOnSuccess(success -> log.info("POST Success {}", success))
                .doOnError(error -> log.error("POST Error {}", String.valueOf(error)))
                .then();
    }

    public Mono<Void> put() {
        return WebClient.builder()
                .baseUrl("https://appdemo.test.com")
                .build()
                .put()
                .uri("/api/example/v1/product/123456")
                .header(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, "Basic 123")
                .body(Mono.just(new Object()), Object.class)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, handlerExceptionFunction("4xx"))
                .onStatus(HttpStatusCode::is5xxServerError, handlerExceptionFunction("5xx"))
                .bodyToMono(String.class)
                .retryWhen(Retry.max(3).filter(throwable -> throwable instanceof RuntimeException))
                .doOnSuccess(success -> log.info("PUT Success {}", success))
                .doOnError(error -> log.error("PUT Error {}", String.valueOf(error)))
                .then();
    }

    public Mono<Void> delete() {
        return WebClient.builder()
                .baseUrl("https://appdemo.test.com")
                .build()
                .delete()
                .uri("/api/example/v1/product/123456")
                .header(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(Void.class)
                .doOnSuccess(success -> log.info("DELETE Success {}", success))
                .onErrorResume(error -> {
                    throw new InternalErrorExceptionReactor("DELETE Something went wrong: " + error);
                });
    }

    private Function<ClientResponse, Mono<? extends Throwable>> handlerExceptionFunction(String status) {
        if (status.equals("4xx")) {
            return clientResponse -> clientResponse
                    .bodyToMono(String.class)
                    .flatMap(error -> Mono.error(new ConflictExceptionReactor("Client Error: " + error)));
        } else {
            return clientResponse -> clientResponse
                    .bodyToMono(String.class)
                    .flatMap(error -> Mono.error(new InternalErrorExceptionReactor("Client Error: " + error)));
        }
    }
}
