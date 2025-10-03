package com.webflux.sample.repository.custom;

import com.webflux.sample.document.UserDocument;
import com.webflux.sample.document.search.UserSearch;
import org.springframework.data.domain.Page;

public interface UserPurchaseRepositoryCustom {

    Page<UserDocument> findUsersWithPurchases(UserSearch userSearch);

}
