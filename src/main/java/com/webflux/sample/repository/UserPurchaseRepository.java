package com.webflux.sample.repository;

import com.webflux.sample.document.UserPurchaseDocument;
import com.webflux.sample.repository.custom.UserPurchaseRepositoryCustom;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserPurchaseRepository extends ReactiveMongoRepository<UserPurchaseDocument, String>, UserPurchaseRepositoryCustom {
}
