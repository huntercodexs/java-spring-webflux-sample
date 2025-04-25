package com.webflux.sample.exception;

import com.webflux.sample.handler.reactor.exception.HttpExceptionReactor;
import org.springframework.http.HttpStatus;

public class BadRequestExceptionReactor extends HttpExceptionReactor {

    public BadRequestExceptionReactor(String message) {
        super(message);
    }

    public BadRequestExceptionReactor(String message, int code) {
        super(message, code);
    }

    public BadRequestExceptionReactor(String message, String track) {
        super(message, track);
    }

    public BadRequestExceptionReactor(String message, int code, String track) {
        super(message, code, track);
    }

    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
