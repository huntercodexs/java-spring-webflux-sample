package com.webflux.sample.handler.noreactor.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClientException;

@Getter
public class BadRequestException extends RestClientException {

    private final HttpStatus status = HttpStatus.BAD_REQUEST;
    private final String message;
    private int code = 0;
    private String track = null;

    public BadRequestException(String message) {
        super(message);
        this.message = message;
    }

    public BadRequestException(String message, int code) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public BadRequestException(String message, String track) {
        super(message);
        this.message = message;
        this.track = track;
    }

    public BadRequestException(String message, int code, String track) {
        super(message);
        this.message = message;
        this.code = code;
        this.track = track;
    }

}
