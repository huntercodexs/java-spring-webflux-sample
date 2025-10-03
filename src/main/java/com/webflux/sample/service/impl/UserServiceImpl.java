package com.webflux.sample.service.impl;

import com.webflux.sample.builder.UserBuilder;
import com.webflux.sample.document.UserDocument;
import com.webflux.sample.exception.NotFoundExceptionReactor;
import com.webflux.sample.repository.AddressRepository;
import com.webflux.sample.repository.PhoneRepository;
import com.webflux.sample.repository.UserRepository;
import com.webflux.sample.service.UserService;
import com.webflux.sample.user.model.*;
import com.webflux.sample.user_credentials_integration.api.UserCredentialsApiClient;
import com.webflux.sample.user_credentials_integration.model.UserCredentialCreateRequest;
import com.webflux.sample.user_credentials_integration.model.UserCredentialUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.webflux.sample.builder.UserBuilder.*;

@Log4j2
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private AddressRepository addressRepository;
    private PhoneRepository phoneRepository;

    private final UserCredentialsApiClient userCredentialsApiClient;

    @Override
    public Mono<UserCreatedResponseBody> create(Mono<UserRequestBody> createUserRequest) {
        return createUserRequest.flatMap(userRequest -> {
            UserDocument user = new UserDocument();
            user.setName(userRequest.getName());
            user.setEmail(userRequest.getEmail());

            return userRepository.save(user)
                    .doFirst(() -> log.info(">>> Save started..."))
                    .doOnTerminate(() -> log.info(">>> Save finished..."))
                    .doOnSuccess(success -> log.info("The Save result is {}", success))
                    .doOnError(error -> log.error("The Save error is {}", String.valueOf(error)))
                    .map(savedUser -> {
                        UserCreatedResponseBody response = new UserCreatedResponseBody();
                        response.setId(savedUser.getId());
                        return response;
                    });
        });
    }

    @Override
    public Mono<UserReadResponseBody> read(String userId) {
        return userRepository.findByIdAndActiveTrue(userId)
                .doFirst(() -> log.info("Find started..."))
                .doOnTerminate(() -> log.info("Find finished..."))
                .doOnSuccess(success -> log.info("The Find result is {}", success))
                .doOnError(error -> log.error("The Find error is {}", String.valueOf(error)))
                .flatMap(this::findAddress)
                .flatMap(this::findPhones)
                .flatMap(this::responseOne)
                .map(user -> user)
                .switchIfEmpty(Mono.error(new NotFoundExceptionReactor("User Not Found")));
    }

    @Override
    public Mono<UsersReadResponseBody> readAll() {
        return userRepository.findAllByActiveTrue()
                .doFirst(() -> log.info("findAll is started"))
                .doOnTerminate(() -> log.info("findAll is finished"))
                .doOnError(error -> log.error("The Find findAll is {}", String.valueOf(error)))
                .collectList()
                .doOnSuccess(success -> log.info("The findAll result is {}", success))
                .flatMap(this::responseAll);
    }

    @Override
    public Mono<UserReadResponseBody> update(String userId, Mono<UserRequestBody> updateUserRequest) {
        return updateUserRequest.flatMap(userRequest -> {
            return userRepository.findByIdAndActiveTrue(userId)
                    .doFirst(() -> log.info(">>> Update started..."))
                    .doOnTerminate(() -> log.info(">>> Update finished..."))
                    .doOnSuccess(success -> log.info("The Update result is {}", success))
                    .doOnError(error -> log.error("The Update error is {}", String.valueOf(error)))
                    .flatMap(userDocument -> {

                        userDocument.setName(userRequest.getName());
                        userDocument.setEmail(userRequest.getEmail());
                        userDocument.setUpdatedAt(LocalDateTime.now());

                        return userRepository.save(userDocument)
                                .doFirst(() -> log.info(">>> Save Update started..."))
                                .doOnTerminate(() -> log.info(">>> Save Update finished..."))
                                .doOnSuccess(success -> log.info("The Save Update result is {}", success))
                                .doOnError(error -> log.error("The Save Update error is {}", String.valueOf(error)))
                                .map(UserBuilder::buildUserResponse);

                    });
        });
    }

    @Override
    public Mono<GenericsResponseBody> delete(String userId) {
        return userRepository.findByIdAndActiveTrue(userId)
                .doFirst(() -> log.info(">>> Delete started..."))
                .doOnTerminate(() -> log.info(">>> Delete finished..."))
                .doOnSuccess(success -> log.info("The Delete result is {}", success))
                .doOnError(error -> log.error("The Delete error is {}", String.valueOf(error)))
                .flatMap(userDocument -> {

                    userDocument.setActive(false);
                    userDocument.setDeletedAt(LocalDateTime.now());

                    return userRepository.save(userDocument)
                            .doFirst(() -> log.info(">>> Save Delete started..."))
                            .doOnTerminate(() -> log.info(">>> Save Delete finished..."))
                            .doOnSuccess(success -> log.info("The Save Delete result is {}", success))
                            .doOnError(error -> log.error("The Save Delete error is {}", String.valueOf(error)))
                            .map(mapper -> buildGenericsResponse("User deleted successfully"));
                });
    }

    @Override
    public Mono<GenericsResponseBody> patch(String userId, Mono<UserPatchRequestBody> patchRequestBodyMono) {
        return patchRequestBodyMono.flatMap(patchRequest ->
                userRepository.findByIdAndActiveTrue(userId)
                        .doFirst(() -> log.info(">>> Patch started..."))
                        .doOnTerminate(() -> log.info(">>> Patch finished..."))
                        .doOnSuccess(success -> log.info("The Patch result is {}", success))
                        .doOnError(error -> log.error("The Patch error is {}", String.valueOf(error)))
                        .flatMap(userDocument ->
                                userRepository.save(buildToPatch(patchRequest, userDocument)))
                        .map(mapper -> buildGenericsResponse("User patched successfully")));
    }

    @Override
    public Mono<GenericsResponseBody> patchByPath(String userId, String fieldName, Object fieldValue) {
        return userRepository.findByIdAndActiveTrue(userId)
                .doFirst(() -> log.info(">>> patchByPath started..."))
                .doOnTerminate(() -> log.info(">>> patchByPath finished..."))
                .doOnSuccess(success -> log.info("The patchByPath result is {}", success))
                .doOnError(error -> log.error("The patchByPath error is {}", String.valueOf(error)))
                .flatMap(document ->
                        userRepository.save(buildAndPatchByPath(document, fieldName, fieldValue)))
                .map(mapper -> buildGenericsResponse("User patched by path successfully"));
    }

    private Mono<UserDocument> findAddress(UserDocument userDocument) {
        return addressRepository.findAllByUserIdAndActiveTrue(userDocument.getId())
                .doFirst(() -> log.info("findAddress is started"))
                .doOnTerminate(() -> log.info("findAddress is finished"))
                .doOnError(error -> log.error("The Find findAddress is {}", String.valueOf(error)))
                .collectList()
                .doOnSuccess(success -> log.info("The findAddress result is {}", success))
                .map(addressDocuments -> {

                    addressDocuments.forEach(addressDoc -> {
                        userDocument.getAddresses().add(addressDoc);
                    });

                    return userDocument;
                });
    }

    private Mono<UserDocument> findPhones(UserDocument userDocument) {
        return phoneRepository.findAllByUserIdAndActiveTrue(userDocument.getId())
                .doFirst(() -> log.info("findPhones is started"))
                .doOnTerminate(() -> log.info("findPhones is finished"))
                .doOnError(error -> log.error("The Find findPhones is {}", String.valueOf(error)))
                .collectList()
                .doOnSuccess(success -> log.info("The findPhones result is {}", success))
                .map(phonesDocuments -> {

                    phonesDocuments.forEach(phoneDoc -> {
                        userDocument.getPhones().add(phoneDoc);
                    });

                    return userDocument;
                });
    }

    private Mono<UsersReadResponseBody> responseAll(List<UserDocument> userDocumentList) {
        List<UserOnlyReadResponseBody> usersList = new ArrayList<>();
        userDocumentList.forEach(userDocument -> {
            usersList.add(buildUesrOnlyResponse(userDocument));
        });
        UsersReadResponseBody usersRead = new UsersReadResponseBody();
        usersRead.setUsers(usersList);
        return Mono.just(usersRead);
    }

    private Mono<UserReadResponseBody> responseOne(UserDocument userDocument) {
        return Mono.just(userDocument).map(UserBuilder::buildUserResponse);
    }

    private Mono<Void> userCredentialCreateIntegrationClient() {
        UserCredentialCreateRequest request = new UserCredentialCreateRequest();
        userCredentialsApiClient.createCredential(request);
        return null;
    }

    private Mono<Void> userCredentialGetIntegrationClient() {
        UserCredentialCreateRequest request = new UserCredentialCreateRequest();
        userCredentialsApiClient.getCredential("12345");
        return null;
    }

    private Mono<Void> userCredentialUpdateIntegrationClient() {
        UserCredentialUpdateRequest request = new UserCredentialUpdateRequest();
        userCredentialsApiClient.putCredential("12345", request);
        return null;
    }

    private Mono<Void> userCredentialPatchIntegrationClient() {
        UserCredentialUpdateRequest request = new UserCredentialUpdateRequest();
        userCredentialsApiClient.patchCredential("12345", request);
        return null;
    }

    private Mono<Void> userCredentialDeleteIntegrationClient() {
        userCredentialsApiClient.removeCredential("12345");
        return null;
    }

}
