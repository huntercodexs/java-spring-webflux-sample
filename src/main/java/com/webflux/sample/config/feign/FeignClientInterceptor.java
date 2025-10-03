package com.webflux.sample.config.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.util.ObjectUtils.isEmpty;

@Component
public class FeignClientInterceptor implements RequestInterceptor {

    private static final String USER_CREDENTIALS_INTEGRATION = "user-credentials-integration";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Override
    public void apply(RequestTemplate requestTemplate) {

        if (Objects.equals(USER_CREDENTIALS_INTEGRATION, requestTemplate.feignTarget().name())) {
            clientHeaderInterceptor(requestTemplate);
        }

        if (isEmpty(requestTemplate.body()) && isNotGetRequest(requestTemplate.method())) {
            requestTemplate.body("{}");
        }

    }

    private boolean isNotGetRequest(String method) {
        return !method.equalsIgnoreCase(GET.toString());
    }

    private void clientHeaderInterceptor(RequestTemplate requestTemplate) {
        requestTemplate.header(CONTENT_TYPE, APPLICATION_JSON);
        // Get the token from the security context or any other source
        // For demonstration, we'll use a hardcoded token
        // In a real application, replace this with actual token retrieval logic
        String jwtToken = "Bearer token_value";
        requestTemplate.header(AUTHORIZATION_HEADER, jwtToken);
    }

}
