package com.webflux.sample.repository.custom;

import com.webflux.sample.document.UserDocument;
import com.webflux.sample.document.search.UserSearch;
import org.springframework.data.domain.Page;
import reactor.core.publisher.Mono;

public interface UserPurchaseRepositoryCustom {

    Mono<Page<UserDocument>> findUsersWithPurchases(UserSearch userSearch);

}
