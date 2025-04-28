package com.webflux.sample.exception;

import com.webflux.sample.handler.reactor.exception.HttpExceptionReactor;
import org.springframework.http.HttpStatus;

public class ConflictExceptionReactor extends HttpExceptionReactor {

    public ConflictExceptionReactor(String message) {
        super(message);
    }

    public ConflictExceptionReactor(String message, int code) {
        super(message, code);
    }

    public ConflictExceptionReactor(String message, String track) {
        super(message, track);
    }

    public ConflictExceptionReactor(String message, int code, String track) {
        super(message, code, track);
    }

    public HttpStatus getStatus() {
        return HttpStatus.CONFLICT;
    }
}
