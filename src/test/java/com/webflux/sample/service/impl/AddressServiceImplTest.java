package com.webflux.sample.service.impl;

import com.webflux.sample.repository.AddressRepository;
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
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.util.ReflectionTestUtils.setField;
import static reactor.core.publisher.Mono.when;

@DisplayName("Test class AddressServiceImplTest")
@ExtendWith({SpringExtension.class, MockitoExtension.class})
class AddressServiceImplTest {

    @MockBean
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressServiceImpl addressService;

    @BeforeEach
    void setUp() {
        openMocks(this);

        setField(addressService, "addressRepository", addressRepository);
    }

    @Test
    @DisplayName("SERVICE - Should Create Address successfully")
    void shouldCreateAddressSuccessfully() {
        var response = buildAddressCreatedResponseBodyForTests();
        when(addressRepository.save(any())).thenReturn(Mono.just(response));

        var request = buildAddressRequestBodyForTests();
        var result = addressService.create(PERSON_ID, Mono.just(request));

        StepVerifier.create(result)
                .expectNextMatches(created -> created.getId().equals(PERSON_ID))
                .verifyComplete();
    }
}
