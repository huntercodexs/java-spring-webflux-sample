package com.webflux.sample.repository;

import com.webflux.sample.document.LoginDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface LoginRepository extends ReactiveMongoRepository<LoginDocument, String> {
    Mono<LoginDocument> findByUsername(String username);
}
