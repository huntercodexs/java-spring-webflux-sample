package com.webflux.sample.handler.reactor.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClientException;

@Getter
public class HttpExceptionReactor extends RestClientException {

    public HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    public String message;
    public int code = 0;
    public String track = null;

    public HttpExceptionReactor(String message) {
        super(message);
        this.message = message;
    }

    public HttpExceptionReactor(String message, int code) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public HttpExceptionReactor(String message, String track) {
        super(message);
        this.message = message;
        this.track = track;
    }

    public HttpExceptionReactor(String message, int code, String track) {
        super(message);
        this.message = message;
        this.code = code;
        this.track = track;
    }
}
