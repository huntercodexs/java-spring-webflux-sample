package com.webflux.sample.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DisplayName("Test class WebClientSampleServiceTest")
@ExtendWith({SpringExtension.class, MockitoExtension.class})
class WebClientSampleServiceTest {

    @Test
    void firstWebClientSampleServiceTest() {
        assert true;
    }

}
