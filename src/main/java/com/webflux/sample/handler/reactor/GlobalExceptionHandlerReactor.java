package com.webflux.sample.handler.reactor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webflux.sample.handler.reactor.exception.BadRequestExceptionReactor;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Order(-2)
@Component
public class GlobalExceptionHandlerReactor implements ErrorWebExceptionHandler {

    public Mono<Void> handle(ServerWebExchange webExchange, Throwable throwable) {

        if (throwable instanceof BadRequestExceptionReactor ex) {
            ServerWebExchange exchange = this.exchange(webExchange, ex.getStatus());
            String response = this.response(ex);
            return exchange.getResponse().writeWith(Mono.just(buffer(exchange, response))).then();
        }

        throw new RuntimeException(throwable.getMessage());
    }

    private ServerWebExchange exchange(ServerWebExchange exchange, HttpStatus status) {
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        exchange.getResponse().setStatusCode(status);
        return exchange;
    }

    private String response(BadRequestExceptionReactor ex) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(new CustomResponseExceptionReactor(ex.getMessage(), ex.getCode(), ex.getTrack()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private DataBuffer buffer(ServerWebExchange headers, String response) {
        DataBufferFactory bufferFactory = headers.getResponse().bufferFactory();
        return bufferFactory.wrap(response.getBytes());
    }
}
