package com.webflux.sample.service.impl;

import com.webflux.sample.document.PersonsDocument;
import com.webflux.sample.model.PersonCreatedResponseBody;
import com.webflux.sample.model.PersonReadResponseBody;
import com.webflux.sample.model.PersonRequestBody;
import com.webflux.sample.repository.AddressRepository;
import com.webflux.sample.repository.PersonsRepository;
import com.webflux.sample.repository.PhonesRepository;
import com.webflux.sample.service.PersonService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;

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
    public Mono<PersonReadResponseBody> find(String id) {
        return personsRepository.findById(id)
                .doFirst(() -> log.info(">>> Find started..."))
                .doOnTerminate(() -> log.info(">>> Find finished..."))
                .doOnSuccess(success -> log.info("The Find result is {}", success))
                .doOnError(error -> log.error( "The Find error is {}", String.valueOf(error)))
                .flatMap(this::findAddress)
                .flatMap(this::findPhones)
                .flatMap(this::buildResponse)
                .map(person -> person);
    }

    @Override
    public Flux<PersonReadResponseBody> findAll() {
        return null;
    }

    private Mono<PersonsDocument> findAddress(PersonsDocument personsDocument) {
        return addressRepository.findById(personsDocument.getId())
                .doOnSuccess(success -> log.info("The findAddress result is {}", success))
                .doOnError(error -> log.error( "The Find findAddress is {}", String.valueOf(error)))
                .map(addressDocument -> {
                    personsDocument.setAddresses(Collections.singleton(addressDocument));
                    return personsDocument;
                });
    }

    private Mono<PersonsDocument> findPhones(PersonsDocument personsDocument) {
        return phonesRepository.findById(personsDocument.getId())
                .doOnSuccess(success -> log.info("The findPhones result is {}", success))
                .doOnError(error -> log.error( "The findPhones error is {}", String.valueOf(error)))
                .map(phonesDocument -> {
                    personsDocument.setPhones(Collections.singleton(phonesDocument));
                    return personsDocument;
                });
    }

    private Mono<PersonReadResponseBody> buildResponse(PersonsDocument personsDocument) {
        return Mono.just(personsDocument).map(document -> {
            PersonReadResponseBody response = new PersonReadResponseBody();
            response.setId(document.getId());
            response.setName(document.getName());
            response.setEmail(document.getEmail());
            response.setAddress(document.getAddresses());
            response.setPhones(document.getPhones());
            return response;
        });
    }

}
