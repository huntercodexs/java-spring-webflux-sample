package com.webflux.sample.dto;

import com.webflux.sample.document.AddressDocument;
import com.webflux.sample.document.PhonesDocument;
import com.webflux.sample.model.PersonRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

import java.util.List;

@Log4j2
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PersonDto {

    private String id;
    private String name;
    private String email;

    public PersonDto(PersonRequest request) {
        this.name = request.getName();
        this.email = request.getEmail();
    }

    public Mono<PersonDto> withDetails(
            List<AddressDocument> addressDocumentList,
            List<PhonesDocument> phonesDocumentList
    ) {
        return Mono
                .just(this)
                .doFirst(() -> log.info(">>> withDetails started"))
                .doOnTerminate(() -> log.info(">>> withDetails finished"))
                .doOnSuccess(result -> log.info(">>> withDetails result is {}", result))
                .doOnError(error -> log.error(">>> withDetails Something went wrong {}", String.valueOf(error)));
    }
}
