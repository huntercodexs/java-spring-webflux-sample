package com.webflux.sample.handler.noreactor.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClientException;

@Getter
public class CustomException extends RestClientException {

    private final HttpStatus status;
    private final String message;
    private final int code;
    private final String track;

    public CustomException(String message, int code, String track, HttpStatus status) {
        super(message);
        this.status = status;
        this.message = message;
        this.code = code;
        this.track = track;
    }

}
