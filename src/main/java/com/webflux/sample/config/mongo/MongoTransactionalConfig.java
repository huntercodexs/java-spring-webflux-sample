package com.webflux.sample.config.mongo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.ReactiveMongoTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.reactive.TransactionalOperator;

@Configuration
@EnableTransactionManagement
public class MongoTransactionalConfig {

    @Bean
    public ReactiveMongoTransactionManager transactionManager(ReactiveMongoDatabaseFactory rdbf) {
        return new ReactiveMongoTransactionManager(rdbf);
    }

    @Bean
    public TransactionalOperator transactionOperator(ReactiveTransactionManager rtm) {
        return TransactionalOperator.create(rtm);
    }
}
