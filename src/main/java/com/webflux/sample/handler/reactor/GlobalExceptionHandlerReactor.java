package com.webflux.sample.handler.reactor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webflux.sample.handler.reactor.exception.HttpExceptionReactor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static java.util.Objects.isNull;

@Log4j2
@Order(-2)
@Component
public class GlobalExceptionHandlerReactor implements ErrorWebExceptionHandler {

    private final HttpStatus internalError = HttpStatus.INTERNAL_SERVER_ERROR;
    private final int internalCode = 0x999;

    public Mono<Void> handle(ServerWebExchange webExchange, Throwable throwable) {
        if (throwable instanceof HttpExceptionReactor ex) {
            return this.web(webExchange, ex);
        }
        return this.internal(webExchange, throwable);
    }

    private Mono<Void> web(ServerWebExchange webExchange, HttpExceptionReactor ex) {
        ServerWebExchange exchange = this.headers(webExchange, ex.getStatus());
        String response = this.response(ex);
        return exchange.getResponse().writeWith(Mono.just(buffer(exchange, response))).then();
    }

    private Mono<Void> internal(ServerWebExchange webExchange, Throwable throwable) {
        String track = UUID.randomUUID().toString().replaceAll("-", "");
        ServerWebExchange exchange = this.headers(webExchange, internalError);
        String response = this.response(new HttpExceptionReactor(throwable.getMessage(), internalCode, track));
        return exchange.getResponse().writeWith(Mono.just(buffer(webExchange, response))).then();
    }

    private ServerWebExchange headers(ServerWebExchange exchange, HttpStatus status) {
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        exchange.getResponse().setStatusCode(status);
        return exchange;
    }

    private String response(HttpExceptionReactor ex) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            if (!isNull(ex.getTrack()) && !ex.getTrack().isEmpty()) {
                this.log(ex.getTrack(), ex.getMessage());
            }
            return mapper.writeValueAsString(new CustomResponseExceptionReactor(ex.getMessage(), ex.getCode(), ex.getTrack()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private DataBuffer buffer(ServerWebExchange exchange, String response) {
        DataBufferFactory bufferFactory = exchange.getResponse().bufferFactory();
        return bufferFactory.wrap(response.getBytes());
    }

    private void log(String track, String message) {
        log.error("Track [{}], ErrorMessage: {}", track, message);
    }
}
