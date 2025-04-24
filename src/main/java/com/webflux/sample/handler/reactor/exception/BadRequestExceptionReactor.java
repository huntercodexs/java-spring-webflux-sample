package com.webflux.sample.handler.reactor.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClientException;

@Getter
public class BadRequestExceptionReactor extends RestClientException {

    private final HttpStatus status = HttpStatus.BAD_REQUEST;
    private final String message;
    private int code = 0;
    private String track = null;

    public BadRequestExceptionReactor(String message) {
        super(message);
        this.message = message;
    }

    public BadRequestExceptionReactor(String message, int code) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public BadRequestExceptionReactor(String message, String track) {
        super(message);
        this.message = message;
        this.track = track;
    }

    public BadRequestExceptionReactor(String message, int code, String track) {
        super(message);
        this.message = message;
        this.code = code;
        this.track = track;
    }
}
