package com.webflux.sample.service.impl;

import com.webflux.sample.document.AddressDocument;
import com.webflux.sample.document.PersonsDocument;
import com.webflux.sample.document.PhonesDocument;
import com.webflux.sample.model.*;
import com.webflux.sample.repository.AddressRepository;
import com.webflux.sample.repository.PersonsRepository;
import com.webflux.sample.repository.PhonesRepository;
import com.webflux.sample.service.PersonService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.webflux.sample.util.WebFluxUtil.datetimeUtil;
import static java.util.Objects.isNull;

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
    public Mono<PersonReadResponseBody> find(String personId) {
        return personsRepository.findByIdAndActiveTrue(personId)
                .doFirst(() -> log.info("Find started..."))
                .doOnTerminate(() -> log.info("Find finished..."))
                .doOnSuccess(success -> log.info("The Find result is {}", success))
                .doOnError(error -> log.error("The Find error is {}", String.valueOf(error)))
                .flatMap(this::findAddress)
                .flatMap(this::findPhones)
                .flatMap(this::buildResponseForOne)
                .map(person -> person);
    }

    @Override
    public Mono<PersonsReadResponseBody> findAll() {
        return personsRepository.findAllByActiveTrue()
                .doFirst(() -> log.info("findAll is started"))
                .doOnTerminate(() -> log.info("findAll is finished"))
                .doOnError(error -> log.error("The Find findAll is {}", String.valueOf(error)))
                .collectList()
                .doOnSuccess(success -> log.info("The findAll result is {}", success))
                .flatMap(this::buildResponseForAll);
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

    private Mono<PersonsReadResponseBody> buildResponseForAll(List<PersonsDocument> personsDocumentList) {
        List<PersonOnlyReadResponseBody> personsList = new ArrayList<>();
        personsDocumentList.forEach(personsDocument -> {
            personsList.add(this.buildPersonOnlyResponse(personsDocument));
        });
        PersonsReadResponseBody personsRead = new PersonsReadResponseBody();
        personsRead.setPersons(personsList);
        return Mono.just(personsRead);
    }

    private Mono<PersonReadResponseBody> buildResponseForOne(PersonsDocument personsDocument) {
        return Mono.just(personsDocument).map(this::buildPersonResponse);
    }

    private PersonReadResponseBody buildPersonResponse(PersonsDocument document) {
        PersonReadResponseBody response = new PersonReadResponseBody();
        response.setId(document.getId());
        response.setName(document.getName());
        response.setEmail(document.getEmail());
        response.setActive(document.isActive());
        response.setCreatedAt(datetimeUtil(document.getCreatedAt()));
        response.setUpdatedAt(datetimeUtil(document.getUpdatedAt()));
        response.setDeletedAt(datetimeUtil(document.getDeletedAt()));
        response.setAddress(this.buildAddressResponse(document.getAddresses()));
        response.setPhones(this.buildPhoneResponse(document.getPhones()));
        return response;
    }

    private PersonOnlyReadResponseBody buildPersonOnlyResponse(PersonsDocument document) {
        PersonOnlyReadResponseBody response = new PersonOnlyReadResponseBody();
        response.setId(document.getId());
        response.setName(document.getName());
        response.setEmail(document.getEmail());
        response.setActive(document.isActive());
        response.setCreatedAt(datetimeUtil(document.getCreatedAt()));
        response.setUpdatedAt(datetimeUtil(document.getUpdatedAt()));
        response.setDeletedAt(datetimeUtil(document.getDeletedAt()));
        return response;
    }

    private List<AddressResponseBody> buildAddressResponse(Set<AddressDocument> addressDocs) {
        if (isNull(addressDocs) || addressDocs.isEmpty()) return null;
        List<AddressResponseBody> addressResponseBody = new ArrayList<>();

        addressDocs.forEach(address -> {
            AddressResponseBody addresses = new AddressResponseBody();

            addresses.setStreet(address.getStreet());
            addresses.setNumber(address.getNumber());
            addresses.setZipcode(address.getZipcode());
            addresses.setCity(address.getCity());
            addresses.setActive(address.isActive());
            addresses.setCreatedAt(datetimeUtil(address.getCreatedAt()));
            addresses.setUpdatedAt(datetimeUtil(address.getUpdatedAt()));
            addresses.setDeletedAt(datetimeUtil(address.getDeletedAt()));

            addressResponseBody.add(addresses);
        });

        return addressResponseBody;
    }

    private List<PhoneResponseBody> buildPhoneResponse(Set<PhonesDocument> phonesDocs) {
        if (isNull(phonesDocs) || phonesDocs.isEmpty()) return null;
        List<PhoneResponseBody> phoneResponseBody = new ArrayList<>();

        phonesDocs.forEach(phone -> {
            PhoneResponseBody phones = new PhoneResponseBody();

            phones.setPhoneNumber(phone.getPhoneNumber());
            phones.setPhoneType(phone.getPhoneType());
            phones.setActive(phone.isActive());
            phones.setCreatedAt(datetimeUtil(phone.getCreatedAt()));
            phones.setUpdatedAt(datetimeUtil(phone.getUpdatedAt()));
            phones.setDeletedAt(datetimeUtil(phone.getDeletedAt()));

            phoneResponseBody.add(phones);
        });

        return phoneResponseBody;
    }

}
