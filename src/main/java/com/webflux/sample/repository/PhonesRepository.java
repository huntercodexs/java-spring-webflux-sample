package com.webflux.sample.repository;

import com.webflux.sample.document.PhonesDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface PhonesRepository extends ReactiveMongoRepository<PhonesDocument, String> {
    Flux<PhonesDocument> findAllById(String id);
}
