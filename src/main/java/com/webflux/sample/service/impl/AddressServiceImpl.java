package com.webflux.sample.service.impl;

import com.webflux.sample.document.AddressDocument;
import com.webflux.sample.repository.AddressRepository;
import com.webflux.sample.service.AddressService;
import com.webflux.sample.user_details.model.AddressCreatedResponseBody;
import com.webflux.sample.user_details.model.AddressItemsResponseBody;
import com.webflux.sample.user_details.model.AddressReadResponseBody;
import com.webflux.sample.user_details.model.AddressRequestBody;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static com.webflux.sample.util.WebFluxUtil.datetimeUtil;

@Log4j2
@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {

    private AddressRepository addressRepository;

    @Override
    public Mono<AddressCreatedResponseBody> create(String userId, Mono<AddressRequestBody> createAddressRequest) {
        return createAddressRequest.flatMap(addressRequest -> {
            AddressDocument address = new AddressDocument();
            address.setUserId(userId);
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
    public Mono<AddressReadResponseBody> find(String userId) {
        return addressRepository.findAllByUserIdAndActiveTrue(userId)
                .doFirst(() -> log.info(">>> Find started..."))
                .doOnTerminate(() -> log.info(">>> Find finished..."))
                .collectList()
                .doOnSuccess(success -> log.info("The Find result is {}", success))
                .doOnError(error -> log.error( "The Find error is {}", String.valueOf(error)))
                .flatMap(this::buildResponse)
                .map(address -> address);
    }

    private Mono<AddressReadResponseBody> buildResponse(List<AddressDocument> addressDocumentList) {
        List<AddressItemsResponseBody> addressList = new ArrayList<>();
        addressDocumentList.forEach(addressDocument -> {
            addressList.add(this.buildAddressItemsResponse(addressDocument));
        });
        AddressReadResponseBody addressRead = new AddressReadResponseBody();
        addressRead.setAddresses(addressList);
        return Mono.just(addressRead);
    }

    private AddressItemsResponseBody buildAddressItemsResponse(AddressDocument document) {
        AddressItemsResponseBody response = new AddressItemsResponseBody();
        response.setStreet(document.getStreet());
        response.setNumber(document.getNumber());
        response.setCity(document.getCity());
        response.setZipcode(document.getZipcode());
        response.setActive(document.isActive());
        response.setCreatedAt(datetimeUtil(document.getCreatedAt()));
        response.setUpdatedAt(datetimeUtil(document.getUpdatedAt()));
        response.setDeletedAt(datetimeUtil(document.getDeletedAt()));
        return response;
    }

}
