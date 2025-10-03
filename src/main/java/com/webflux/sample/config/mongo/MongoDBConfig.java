package com.webflux.sample.config.mongo;

import com.webflux.sample.config.mongo.converter.DateToOffSetDateTimeConverter;
import com.webflux.sample.config.mongo.converter.OffSetDateTimeToDateConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.List;

@Configuration
public class MongoDBConfig {

    @Bean
    @Primary
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(List.of(
                new DateToOffSetDateTimeConverter(),
                new OffSetDateTimeToDateConverter()
        ));
    }

}
