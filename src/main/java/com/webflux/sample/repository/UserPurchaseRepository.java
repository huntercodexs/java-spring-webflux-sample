package com.webflux.sample.repository;

import com.webflux.sample.document.UserPurchaseDocument;
import com.webflux.sample.repository.custom.UserPurchaseRepositoryCustom;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserPurchaseRepository extends MongoRepository<UserPurchaseDocument, String>, UserPurchaseRepositoryCustom {
}
