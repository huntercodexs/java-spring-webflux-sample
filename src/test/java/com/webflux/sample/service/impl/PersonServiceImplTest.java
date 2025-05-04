package com.webflux.sample.service.impl;

import com.webflux.sample.document.PersonsDocument;
import com.webflux.sample.repository.AddressRepository;
import com.webflux.sample.repository.PersonsRepository;
import com.webflux.sample.repository.PhonesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.webflux.sample.DataBuilder.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@DisplayName("Test class PersonServiceImplTest")
@ExtendWith({SpringExtension.class, MockitoExtension.class})
class PersonServiceImplTest {

    @MockBean
    private PersonsRepository personsRepository;

    @MockBean
    private AddressRepository addressRepository;

    @MockBean
    private PhonesRepository phonesRepository;

    @InjectMocks
    private PersonServiceImpl personService;

    @BeforeEach
    public void init() {
        openMocks(this);

        setField(personService, "personsRepository", personsRepository);
        setField(personService, "addressRepository", addressRepository);
        setField(personService, "phonesRepository", phonesRepository);
    }

    @Test
    @DisplayName("SERVICE - Should Create Person successfully")
    void shouldCreatePersonSuccessfully() {
        var saveResult = buildPersonsDocumentForTests();
        when(personsRepository.save(any(PersonsDocument.class))).thenReturn(Mono.just(saveResult));

        var request = buildPersonRequestBodyForTests();
        var result = personService.create(Mono.just(request));

        StepVerifier.create(result)
                .expectNextMatches(created -> created.getId().equals(PERSON_ID))
                .verifyComplete();
    }

    @Test
    @DisplayName("SERVICE - Should Read One Person successfully")
    void shouldReadOnePersonSuccessfully() {
        var addressDocument = buildAddressDocumentForTests();
        var phonesDocument = buildPhonesDocumentForTests();
        var personDocument = buildPersonsDocumentForTests();
        when(addressRepository.findAllByPersonIdAndActiveTrue(anyString())).thenReturn(Flux.just(addressDocument));
        when(phonesRepository.findAllByPersonIdAndActiveTrue(anyString())).thenReturn(Flux.just(phonesDocument));
        when(personsRepository.findByIdAndActiveTrue(anyString())).thenReturn(Mono.just(personDocument));

        var result = personService.read(PERSON_ID);

        StepVerifier.create(result)
                .expectNextMatches(find ->
                        (find.getId().equals(PERSON_ID) && find.getName().equals(PERSON_NAME)))
                .verifyComplete();
    }

    @Test
    @DisplayName("SERVICE - Should Read All Persons successfully")
    void shouldReadAllPersonsSuccessfully() {
        var personDocument = buildPersonsDocumentForTests();
        when(personsRepository.findAllByActiveTrue()).thenReturn(Flux.just(personDocument));

        var result = personService.readAll();

        StepVerifier.create(result)
                .expectNextMatches(find -> (
                        find.getPersons().getFirst().getId().equals(PERSON_ID)) && (
                                find.getPersons().getFirst().getName().equals(PERSON_NAME))
                )
                .verifyComplete();
    }

    @Test
    @DisplayName("SERVICE - Should Update Person successfully")
    void shouldUpdatePersonSuccessfully() {
        var personsDocument = buildPersonsDocumentForTests();
        when(personsRepository.findByIdAndActiveTrue(anyString())).thenReturn(Mono.just(personsDocument));
        when(personsRepository.save(any(PersonsDocument.class))).thenReturn(Mono.just(personsDocument));

        var request = buildPersonRequestBodyForTests();
        var result = personService.update(PERSON_ID, Mono.just(request));

        StepVerifier.create(result)
                .expectNextMatches(created -> created.getId().equals(PERSON_ID))
                .verifyComplete();
    }

    @Test
    @DisplayName("SERVICE - Should Delete Person successfully")
    void shouldDeletePersonSuccessfully() {
        var personsDocument = buildPersonsDocumentForTests();
        when(personsRepository.findByIdAndActiveTrue(anyString())).thenReturn(Mono.just(personsDocument));
        when(personsRepository.save(any(PersonsDocument.class))).thenReturn(Mono.just(personsDocument));

        var result = personService.delete(PERSON_ID);

        StepVerifier.create(result)
                .expectNextMatches(created -> created.getMessage().equals("Person deleted successfully"))
                .verifyComplete();
    }

    @Test
    @DisplayName("SERVICE - Should Patch Person successfully")
    void shouldPatchPersonSuccessfully() {
        var personsDocument = buildPersonsDocumentForTests();
        when(personsRepository.findByIdAndActiveTrue(anyString())).thenReturn(Mono.just(personsDocument));
        when(personsRepository.save(any(PersonsDocument.class))).thenReturn(Mono.just(personsDocument));

        var request = buildPersonPatchRequestBodyForTests();
        var result = personService.patch(PERSON_ID, Mono.just(request));

        StepVerifier.create(result)
                .expectNextMatches(created -> created.getMessage().equals("Person patched successfully"))
                .verifyComplete();
    }

    @Test
    @DisplayName("SERVICE - Should Patch By Path Person successfully")
    void shouldPatchByPathPersonSuccessfully() {
        var personsDocument = buildPersonsDocumentForTests();
        when(personsRepository.findByIdAndActiveTrue(anyString())).thenReturn(Mono.just(personsDocument));
        when(personsRepository.save(any(PersonsDocument.class))).thenReturn(Mono.just(personsDocument));

        var result = personService.patchByPath(PERSON_ID, FIELD_NAME, FIELD_VALUE);

        StepVerifier.create(result)
                .expectNextMatches(created -> created.getMessage().equals("Person patched by path successfully"))
                .verifyComplete();
    }
}
