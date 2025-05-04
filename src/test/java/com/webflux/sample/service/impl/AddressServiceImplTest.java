package com.webflux.sample.service.impl;

import com.webflux.sample.document.AddressDocument;
import com.webflux.sample.exception.BadRequestExceptionReactor;
import com.webflux.sample.exception.NotFoundExceptionReactor;
import com.webflux.sample.repository.AddressRepository;
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

@DisplayName("Test class AddressServiceImplTest")
@ExtendWith({SpringExtension.class, MockitoExtension.class})
class AddressServiceImplTest {

    @MockBean
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressServiceImpl addressService;

    @BeforeEach
    void setUp() {
        BlockHound.install();
        openMocks(this);

        setField(addressService, "addressRepository", addressRepository);
    }

    @Test
    @DisplayName("SERVICE - Should Create Address successfully")
    void shouldCreateAddressSuccessfully() {
        var saveResult = buildAddressDocumentForTests();
        when(addressRepository.save(any(AddressDocument.class))).thenReturn(Mono.just(saveResult));

        var request = buildAddressRequestBodyForTests();
        var result = addressService.create(PERSON_ID, Mono.just(request));

        StepVerifier.create(result)
                .expectNextMatches(created -> created.getId().equals(PERSON_ID))
                .verifyComplete();
    }

    @Test
    @DisplayName("SERVICE - Should NOT Create Address successfully")
    void shouldNotCreateAddressSuccessfully() {
        when(addressRepository.save(any(AddressDocument.class))).thenReturn(Mono.error(new BadRequestExceptionReactor("Some Bad Request")));

        var request = buildAddressRequestBodyForTests();
        var result = addressService.create(PERSON_ID, Mono.just(request));

        StepVerifier.create(result)
                .expectErrorMatches(e -> e instanceof BadRequestExceptionReactor && e.getMessage().equals("Some Bad Request"))
                .verify();
    }

    @Test
    @DisplayName("SERVICE - Should Find Address successfully")
    void shouldFindAddressSuccessfully() {
        var addressDocument = buildAddressDocumentForTests();
        when(addressRepository.findAllByPersonIdAndActiveTrue(anyString())).thenReturn(Flux.just(addressDocument));

        var result = addressService.find(PERSON_ID);

        StepVerifier.create(result)
                .expectNextMatches(find ->
                        (find.getAddresses().getFirst().getStreet().equals(ADDRESS_STREET) &&
                        (find.getAddresses().getFirst().getNumber() == ADDRESS_NUMBER)) &&
                        (find.getAddresses().getFirst().getCity().equals(ADDRESS_CITY)))
                .verifyComplete();
    }

    @Test
    @DisplayName("SERVICE - Should NOT Find Address successfully")
    void shouldNotFindAddressSuccessfully() {
        when(addressRepository.findAllByPersonIdAndActiveTrue(anyString())).thenReturn(Flux.error(new NotFoundExceptionReactor("Some Not Found")));

        var result = addressService.find(PERSON_ID);

        StepVerifier.create(result)
                .expectErrorMatches(e -> e instanceof NotFoundExceptionReactor && e.getMessage().equals("Some Not Found"))
                .verify();
    }
}
