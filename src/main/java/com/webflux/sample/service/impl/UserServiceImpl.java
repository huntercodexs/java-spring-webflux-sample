package com.webflux.sample.service.impl;

import com.webflux.sample.document.UsersDocument;
import com.webflux.sample.dto.UserRequest;
import com.webflux.sample.exception.UnAuthorizedExceptionReactor;
import com.webflux.sample.repository.UserRepository;
import com.webflux.sample.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

@Log4j2
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<Void> create(Mono<UserRequest> userRequest) {
        return userRequest.flatMap(request -> {

            UsersDocument usersDocument = new UsersDocument();
            usersDocument.setUsername(request.getUsername());
            usersDocument.setPassword(passwordEncoder.encode(request.getPassword()));

            //TODO: Create roles dynamically
            Set<String> roles = new HashSet<>();
            roles.add("USER");
            roles.add("ADMIN");
            usersDocument.setRoles(roles);

            usersDocument.setActive(true);

            return userRepository.save(usersDocument)
                    .doOnSuccess(s -> log.info("OK {}", s))
                    .doOnError(e -> log.error("NOK {}", String.valueOf(e)))
                    .map(userSave -> {
                        System.out.println(userSave);
                        return userSave;
                    });
        }).then();
    }

    @Override
    public Mono<String> search(UserRequest userRequest) {
        return userRepository.findByUsername(userRequest.getUsername())
                .flatMap(resulted -> {
                    if (passwordEncoder.matches(userRequest.getPassword(), resulted.getPassword())) {
                        return Mono.just("Login successfully");
                    }
                    throw new UnAuthorizedExceptionReactor("Invalid Login");
                })
                .switchIfEmpty(Mono.error(new UnAuthorizedExceptionReactor("User Not Found")));
    }

}
