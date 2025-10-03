package com.webflux.sample.service.impl;

import com.webflux.sample.document.PhoneDocument;
import com.webflux.sample.repository.PhoneRepository;
import com.webflux.sample.service.PhoneService;
import com.webflux.sample.user_details.model.PhoneCreatedResponseBody;
import com.webflux.sample.user_details.model.PhoneReadResponseBody;
import com.webflux.sample.user_details.model.PhoneRequestBody;
import com.webflux.sample.user_details.model.PhonesItemsResponseBody;
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
public class PhoneServiceImpl implements PhoneService {

    private PhoneRepository phoneRepository;

    @Override
    public Mono<PhoneCreatedResponseBody> create(String userId, Mono<PhoneRequestBody> createPhoneRequest) {
        return createPhoneRequest.flatMap(phoneRequest -> {
            PhoneDocument address = new PhoneDocument();
            address.setUserId(userId);
            address.setPhoneNumber(phoneRequest.getPhoneNumber());
            address.setPhoneType(phoneRequest.getPhoneType().getValue());

            return phoneRepository.save(address)
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
    public Mono<PhoneReadResponseBody> find(String userId) {
        return phoneRepository.findAllByUserIdAndActiveTrue(userId)
                .doFirst(() -> log.info(">>> Find started..."))
                .doOnTerminate(() -> log.info(">>> Find finished..."))
                .collectList()
                .doOnSuccess(success -> log.info("The Find result is {}", success))
                .doOnError(error -> log.error("The Find error is {}", String.valueOf(error)))
                .flatMap(this::buildResponse)
                .map(phones -> phones);
    }

    private Mono<PhoneReadResponseBody> buildResponse(List<PhoneDocument> phoneDocumentList) {
        List<PhonesItemsResponseBody> addressList = new ArrayList<>();
        phoneDocumentList.forEach(addressDocument -> {
            addressList.add(this.buildPhonesItemsResponse(addressDocument));
        });
        PhoneReadResponseBody phonesRead = new PhoneReadResponseBody();
        phonesRead.setPhones(addressList);
        return Mono.just(phonesRead);
    }

    private PhonesItemsResponseBody buildPhonesItemsResponse(PhoneDocument document) {
        PhonesItemsResponseBody response = new PhonesItemsResponseBody();
        response.setPhoneNumber(document.getPhoneNumber());
        response.setPhoneType(document.getPhoneType());
        response.setActive(document.isActive());
        response.setCreatedAt(datetimeUtil(document.getCreatedAt()));
        response.setUpdatedAt(datetimeUtil(document.getUpdatedAt()));
        response.setDeletedAt(datetimeUtil(document.getDeletedAt()));
        return response;
    }

}
