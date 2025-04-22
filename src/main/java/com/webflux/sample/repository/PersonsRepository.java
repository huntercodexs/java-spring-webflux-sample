package com.webflux.sample.repository;

import com.webflux.sample.document.PersonsDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface PersonsRepository extends ReactiveMongoRepository<PersonsDocument, String> {
    Mono<PersonsDocument> findByIdAndActiveTrue(String personId);
    Flux<PersonsDocument> findAllByActiveTrue();
}
