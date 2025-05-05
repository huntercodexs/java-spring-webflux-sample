package com.webflux.sample.config.security.user;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<UsersDocument, String> {
    Mono<UsersDocument> findByUsername(String username);
}
