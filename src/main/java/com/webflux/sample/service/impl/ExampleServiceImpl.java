package com.webflux.sample.service.impl;

import com.webflux.sample.document.ExampleDocument;
import com.webflux.sample.dto.ExampleRequest;
import com.webflux.sample.exception.ConflictExceptionReactor;
import com.webflux.sample.repository.ExampleRepository;
import com.webflux.sample.service.ExampleService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static java.util.Objects.isNull;

@Log4j2
@Service
@AllArgsConstructor
public class ExampleServiceImpl implements ExampleService {

    private ExampleRepository exampleRepository;

    @Override
    public Mono<ExampleDocument> execute(Mono<ExampleRequest> exampleRequest) {
        return exampleRequest
                .doFirst(() -> log.info("Example Some Method is running"))
                .flatMap(this::exists)
                .flatMap(this::checkProductName)
                .flatMap(this::checkProductDescription)
                .flatMap(request -> Mono.just(this.requestToDocument(request)))
                .flatMap(exampleRepository::save);
    }

    /*Just a Simple and Sample Method - Repository Simulation*/
    public Mono<ExampleRequest> exists(ExampleRequest exampleRequest) {
        if (!isNull(this.exampleRepository.findByProductAndActiveTrue(exampleRequest.getProduct()))) {
            throw new ConflictExceptionReactor("Product already exists");
        }
        return Mono.just(exampleRequest);
    }

    /*Just a Simple and Sample Method*/
    private Mono<ExampleRequest> checkProductName(ExampleRequest exampleRequest) {
        if (exampleRequest.getProduct().isEmpty()) return null;
        return Mono.just(exampleRequest);
    }

    /*Just a Simple and Sample Method*/
    private Mono<ExampleRequest> checkProductDescription(ExampleRequest exampleRequest) {
        if (exampleRequest.getDescription().isEmpty()) return null;
        return Mono.just(exampleRequest);
    }

    /*Just a Simple and Sample Method*/
    private ExampleDocument requestToDocument(ExampleRequest exampleRequest) {
        ExampleDocument document = new ExampleDocument();
        document.setProduct(exampleRequest.getProduct());
        document.setDescription(exampleRequest.getDescription());
        document.setActive(true);
        return document;
    }

}
