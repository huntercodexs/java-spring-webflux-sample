package com.webflux.sample.exception;

import com.webflux.sample.handler.reactor.exception.HttpExceptionReactor;
import org.springframework.http.HttpStatus;

public class NotFoundExceptionReactor extends HttpExceptionReactor {

    public NotFoundExceptionReactor(String message) {
        super(message);
    }

    public NotFoundExceptionReactor(String message, int code) {
        super(message, code);
    }

    public NotFoundExceptionReactor(String message, String track) {
        super(message, track);
    }

    public NotFoundExceptionReactor(String message, int code, String track) {
        super(message, code, track);
    }

    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
