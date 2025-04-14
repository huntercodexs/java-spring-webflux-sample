package com.webflux.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.webflux.sample")
@ConfigurationPropertiesScan("com.webflux.sample")
@EnableConfigurationProperties
public class WebFluxSampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebFluxSampleApplication.class, args);
    }
}
