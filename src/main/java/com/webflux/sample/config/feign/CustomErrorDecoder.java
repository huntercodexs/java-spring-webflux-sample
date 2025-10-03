package com.webflux.sample.config.feign;

import com.webflux.sample.handler.reactor.exception.HttpExceptionReactor;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        return new HttpExceptionReactor("Error occurred with status code: " + response.status(), response.status());
    }

}
