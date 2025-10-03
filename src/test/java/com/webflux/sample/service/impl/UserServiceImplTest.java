package com.webflux.sample.service.impl;

import com.webflux.sample.document.UserDocument;
import com.webflux.sample.repository.AddressRepository;
import com.webflux.sample.repository.PhoneRepository;
import com.webflux.sample.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.blockhound.BlockHound;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.webflux.sample.DataBuilder.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@DisplayName("Test class UserServiceImplTest")
@ExtendWith({SpringExtension.class, MockitoExtension.class})
class UserServiceImplTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AddressRepository addressRepository;

    @MockBean
    private PhoneRepository phoneRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void init() {
        BlockHound.install();
        openMocks(this);

        setField(userService, "userRepository", userRepository);
        setField(userService, "addressRepository", addressRepository);
        setField(userService, "phoneRepository", phoneRepository);
    }

    @Test
    @DisplayName("SERVICE - Should Create User successfully")
    void shouldCreateUserSuccessfully() {
        var saveResult = buildUserDocumentForTests();
        when(userRepository.save(any(UserDocument.class))).thenReturn(Mono.just(saveResult));

        var request = buildUserRequestBodyForTests();
        var result = userService.create(Mono.just(request));

        StepVerifier.create(result)
                .expectNextMatches(created -> created.getId().equals(USER_ID))
                .verifyComplete();
    }

    @Test
    @DisplayName("SERVICE - Should Read One User successfully")
    void shouldReadOneUserSuccessfully() {
        var addressDocument = buildAddressDocumentForTests();
        var phonesDocument = buildPhonesDocumentForTests();
        var userDocument = buildUserDocumentForTests();
        when(addressRepository.findAllByUserIdAndActiveTrue(anyString())).thenReturn(Flux.just(addressDocument));
        when(phoneRepository.findAllByUserIdAndActiveTrue(anyString())).thenReturn(Flux.just(phonesDocument));
        when(userRepository.findByIdAndActiveTrue(anyString())).thenReturn(Mono.just(userDocument));

        var result = userService.read(USER_ID);

        StepVerifier.create(result)
                .expectNextMatches(find ->
                        (find.getId().equals(USER_ID) && find.getName().equals(USER_NAME)))
                .verifyComplete();
    }

    @Test
    @DisplayName("SERVICE - Should Read All Users successfully")
    void shouldReadAllUsersSuccessfully() {
        var userDocument = buildUserDocumentForTests();
        when(userRepository.findAllByActiveTrue()).thenReturn(Flux.just(userDocument));

        var result = userService.readAll();

        StepVerifier.create(result)
                .expectNextMatches(find -> (
                        find.getUsers().getFirst().getId().equals(USER_ID)) && (
                                find.getUsers().getFirst().getName().equals(USER_NAME))
                )
                .verifyComplete();
    }

    @Test
    @DisplayName("SERVICE - Should Update User successfully")
    void shouldUpdateUserSuccessfully() {
        var userDocument = buildUserDocumentForTests();
        when(userRepository.findByIdAndActiveTrue(anyString())).thenReturn(Mono.just(userDocument));
        when(userRepository.save(any(UserDocument.class))).thenReturn(Mono.just(userDocument));

        var request = buildUserRequestBodyForTests();
        var result = userService.update(USER_ID, Mono.just(request));

        StepVerifier.create(result)
                .expectNextMatches(created -> created.getId().equals(USER_ID))
                .verifyComplete();
    }

    @Test
    @DisplayName("SERVICE - Should Delete User successfully")
    void shouldDeleteUserSuccessfully() {
        var userDocument = buildUserDocumentForTests();
        when(userRepository.findByIdAndActiveTrue(anyString())).thenReturn(Mono.just(userDocument));
        when(userRepository.save(any(UserDocument.class))).thenReturn(Mono.just(userDocument));

        var result = userService.delete(USER_ID);

        StepVerifier.create(result)
                .expectNextMatches(created -> created.getMessage().equals("User deleted successfully"))
                .verifyComplete();
    }

    @Test
    @DisplayName("SERVICE - Should Patch User successfully")
    void shouldPatchUserSuccessfully() {
        var userDocument = buildUserDocumentForTests();
        when(userRepository.findByIdAndActiveTrue(anyString())).thenReturn(Mono.just(userDocument));
        when(userRepository.save(any(UserDocument.class))).thenReturn(Mono.just(userDocument));

        var request = buildUserPatchRequestBodyForTests();
        var result = userService.patch(USER_ID, Mono.just(request));

        StepVerifier.create(result)
                .expectNextMatches(created -> created.getMessage().equals("User patched successfully"))
                .verifyComplete();
    }

    @Test
    @DisplayName("SERVICE - Should Patch By Path User successfully")
    void shouldPatchByPathUserSuccessfully() {
        var userDocument = buildUserDocumentForTests();
        when(userRepository.findByIdAndActiveTrue(anyString())).thenReturn(Mono.just(userDocument));
        when(userRepository.save(any(UserDocument.class))).thenReturn(Mono.just(userDocument));

        var result = userService.patchByPath(USER_ID, FIELD_NAME, FIELD_VALUE);

        StepVerifier.create(result)
                .expectNextMatches(created -> created.getMessage().equals("User patched by path successfully"))
                .verifyComplete();
    }
}
