package com.webflux.sample.repository;

import com.webflux.sample.document.AddressDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface AddressRepository extends ReactiveMongoRepository<AddressDocument, String> {
    Flux<AddressDocument> findAllById(String id);
    Flux<AddressDocument> findAllByUserIdAndActiveTrue(String id);
}
