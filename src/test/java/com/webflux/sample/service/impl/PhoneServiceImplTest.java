package com.webflux.sample.service.impl;

import com.webflux.sample.repository.AddressRepository;
import com.webflux.sample.service.AddressService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static reactor.test.StepVerifier.create;

@DisplayName("Test class AddressServiceImplTest")
@ExtendWith({SpringExtension.class, MockitoExtension.class})
class PhoneServiceImplTest {

    @MockBean
    AddressService addressService;

    @MockBean
    AddressRepository addressRepository;

    @BeforeEach
    public void init() {
        openMocks(this);
    }

    @Test
    @DisplayName("When create address with successfully")
    void shouldCreateAddressWithSuccess() {
        when(addressRepository.save(any())).thenReturn(Mono.just(null));
        var response = addressService.create("123", null);
        create(response)
                .assertNext(Assertions::assertNotNull)
                .verifyComplete();
    }
}
