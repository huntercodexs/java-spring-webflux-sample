package com.webflux.sample.exception;

import com.webflux.sample.handler.reactor.exception.HttpExceptionReactor;
import org.springframework.http.HttpStatus;

public class UnAuthorizedExceptionReactor extends HttpExceptionReactor {

    public UnAuthorizedExceptionReactor(String message) {
        super(message);
    }

    public UnAuthorizedExceptionReactor(String message, int code) {
        super(message, code);
    }

    public UnAuthorizedExceptionReactor(String message, String track) {
        super(message, track);
    }

    public UnAuthorizedExceptionReactor(String message, int code, String track) {
        super(message, code, track);
    }

    public HttpStatus getStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}
