package com.webflux.sample.config.security.user;

import com.webflux.sample.exception.NotFoundExceptionReactor;
import com.webflux.sample.exception.UnAuthorizedExceptionReactor;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@Component
@AllArgsConstructor
public class CustomUserDetailsService implements ReactiveUserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByUsername(username)
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

    public Mono<Void> create(Mono<UserRequest> userRequest) {
        return userRequest.flatMap(request -> {

            UsersDocument usersDocument = new UsersDocument();
            usersDocument.setUsername(request.getUsername());
            usersDocument.setPassword(passwordEncoder.encode(request.getPassword()));

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
