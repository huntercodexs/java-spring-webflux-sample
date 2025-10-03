package com.webflux.sample.repository.custom.impl;

import com.webflux.sample.config.mongo.CurrentPage;
import com.webflux.sample.document.UserDocument;
import com.webflux.sample.document.search.UserSearch;
import com.webflux.sample.repository.custom.UserPurchaseRepositoryCustom;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
@AllArgsConstructor
public class UserPurchaseRepositoryCustomImpl implements UserPurchaseRepositoryCustom {

    private final static String PURCHASES_SUMMARY_ALIAS = "purchasesSummary";
    private final static String PURCHASE_ID_SUMMARY_ALIAS = "purchasesSummary.purchaseId";
    private final static String USER_COLLECTION = "User";
    private final static String USER_PURCHASE_COLLECTION = "UserPurchase";
    private final static String PURCHASE_ID_REF = "purchases.$id";
    private final static String ID_FIELD = "_id";
    private final static String ACTIVE_FIELD = "active";
    private final static String EMAIL_FIELD = "email";

    private final MongoTemplate mongoTemplate;

    @Override
    public Page<UserDocument> findUsersWithPurchases(UserSearch userSearch) {

        Criteria criteria = new Criteria();
        criteria.andOperator(criteriaListForUser(userSearch));
        MatchOperation matchForUser = Aggregation.match(criteria);

        LookupOperation lookup = Aggregation.lookup(USER_PURCHASE_COLLECTION, PURCHASE_ID_REF, ID_FIELD, PURCHASES_SUMMARY_ALIAS);

        MatchOperation matchForUserPurchases = Aggregation.match(criteriaForUserPurchases(criteria, userSearch));
        SkipOperation skip = Aggregation.skip((long) userSearch.getOffset());
        LimitOperation limit = Aggregation.limit(userSearch.getLimit());
        Aggregation aggregation = Aggregation.newAggregation(matchForUser, lookup, matchForUserPurchases, skip, limit);

        List<UserDocument> users = mongoTemplate.aggregate(aggregation, USER_COLLECTION, UserDocument.class).getMappedResults();

        Aggregation countAggregation = Aggregation.newAggregation(matchForUser, matchForUserPurchases, Aggregation.count().as("total"));

        AggregationResults<Document> countResult = mongoTemplate.aggregate(countAggregation, USER_COLLECTION, Document.class);

        long total = Optional.ofNullable(countResult.getUniqueMappedResult())
                .map(d -> d.getInteger("total")).orElse(0);

        return new CurrentPage<>(users, userSearch.getLimit(), userSearch.getOffset(), total);
    }

    private Criteria[] criteriaListForUser(UserSearch userSearch) {

        List<Criteria> criteriaList = new ArrayList<>();

        criteriaList.add(where(ACTIVE_FIELD).is(true));

        // TODO: Write filters here

        if (nonNull(userSearch.getEmail())) {
            criteriaList.add(where(EMAIL_FIELD).is(userSearch.getEmail()));
        }

        criteriaList.add(where(PURCHASE_ID_REF).in(userSearch.getPurchaseIdList().stream().map(ObjectId::new).toList()));

        return criteriaList.toArray(new Criteria[0]);
    }

    private Criteria criteriaForUserPurchases(Criteria criteria, UserSearch userSearch) {

        boolean newCriteria = false;

        // TODO: Write filters here

        if (nonNull(userSearch.getPurchaseId())) {
            criteria = new Criteria();
            criteria.and(PURCHASE_ID_SUMMARY_ALIAS).is(userSearch.getPurchaseId());
            newCriteria = true;
        }

        if (nonNull(userSearch.getPurchaseValue())) {
            if (!newCriteria) criteria = new Criteria();
            criteria.and(PURCHASE_ID_SUMMARY_ALIAS).is(userSearch.getPurchaseId());
        }

        return criteria;

    }

}