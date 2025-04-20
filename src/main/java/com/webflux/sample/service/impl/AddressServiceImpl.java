package com.webflux.sample.service.impl;

import com.webflux.sample.document.AddressDocument;
import com.webflux.sample.model.AddressCreatedResponseBody;
import com.webflux.sample.model.AddressReadResponseBody;
import com.webflux.sample.model.AddressRequestBody;
import com.webflux.sample.repository.AddressRepository;
import com.webflux.sample.service.AddressService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {

    private AddressRepository addressRepository;

    @Override
    public Mono<AddressCreatedResponseBody> create(Mono<AddressRequestBody> createAddressRequest) {
        return createAddressRequest.flatMap(addressRequest -> {
            AddressDocument address = new AddressDocument();
            address.setId(addressRequest.getPersonId());
            address.setStreet(addressRequest.getStreet());
            address.setNumber(addressRequest.getNumber());
            address.setCity(addressRequest.getCity());
            address.setZipcode(addressRequest.getZipcode());

            return addressRepository.save(address)
                    .doFirst(() -> log.info(">>> Save started..."))
                    .doOnTerminate(() -> log.info(">>> Save finished..."))
                    .doOnSuccess(success -> log.info("The Save result is {}", success))
                    .doOnError(error -> log.error("The Save error is {}", String.valueOf(error)))
                    .map(savedAddress -> {
                        AddressCreatedResponseBody response = new AddressCreatedResponseBody();
                        response.setId(savedAddress.getId());
                        return response;
                    });
        });
    }

    @Override
    public Mono<AddressReadResponseBody> find(String id) {
        return addressRepository.findById(id)
                .doFirst(() -> log.info(">>> Find started..."))
                .doOnTerminate(() -> log.info(">>> Find finished..."))
                .doOnSuccess(success -> log.info("The Find result is {}", success))
                .doOnError(error -> log.error( "The Find error is {}", String.valueOf(error)))
                .flatMap(this::buildResponse)
                .map(address -> address);
    }

    @Override
    public Flux<AddressReadResponseBody> findAll() {
        return null;
    }

    private Mono<AddressReadResponseBody> buildResponse(AddressDocument addressDocument) {
        return Mono.just(addressDocument).map(document -> {
            AddressReadResponseBody response = new AddressReadResponseBody();
            response.setPersonId(document.getId());
            response.setStreet(document.getStreet());
            response.setNumber(document.getNumber());
            response.setCity(document.getCity());
            response.setZipcode(document.getZipcode());
            return response;
        });
    }

}
