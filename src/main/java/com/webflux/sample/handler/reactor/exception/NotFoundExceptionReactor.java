package com.webflux.sample.handler.reactor.exception;

import org.springframework.http.HttpStatus;

public class NotFoundExceptionReactor extends HttpExceptionReactor {

    public NotFoundExceptionReactor(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

    public NotFoundExceptionReactor(String message, int code) {
        super(message, code, HttpStatus.NOT_FOUND);
    }

    public NotFoundExceptionReactor(String message, String track) {
        super(message, track, HttpStatus.NOT_FOUND);
    }

    public NotFoundExceptionReactor(String message, int code, String track) {
        super(message, code, track, HttpStatus.NOT_FOUND);
    }
}
