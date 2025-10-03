package com.webflux.sample.config.feign;

import feign.Logger;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class FeignClientConfig {

    @Value("${huntercodexs.openfeign.client.config.retryer.period:1000}")
    private long period;

    @Value("${huntercodexs.openfeign.client.config.retryer.max-period:1_000}")
    private long maxPeriod;

    @Value("${huntercodexs.openfeign.client.config.retryer.max-attempts:3}")
    private int maxAttempts;

    @Bean
    @Primary
    public ErrorDecoder defaultErrorDecoder() {
        return new CustomErrorDecoder();
    }

    @Bean
    @Primary
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    @Primary
    public Retryer retryer() {
        return new Retryer.Default(this.period, this.maxPeriod, this.maxAttempts);
    }

}
