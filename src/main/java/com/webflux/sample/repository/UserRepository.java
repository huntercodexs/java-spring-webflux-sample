package com.webflux.sample.repository;

import com.webflux.sample.document.UserDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveMongoRepository<UserDocument, String> {
    Mono<UserDocument> findByIdAndActiveTrue(String userId);
    Flux<UserDocument> findAllByActiveTrue();
}
