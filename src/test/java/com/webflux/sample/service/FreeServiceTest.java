package com.webflux.sample.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.blockhound.BlockHound;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Log4j2
@DisplayName("Test class FreeServiceTest")
@ExtendWith({SpringExtension.class, MockitoExtension.class})
class FreeServiceTest {

    @BeforeAll
    static void setUp() {
        BlockHound.install(
                builder -> builder.allowBlockingCallsInside("java.base/java.lang.Thread", "sleep")
        );
    }

    @Test
    void firstFreeServiceTest() {
        assert true;
    }

    @Test
    void shouldVerifyBlockHoundWorkTest() {
        Exception exception = null;
        try {
            Mono.delay(Duration.ofSeconds(1))
                    .doFirst(() -> log.info("Should fail"))
                    .doOnNext(it -> {
                        try {
                            Thread.sleep(3);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .block();
        } catch (Exception e) {
            exception = e;
        }
        assert exception != null && exception.getCause() instanceof reactor.blockhound.BlockingOperationError;
    }

}
