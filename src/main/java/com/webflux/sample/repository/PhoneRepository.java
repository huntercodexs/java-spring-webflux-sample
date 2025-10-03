package com.webflux.sample.repository;

import com.webflux.sample.document.PhoneDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface PhoneRepository extends ReactiveMongoRepository<PhoneDocument, String> {
    Flux<PhoneDocument> findAllById(String id);
    Flux<PhoneDocument> findAllByUserIdAndActiveTrue(String id);
}
