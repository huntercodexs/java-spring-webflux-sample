package com.webflux.sample.config.security;

import com.webflux.sample.exception.NotFoundExceptionReactor;
import com.webflux.sample.repository.LoginRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Log4j2
@Component
@AllArgsConstructor
public class CustomUserDetailsService implements ReactiveUserDetailsService {

    private final LoginRepository loginRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return loginRepository.findByUsername(username)
                .doFirst(() -> log.info("Searching username login {}", username))
                .doOnSuccess(success -> log.info("Everything was fine"))
                .doOnError(error -> log.error("Something went wrong: {}", String.valueOf(error)))
                .doOnTerminate(() -> log.info("Searching username is terminating"))
                .map(user -> {

                    var authorities = user.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role)) // Prefix with "ROLE_"
                            .collect(Collectors.toList());

                    UserDetails userDetails = User.builder()
                            .username(user.getUsername())
                            //.password("{noop}" + user.getPassword())
                            //.password("{pbkdf2}" + user.getPassword())
                            .password("{bcrypt}" + user.getPassword())
                            .authorities(authorities)
                            .build();

                    log.info("UserDetails: {}", userDetails);
                    return userDetails;

                })
                .switchIfEmpty(Mono.error(new NotFoundExceptionReactor("Invalid Login")));
    }

}
