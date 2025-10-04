package com.webflux.sample.service;

import com.webflux.sample.exception.InternalErrorExceptionReactor;
import com.webflux.sample.exception.NotFoundExceptionReactor;
import com.webflux.sample.handler.reactor.exception.HttpExceptionReactor;
import io.swagger.codegen.v3.service.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Log4j2
@Service
@AllArgsConstructor
public class ExceptionService {

    public Mono<Object> illegalArgumentException() {
        return Mono.just(new Object())
                .map(mapper -> {
                    throw new IllegalArgumentException("IllegalArgumentException");
                });
    }

    public Mono<Object> nullPointerException() {
        return Mono.just(new Object())
                .map(mapper -> {
                    throw new NullPointerException("NullPointerException");
                });
    }

    public Mono<Object> illegalAccessException() {
        return Mono.just(new Object())
                .map(mapper -> {
                    try {
                        throw new IllegalAccessException("IllegalAccessException");
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    public Mono<Object> httpException() {
        return Mono.just(new Object())
                .map(mapper -> {
                    try {
                        throw new HttpException("HttpException");
                    } catch (HttpException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    public Mono<Object> indexOutOfBoundsException() {
        return Mono.just(new Object())
                .map(mapper -> {
                    throw new IndexOutOfBoundsException("IndexOutOfBoundsException");
                });
    }

    public Mono<Object> notFoundExceptionReactor() {
        return Mono.just(new Object())
                .map(mapper -> {
                    throw new NotFoundExceptionReactor("Not Found for Reactor", 123, "8540958349058439085490");
                });
    }

    public Mono<Object> notFoundException() {
        return Mono.just(new Object())
                .map(mapper -> {
                    throw new NotFoundException("NotFoundException-Swagger");
                });
    }

    public Mono<Object> error() {
        return Mono.just(new Object())
                .map(mapper -> {
                    throw new Error("Error");
                })
                .doOnSuccess(success -> {
                    log.info("OK");
                })
                .doOnError(HttpExceptionReactor.class, error -> {
                    throw new InternalErrorExceptionReactor("Internal Error for Reactor: " + error);
                })
                .doOnError(InternalError.class, error -> {
                    throw new InternalError("Internal Error Java Lang: " + error);
                });
    }

    public Mono<Object> internalError() {
        return Mono.just(new Object())
                .doOnSuccess(success -> {
                    log.info("OK");
                    try {
                        throw new InternalError("Internal Error");
                    } catch (InternalError ie) {
                        throw new Error(ie.getMessage());
                    }
                })
                .doOnError(InternalError.class, error -> {
                    throw new InternalError("Internal Error Java Lang: " + error);
                });
    }

    public Mono<Object> internalErrorException() {
        return Mono.just(new Object())
                .map(mapper -> mapper)
                .doOnSuccess(success -> {
                    throw new NotFoundExceptionReactor("Not Found for Reactor", 123, "8540958349058439085490");
                })
                .doOnError(HttpExceptionReactor.class, error -> {
                    throw new InternalErrorExceptionReactor("Internal Error for Reactor: " + error);
                });
    }

}
