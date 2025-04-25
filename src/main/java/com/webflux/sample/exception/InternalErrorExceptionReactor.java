package com.webflux.sample.exception;

import com.webflux.sample.handler.reactor.exception.HttpExceptionReactor;
import org.springframework.http.HttpStatus;

public class InternalErrorExceptionReactor extends HttpExceptionReactor {

    public InternalErrorExceptionReactor(String message) {
        super(message);
    }

    public InternalErrorExceptionReactor(String message, int code) {
        super(message, code);
    }

    public InternalErrorExceptionReactor(String message, String track) {
        super(message, track);
    }

    public InternalErrorExceptionReactor(String message, int code, String track) {
        super(message, code, track);
    }

    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
