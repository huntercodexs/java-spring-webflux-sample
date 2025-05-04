package com.webflux.sample.service.impl;

import com.webflux.sample.document.PhonesDocument;
import com.webflux.sample.exception.BadRequestExceptionReactor;
import com.webflux.sample.exception.NotFoundExceptionReactor;
import com.webflux.sample.repository.PhonesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.blockhound.BlockHound;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.webflux.sample.DataBuilder.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@DisplayName("Test class PhonesServiceImplTest")
@ExtendWith({SpringExtension.class, MockitoExtension.class})
class PhoneServiceImplTest {

    @MockBean
    private PhonesRepository phonesRepository;

    @InjectMocks
    private PhoneServiceImpl phoneService;

    @BeforeEach
    public void init() {
        BlockHound.install();
        openMocks(this);

        setField(phoneService, "phonesRepository", phonesRepository);
    }

    @Test
    @DisplayName("SERVICE - Should Create Phone successfully")
    void shouldCreatePhoneSuccessfully() {
        var saveResult = buildPhonesDocumentForTests();
        when(phonesRepository.save(any(PhonesDocument.class))).thenReturn(Mono.just(saveResult));

        var request = buildPhoneRequestBodyForTests();
        var result = phoneService.create(PERSON_ID, Mono.just(request));

        StepVerifier.create(result)
                .expectNextMatches(created -> created.getId().equals(PERSON_ID))
                .verifyComplete();
    }

    @Test
    @DisplayName("SERVICE - Should NOT Create Phone successfully")
    void shouldNotCreatePhoneSuccessfully() {
        when(phonesRepository.save(any(PhonesDocument.class))).thenReturn(Mono.error(new BadRequestExceptionReactor("Some Bad Request")));

        var request = buildPhoneRequestBodyForTests();
        var result = phoneService.create(PERSON_ID, Mono.just(request));

        StepVerifier.create(result)
                .expectErrorMatches(e -> e instanceof BadRequestExceptionReactor && e.getMessage().equals("Some Bad Request"))
                .verify();
    }

    @Test
    @DisplayName("SERVICE - Should Find Phone successfully")
    void shouldFindPhoneSuccessfully() {
        var phonesDocument = buildPhonesDocumentForTests();
        when(phonesRepository.findAllByPersonIdAndActiveTrue(anyString())).thenReturn(Flux.just(phonesDocument));

        var result = phoneService.find(PERSON_ID);

        StepVerifier.create(result)
                .expectNextMatches(find ->
                        (find.getPhones().getFirst().getPhoneNumber().equals(PHONE_NUMBER) &&
                                (find.getPhones().getFirst().getPhoneType().equals(PHONE_TYPE_MOBILE.getValue()))))
                .verifyComplete();
    }

    @Test
    @DisplayName("SERVICE - Should NOT Find Phone successfully")
    void shouldNotFindPhoneSuccessfully() {
        when(phonesRepository.findAllByPersonIdAndActiveTrue(anyString())).thenReturn(Flux.error(new NotFoundExceptionReactor("Some Not Found")));

        var result = phoneService.find(PERSON_ID);

        StepVerifier.create(result)
                .expectErrorMatches(e -> e instanceof NotFoundExceptionReactor && e.getMessage().equals("Some Not Found"))
                .verify();
    }
}
