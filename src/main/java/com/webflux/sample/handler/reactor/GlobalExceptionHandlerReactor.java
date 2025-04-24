package com.webflux.sample.handler.reactor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webflux.sample.handler.reactor.exception.BadRequestExceptionReactor;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Order(-2)
@Component
public class GlobalExceptionHandlerReactor implements ErrorWebExceptionHandler {

    public Mono<Void> handle(ServerWebExchange exchange, Throwable throwable) {

        if (throwable instanceof BadRequestExceptionReactor ex) {

            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
            exchange.getResponse().setStatusCode(ex.getStatus());

            ObjectMapper mapper = new ObjectMapper();
            String response = null;
            try {
                response = mapper.writeValueAsString(new CustomResponseExceptionReactor(ex.getMessage(), ex.getCode(), ex.getTrack()));
            } catch (JsonProcessingException e) {
                exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
                return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap("Error converting to JSON".getBytes(StandardCharsets.UTF_8))));
            }

            DataBufferFactory bufferFactory = exchange.getResponse().bufferFactory();
            DataBuffer dataBuffer = bufferFactory.wrap(response.getBytes());

            return exchange.getResponse().writeWith(Mono.just(dataBuffer)).then();
        }

        throw new RuntimeException(throwable.getMessage());
    }
}
