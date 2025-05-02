package com.webflux.sample.service.impl;

import com.webflux.sample.builder.PersonBuilder;
import com.webflux.sample.document.PersonsDocument;
import com.webflux.sample.exception.NotFoundExceptionReactor;
import com.webflux.sample.model.*;
import com.webflux.sample.repository.AddressRepository;
import com.webflux.sample.repository.PersonsRepository;
import com.webflux.sample.repository.PhonesRepository;
import com.webflux.sample.service.PersonService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.webflux.sample.builder.PersonBuilder.*;

@Log4j2
@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {

    private PersonsRepository personsRepository;
    private AddressRepository addressRepository;
    private PhonesRepository phonesRepository;

    @Override
    public Mono<PersonCreatedResponseBody> create(Mono<PersonRequestBody> createPersonRequest) {
        return createPersonRequest.flatMap(personRequest -> {
            PersonsDocument person = new PersonsDocument();
            person.setName(personRequest.getName());
            person.setEmail(personRequest.getEmail());

            return personsRepository.save(person)
                    .doFirst(() -> log.info(">>> Save started..."))
                    .doOnTerminate(() -> log.info(">>> Save finished..."))
                    .doOnSuccess(success -> log.info("The Save result is {}", success))
                    .doOnError(error -> log.error("The Save error is {}", String.valueOf(error)))
                    .map(savedPerson -> {
                        PersonCreatedResponseBody response = new PersonCreatedResponseBody();
                        response.setId(savedPerson.getId());
                        return response;
                    });
        });
    }

    @Override
    public Mono<PersonReadResponseBody> read(String personId) {
        return personsRepository.findByIdAndActiveTrue(personId)
                .doFirst(() -> log.info("Find started..."))
                .doOnTerminate(() -> log.info("Find finished..."))
                .doOnSuccess(success -> log.info("The Find result is {}", success))
                .doOnError(error -> log.error("The Find error is {}", String.valueOf(error)))
                .flatMap(this::findAddress)
                .flatMap(this::findPhones)
                .flatMap(this::responseOne)
                .map(person -> person)
                .switchIfEmpty(Mono.error(new NotFoundExceptionReactor("Person Not Found")));
    }

    @Override
    public Mono<PersonsReadResponseBody> readAll() {
        return personsRepository.findAllByActiveTrue()
                .doFirst(() -> log.info("findAll is started"))
                .doOnTerminate(() -> log.info("findAll is finished"))
                .doOnError(error -> log.error("The Find findAll is {}", String.valueOf(error)))
                .collectList()
                .doOnSuccess(success -> log.info("The findAll result is {}", success))
                .flatMap(this::responseAll);
    }

    @Override
    public Mono<PersonReadResponseBody> update(String personId, Mono<PersonRequestBody> updatePersonRequest) {
        return updatePersonRequest.flatMap(personRequest -> {
            return personsRepository.findByIdAndActiveTrue(personId)
                    .doFirst(() -> log.info(">>> Update started..."))
                    .doOnTerminate(() -> log.info(">>> Update finished..."))
                    .doOnSuccess(success -> log.info("The Update result is {}", success))
                    .doOnError(error -> log.error("The Update error is {}", String.valueOf(error)))
                    .flatMap(personsDocument -> {

                        personsDocument.setName(personRequest.getName());
                        personsDocument.setEmail(personRequest.getEmail());
                        personsDocument.setUpdatedAt(LocalDateTime.now());

                        return personsRepository.save(personsDocument)
                                .doFirst(() -> log.info(">>> Save Update started..."))
                                .doOnTerminate(() -> log.info(">>> Save Update finished..."))
                                .doOnSuccess(success -> log.info("The Save Update result is {}", success))
                                .doOnError(error -> log.error("The Save Update error is {}", String.valueOf(error)))
                                .map(PersonBuilder::buildPersonResponse);

                    });
        });
    }

    @Override
    public Mono<GenericsResponseBody> delete(String personId) {
        return personsRepository.findByIdAndActiveTrue(personId)
                .doFirst(() -> log.info(">>> Delete started..."))
                .doOnTerminate(() -> log.info(">>> Delete finished..."))
                .doOnSuccess(success -> log.info("The Delete result is {}", success))
                .doOnError(error -> log.error("The Delete error is {}", String.valueOf(error)))
                .flatMap(personsDocument -> {

                    personsDocument.setActive(false);
                    personsDocument.setDeletedAt(LocalDateTime.now());

                    return personsRepository.save(personsDocument)
                            .doFirst(() -> log.info(">>> Save Delete started..."))
                            .doOnTerminate(() -> log.info(">>> Save Delete finished..."))
                            .doOnSuccess(success -> log.info("The Save Delete result is {}", success))
                            .doOnError(error -> log.error("The Save Delete error is {}", String.valueOf(error)))
                            .map(mapper -> buildGenericsResponse("Person deleted successfully"));
                });
    }

    @Override
    public Mono<GenericsResponseBody> patch(String personId, Mono<PersonPatchRequestBody> patchRequestBodyMono) {
        return patchRequestBodyMono.flatMap(patchRequest ->
                personsRepository.findByIdAndActiveTrue(personId)
                        .doFirst(() -> log.info(">>> Patch started..."))
                        .doOnTerminate(() -> log.info(">>> Patch finished..."))
                        .doOnSuccess(success -> log.info("The Patch result is {}", success))
                        .doOnError(error -> log.error("The Patch error is {}", String.valueOf(error)))
                        .flatMap(personsDocument ->
                                personsRepository.save(buildAndPatch(patchRequest, personsDocument)))
                        .map(mapper -> buildGenericsResponse("Person patched successfully")));
    }

    @Override
    public Mono<GenericsResponseBody> patchByPath(String personId, String fieldName, Object fieldValue) {
        return personsRepository.findByIdAndActiveTrue(personId)
                .doFirst(() -> log.info(">>> patchByPath started..."))
                .doOnTerminate(() -> log.info(">>> patchByPath finished..."))
                .doOnSuccess(success -> log.info("The patchByPath result is {}", success))
                .doOnError(error -> log.error("The patchByPath error is {}", String.valueOf(error)))
                .flatMap(document ->
                        personsRepository.save(buildAndPatchByPath(document, fieldName, fieldValue)))
                .map(mapper -> buildGenericsResponse("Person patched successfully"));
    }

    private Mono<PersonsDocument> findAddress(PersonsDocument personsDocument) {
        return addressRepository.findAllByPersonIdAndActiveTrue(personsDocument.getId())
                .doFirst(() -> log.info("findAddress is started"))
                .doOnTerminate(() -> log.info("findAddress is finished"))
                .doOnError(error -> log.error("The Find findAddress is {}", String.valueOf(error)))
                .collectList()
                .doOnSuccess(success -> log.info("The findAddress result is {}", success))
                .map(addressDocuments -> {

                    addressDocuments.forEach(addressDoc -> {
                        personsDocument.getAddresses().add(addressDoc);
                    });

                    return personsDocument;
                });
    }

    private Mono<PersonsDocument> findPhones(PersonsDocument personsDocument) {
        return phonesRepository.findAllByPersonIdAndActiveTrue(personsDocument.getId())
                .doFirst(() -> log.info("findPhones is started"))
                .doOnTerminate(() -> log.info("findPhones is finished"))
                .doOnError(error -> log.error("The Find findPhones is {}", String.valueOf(error)))
                .collectList()
                .doOnSuccess(success -> log.info("The findPhones result is {}", success))
                .map(phonesDocuments -> {

                    phonesDocuments.forEach(phoneDoc -> {
                        personsDocument.getPhones().add(phoneDoc);
                    });

                    return personsDocument;
                });
    }

    private Mono<PersonsReadResponseBody> responseAll(List<PersonsDocument> personsDocumentList) {
        List<PersonOnlyReadResponseBody> personsList = new ArrayList<>();
        personsDocumentList.forEach(personsDocument -> {
            personsList.add(buildPersonOnlyResponse(personsDocument));
        });
        PersonsReadResponseBody personsRead = new PersonsReadResponseBody();
        personsRead.setPersons(personsList);
        return Mono.just(personsRead);
    }

    private Mono<PersonReadResponseBody> responseOne(PersonsDocument personsDocument) {
        return Mono.just(personsDocument).map(PersonBuilder::buildPersonResponse);
    }

}
