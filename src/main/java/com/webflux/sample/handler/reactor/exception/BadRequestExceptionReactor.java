package com.webflux.sample.handler.reactor.exception;

import org.springframework.http.HttpStatus;

public class BadRequestExceptionReactor extends HttpExceptionReactor {

    public BadRequestExceptionReactor(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public BadRequestExceptionReactor(String message, int code) {
        super(message, code, HttpStatus.BAD_REQUEST);
    }

    public BadRequestExceptionReactor(String message, String track) {
        super(message, track, HttpStatus.BAD_REQUEST);
    }

    public BadRequestExceptionReactor(String message, int code, String track) {
        super(message, code, track, HttpStatus.BAD_REQUEST);
    }
}
