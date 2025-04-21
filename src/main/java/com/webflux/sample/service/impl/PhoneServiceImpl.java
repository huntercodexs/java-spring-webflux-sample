package com.webflux.sample.service.impl;

import com.webflux.sample.document.PhonesDocument;
import com.webflux.sample.model.PhoneCreatedResponseBody;
import com.webflux.sample.model.PhoneReadResponseBody;
import com.webflux.sample.model.PhoneRequestBody;
import com.webflux.sample.repository.PhonesRepository;
import com.webflux.sample.service.PhoneService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Log4j2
@Service
@AllArgsConstructor
public class PhoneServiceImpl implements PhoneService {

    private PhonesRepository phonesRepository;

    @Override
    public Mono<PhoneCreatedResponseBody> create(String personId, Mono<PhoneRequestBody> createPhoneRequest) {
        return createPhoneRequest.flatMap(phoneRequest -> {
            PhonesDocument address = new PhonesDocument();
            address.setPersonId(personId);
            address.setPhoneNumber(phoneRequest.getPhoneNumber());
            address.setPhoneType(phoneRequest.getPhoneType().getValue());

            return phonesRepository.save(address)
                    .doFirst(() -> log.info(">>> Save started..."))
                    .doOnTerminate(() -> log.info(">>> Save finished..."))
                    .doOnSuccess(success -> log.info("The Save result is {}", success))
                    .doOnError(error -> log.error("The Save error is {}", String.valueOf(error)))
                    .map(savedPhone -> {
                        PhoneCreatedResponseBody response = new PhoneCreatedResponseBody();
                        response.setId(savedPhone.getId());
                        return response;
                    });
        });
    }

    @Override
    public Mono<PhoneReadResponseBody> find(String id) {
        return phonesRepository.findById(id)
                .doFirst(() -> log.info(">>> Find started..."))
                .doOnTerminate(() -> log.info(">>> Find finished..."))
                .doOnSuccess(success -> log.info("The Find result is {}", success))
                .doOnError(error -> log.error( "The Find error is {}", String.valueOf(error)))
                .flatMap(this::buildResponse)
                .map(address -> address);
    }

    private Mono<PhoneReadResponseBody> buildResponse(PhonesDocument phonesDocument) {
        return Mono.just(phonesDocument).map(document -> {
            PhoneReadResponseBody response = new PhoneReadResponseBody();
            response.setPhoneNumber(document.getPhoneNumber());
            response.setPhoneTYpe(document.getPhoneNumber());
            return response;
        });
    }

}
