package com.webflux.sample.controller;

import com.webflux.sample.service.AddressService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebFluxTest(AddressController.class)
class AddressControllerTest extends BaseControllerTest {

    @MockBean
    private AddressService addressService;

    @Test
    @DisplayName("should Create One Address POST - /webflux-sample/v1/addresses/{personId}")
    void shouldCreateOneAddressSuccessfully() {

    }

    @Test
    @DisplayName("should NOT Create One Address POST - /webflux-sample/v1/addresses/{personId}")
    void shouldNotCreateOneAddressSuccessfully() {

    }

    @Test
    @DisplayName("should Read All Address for One Person GET - /webflux-sample/v1/addresses/{personId}")
    void shouldReadAllAddressForOnePersonSuccessfully() {

    }

    @Test
    @DisplayName("should NOT Read All Address for One Person GET - /webflux-sample/v1/addresses/{personId}")
    void shouldNotReadAllAddressForOnePersonSuccessfully() {

    }

}
