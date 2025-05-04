package com.webflux.sample.repository;

import com.webflux.sample.document.ExampleDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ExampleRepository extends ReactiveMongoRepository<ExampleDocument, String> {
    Mono<ExampleDocument> findByProductAndActiveTrue(String productName);
}
