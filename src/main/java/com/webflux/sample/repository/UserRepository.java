package com.webflux.sample.repository;

import com.webflux.sample.document.UsersDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<UsersDocument, String> {
    Mono<UsersDocument> findByUsername(String username);
}
