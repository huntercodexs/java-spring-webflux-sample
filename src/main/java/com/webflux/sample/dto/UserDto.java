package com.webflux.sample.dto;

import com.webflux.sample.document.AddressDocument;
import com.webflux.sample.document.UserDocument;
import com.webflux.sample.document.PhoneDocument;
import com.webflux.sample.user.model.UserRequestBody;
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
public class UserDto {

    private String id;
    private String name;
    private String email;

    public UserDto(UserRequestBody request) {
        this.name = request.getName();
        this.email = request.getEmail();
    }

    public UserDto(UserDocument userDocument) {
    }

    public Mono<UserDto> withDetails(
            List<AddressDocument> addressDocumentList,
            List<PhoneDocument> phoneDocumentList
    ) {
        return Mono
                .just(this)
                .doFirst(() -> log.info(">>> withDetails started"))
                .doOnTerminate(() -> log.info(">>> withDetails finished"))
                .doOnSuccess(result -> log.info(">>> withDetails result is {}", result))
                .doOnError(error -> log.error(">>> withDetails Something went wrong {}", String.valueOf(error)));
    }
}
