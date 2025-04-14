package com.webflux.sample.service.impl;

import com.webflux.sample.document.AddressDocument;
import com.webflux.sample.document.PhonesDocument;
import com.webflux.sample.dto.PersonDto;
import com.webflux.sample.model.PersonCreatedResponse;
import com.webflux.sample.model.PersonRequest;
import com.webflux.sample.repository.AddressRepository;
import com.webflux.sample.repository.PersonsRepository;
import com.webflux.sample.repository.PhonesRepository;
import com.webflux.sample.service.PersonService;
import com.webflux.sample.service.SecurityService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.List;

@Log4j2
@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {

    private SecurityService securityService;
    private PersonsRepository personsRepository;
    private AddressRepository addressRepository;
    private PhonesRepository phonesRepository;

    @Override
    public Mono<PersonCreatedResponse> create(Mono<PersonRequest> createPersonRequest) {
        return Mono.defer(() -> securityService.fakeAuthentication())
                .doFirst(() -> log.info(">>> Person service create started"))
                .doOnTerminate(() -> log.info(">>> Person service create finished"))
                .map(PersonDto::new)
                .flatMap(this::zipPerson)
                .flatMap(this::buildResponse)
                .doOnSuccess(result -> log.info(">>> Everything was fine {}", result))
                .doOnError(error -> log.error(">>> Something went wrong in create {}", String.valueOf(error)))
                .delaySubscription(Duration.ofSeconds(1));
    }

    private Mono<PersonDto> zipPerson(PersonDto personDto) {
        return Mono.zip(
                        /*T1*/
                        findPersonAddress(personDto).subscribeOn(Schedulers.parallel()),
                        /*T2*/
                        findPersonPhone(personDto).subscribeOn(Schedulers.parallel())
                )
                .doFirst(() -> log.info(">>> Zip Person started"))
                .doOnTerminate(() -> log.info(">>> Zip Person finished"))
                .flatMap(tuple -> personDto.withDetails(tuple.getT1(), tuple.getT2()))
                .doOnSuccess(result -> log.info(">>> Everything is fine {}", result))
                .doOnError(error -> log.error(">>> Something went wrong in zipPerson {}", String.valueOf(error)));
    }

    private Mono<List<AddressDocument>> findPersonAddress(PersonDto personDto) {
        return addressRepository.findAllById(personDto.getId())
                .doFirst(() -> log.info(">>> findPersonAddress started"))
                .doOnTerminate(() -> log.info(">>> findPersonAddress finished"))
                .collectList()
                .doOnSuccess(response -> log.info(">>> Finish method findPersonAddress with response {}", response));
    }

    private Mono<List<PhonesDocument>> findPersonPhone(PersonDto personDto) {
        return phonesRepository.findAllById(personDto.getId())
                .doFirst(() -> log.info(">>> findPersonPhone started"))
                .doOnTerminate(() -> log.info(">>> findPersonPhone finished"))
                .collectList()
                .doOnSuccess(response -> log.info(">>> Finish method findPersonPhone with response {}", response));
    }

    private Mono<PersonCreatedResponse> buildResponse(PersonDto personDto) {
        return Mono.just(new PersonCreatedResponse())
                .doFirst(() -> log.info(">>> buildResponse started"))
                .doOnTerminate(() -> log.info(">>> buildResponse finished"));
    }

}
