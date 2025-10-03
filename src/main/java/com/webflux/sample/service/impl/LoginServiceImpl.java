package com.webflux.sample.service.impl;

import com.webflux.sample.document.LoginDocument;
import com.webflux.sample.dto.LoginRequest;
import com.webflux.sample.exception.UnAuthorizedExceptionReactor;
import com.webflux.sample.repository.LoginRepository;
import com.webflux.sample.service.LoginService;
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
public class LoginServiceImpl implements LoginService {

    private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<Void> create(Mono<LoginRequest> userRequest) {
        return userRequest.flatMap(request -> {

            LoginDocument loginDocument = new LoginDocument();
            loginDocument.setUsername(request.getUsername());
            loginDocument.setPassword(passwordEncoder.encode(request.getPassword()));

            //TODO: Create roles dynamically
            Set<String> roles = new HashSet<>();
            roles.add("USER");
            roles.add("ADMIN");
            loginDocument.setRoles(roles);

            loginDocument.setActive(true);

            return loginRepository.save(loginDocument)
                    .doOnSuccess(s -> log.info("OK {}", s))
                    .doOnError(e -> log.error("NOK {}", String.valueOf(e)))
                    .map(userSave -> {
                        System.out.println(userSave);
                        return userSave;
                    });
        }).then();
    }

    @Override
    public Mono<String> search(LoginRequest loginRequest) {
        return loginRepository.findByUsername(loginRequest.getUsername())
                .flatMap(resulted -> {
                    if (passwordEncoder.matches(loginRequest.getPassword(), resulted.getPassword())) {
                        return Mono.just("Login successfully");
                    }
                    throw new UnAuthorizedExceptionReactor("Invalid Login");
                })
                .switchIfEmpty(Mono.error(new UnAuthorizedExceptionReactor("User Not Found")));
    }

}
