package com.webflux.sample.repository;

import com.webflux.sample.document.PersonsDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonsRepository extends ReactiveMongoRepository<PersonsDocument, String> {
}
