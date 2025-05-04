package com.webflux.sample.service.impl;

import com.webflux.sample.document.ExampleDocument;
import com.webflux.sample.exception.ConflictExceptionReactor;
import com.webflux.sample.repository.ExampleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.webflux.sample.DataBuilder.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@DisplayName("Test class ExampleServiceImplTest")
@ExtendWith({SpringExtension.class, MockitoExtension.class})
class ExampleServiceImplTest {

    @MockBean
    private ExampleRepository exampleRepository;

    @InjectMocks
    private ExampleServiceImpl exampleService;

    @BeforeEach
    public void init() {
        openMocks(this);

        setField(exampleService, "exampleRepository", exampleRepository);
    }

    @Test
    @DisplayName("SERVICE - Should Execute Example successfully")
    void shouldExecuteExecuteSuccessfully() {
        var exampleDocument = buildExampleDocumentForTests();
        when(exampleRepository.save(any(ExampleDocument.class))).thenReturn(Mono.just(exampleDocument));

        var request = buildExampleRequestBodyForTests();
        var result = exampleService.execute(Mono.just(request));

        StepVerifier.create(result)
                .expectNextMatches(created -> created.getId().equals(PRODUCT_ID))
                .verifyComplete();
    }

    @Test
    @DisplayName("SERVICE - Should NOT Execute Example successfully")
    void shouldNotExecuteExecuteSuccessfully() {
        var exampleDocument = buildExampleDocumentForTests();
        var request = buildExampleRequestBodyForTests();
        when(exampleRepository.findByProductAndActiveTrue(anyString())).thenReturn(Mono.just(exampleDocument));
        when(exampleRepository.save(any(ExampleDocument.class))).thenReturn(Mono.just(exampleDocument));

        var result = exampleService.execute(Mono.just(request));

        StepVerifier.create(result)
                .expectErrorMatches(e -> e instanceof ConflictExceptionReactor && e.getMessage().equals("Product already exists"))
                .verify();
    }

}
