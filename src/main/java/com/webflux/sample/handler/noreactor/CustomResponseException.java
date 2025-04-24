package com.webflux.sample.handler.noreactor;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomResponseException {

    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String code;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String track;

    public CustomResponseException(String message, int code, String track) {
        this.message = message;
        if (code == 0) {
            this.code = null;
        } else {
            this.code = String.valueOf(code);
        }
        this.track = track;
    }

}

