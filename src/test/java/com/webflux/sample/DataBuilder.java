package com.webflux.sample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webflux.sample.document.AddressDocument;
import com.webflux.sample.document.ExampleDocument;
import com.webflux.sample.document.UserDocument;
import com.webflux.sample.document.PhoneDocument;
import com.webflux.sample.dto.ExampleRequest;
import com.webflux.sample.model.*;
import com.webflux.sample.user.model.*;
import com.webflux.sample.user_details.model.*;

import java.util.ArrayList;
import java.util.List;

public class DataBuilder {

    public static final String BASE_URL = "http://localhost:8080";
    public static final String API_PREFIX = "/webflux-sample/v1";

    public static final String USER_NAME = "John Smith Viz";
    public static final String USER_EMAIL = "john@email.com";
    public static final String USER_ID = "68093a2f5f5e40392f31t4d3";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_VALUE = "John Smith Hunt";

    public static final String ADDRESS_STREET = "Street test";
    public static final int ADDRESS_NUMBER = 1234;
    public static final String ADDRESS_CITY = "City test";
    public static final String ADDRESS_CODE = "1122334455";

    public static final String PHONE_NUMBER = "12158912345678";
    public static final PhoneRequestBody.PhoneTypeEnum PHONE_TYPE_HOME = PhoneRequestBody.PhoneTypeEnum.HOME;
    public static final PhoneRequestBody.PhoneTypeEnum PHONE_TYPE_OFFICE = PhoneRequestBody.PhoneTypeEnum.OFFICE;
    public static final PhoneRequestBody.PhoneTypeEnum PHONE_TYPE_MOBILE = PhoneRequestBody.PhoneTypeEnum.MOBILE;

    public static final String PRODUCT_ID = "123456";
    public static final String PRODUCT_NAME = "PRODUCT NAME";
    public static final String PRODUCT_DESCRIPTION = "PRODUCT DESCRIPTION";

    public static UserDocument buildUserDocumentForTests() {
        UserDocument userDocument = new UserDocument();
        userDocument.setId(USER_ID);
        userDocument.setName(USER_NAME);
        userDocument.setEmail(USER_EMAIL);
        return userDocument;
    }

    public static ExampleDocument buildExampleDocumentForTests() {
        ExampleDocument exampleDocument = new ExampleDocument();
        exampleDocument.setId(PRODUCT_ID);
        exampleDocument.setProduct(PRODUCT_NAME);
        exampleDocument.setDescription(PRODUCT_DESCRIPTION);
        return exampleDocument;
    }

    public static AddressDocument buildAddressDocumentForTests() {
        AddressDocument addressDocument = new AddressDocument();
        addressDocument.setId(USER_ID);
        addressDocument.setStreet(ADDRESS_STREET);
        addressDocument.setNumber(ADDRESS_NUMBER);
        addressDocument.setCity(ADDRESS_CITY);
        addressDocument.setZipcode(ADDRESS_CODE);
        return addressDocument;
    }

    public static PhoneDocument buildPhonesDocumentForTests() {
        PhoneDocument phoneDocument = new PhoneDocument();
        phoneDocument.setId(USER_ID);
        phoneDocument.setPhoneNumber(PHONE_NUMBER);
        phoneDocument.setPhoneType(PHONE_TYPE_MOBILE.getValue());
        return phoneDocument;
    }

    public static AddressRequestBody buildAddressRequestBodyForTests() {
        AddressRequestBody addressRequestBody = new AddressRequestBody();
        addressRequestBody.setStreet(ADDRESS_STREET);
        addressRequestBody.setNumber(ADDRESS_NUMBER);
        addressRequestBody.setCity(ADDRESS_CITY);
        addressRequestBody.setZipcode(ADDRESS_CODE);
        return addressRequestBody;
    }

    public static PhoneRequestBody buildPhoneRequestBodyForTests() {
        PhoneRequestBody addressRequestBody = new PhoneRequestBody();
        addressRequestBody.setPhoneNumber(PHONE_NUMBER);
        addressRequestBody.setPhoneType(PHONE_TYPE_HOME);
        return addressRequestBody;
    }

    public static AddressCreatedResponseBody buildAddressCreatedResponseBodyForTests() {
        AddressCreatedResponseBody addressCreatedResponseBody = new AddressCreatedResponseBody();
        addressCreatedResponseBody.setId(USER_ID);
        return addressCreatedResponseBody;
    }

    public static PhoneCreatedResponseBody buildPhoneCreatedResponseBodyForTests() {
        PhoneCreatedResponseBody phoneCreatedResponseBody = new PhoneCreatedResponseBody();
        phoneCreatedResponseBody.setId(USER_ID);
        return phoneCreatedResponseBody;
    }

    public static AddressReadResponseBody buildAddressReadResponseBodyForTests() {
        AddressReadResponseBody addressReadResponseBody = new AddressReadResponseBody();
        List<AddressItemsResponseBody> itemsResponseBodyList = new ArrayList<>();
        AddressItemsResponseBody addressItemsResponseBody = new AddressItemsResponseBody();
        addressItemsResponseBody.setStreet(ADDRESS_STREET);
        addressItemsResponseBody.setNumber(ADDRESS_NUMBER);
        addressItemsResponseBody.setCity(ADDRESS_CITY);
        addressItemsResponseBody.setZipcode(ADDRESS_CODE);
        itemsResponseBodyList.add(addressItemsResponseBody);
        addressReadResponseBody.setAddresses(itemsResponseBodyList);
        return addressReadResponseBody;
    }

    public static PhoneReadResponseBody buildPhoneReadResponseBodyForTests() {
        PhoneReadResponseBody phoneReadResponseBody = new PhoneReadResponseBody();
        List<PhonesItemsResponseBody> itemsResponseBodyList = new ArrayList<>();
        PhonesItemsResponseBody phonesItemsResponseBody = new PhonesItemsResponseBody();
        phonesItemsResponseBody.setPhoneNumber(PHONE_NUMBER);
        phonesItemsResponseBody.setPhoneType(PhoneRequestBody.PhoneTypeEnum.HOME.getValue());
        phonesItemsResponseBody.setActive(true);
        itemsResponseBodyList.add(phonesItemsResponseBody);
        phoneReadResponseBody.setPhones(itemsResponseBodyList);
        return phoneReadResponseBody;
    }

    public static UserRequestBody buildUserRequestBodyForTests() {
        UserRequestBody userRequestBody = new UserRequestBody();
        userRequestBody.setName(USER_NAME);
        userRequestBody.setEmail(USER_EMAIL);
        return userRequestBody;
    }

    public static ExampleRequest buildExampleRequestBodyForTests() {
        ExampleRequest exampleRequest = new ExampleRequest();
        exampleRequest.setId(PRODUCT_ID);
        exampleRequest.setProduct(PRODUCT_NAME);
        exampleRequest.setDescription(PRODUCT_DESCRIPTION);
        return exampleRequest;
    }

    public static UserPatchRequestBody buildUserPatchRequestBodyForTests() {
        UserPatchRequestBody userPatchRequestBody = new UserPatchRequestBody();
        userPatchRequestBody.setName(USER_NAME);
        return userPatchRequestBody;
    }

    public static UserCreatedResponseBody buildUserCreatedResponseBodyForTests() {
        UserCreatedResponseBody userCreatedResponseBody = new UserCreatedResponseBody();
        userCreatedResponseBody.setId(USER_ID);
        return userCreatedResponseBody;
    }

    public static UserReadResponseBody buildUserReadResponseBodyForTests() {
        UserReadResponseBody userReadResponseBody = new UserReadResponseBody();
        userReadResponseBody.setId(USER_ID);
        userReadResponseBody.setName(USER_NAME);
        userReadResponseBody.setEmail(USER_EMAIL);
        userReadResponseBody.setActive(true);
        return userReadResponseBody;
    }

    public static UsersReadResponseBody buildUsersReadResponseBodyForTests() {
        UsersReadResponseBody usersReadResponseBody = new UsersReadResponseBody();
        usersReadResponseBody.setUsers(new ArrayList<>());
        return usersReadResponseBody;
    }

    public static GenericsResponseBody buildGenericsResponseBodyForTests(String message) {
        GenericsResponseBody genericsResponseBody = new GenericsResponseBody();
        genericsResponseBody.setMessage(message);
        return genericsResponseBody;
    }

    public static ZipcodeModel buildZipcodeModelForTests() {
        ZipcodeModel zipcodeModel = new ZipcodeModel();
        zipcodeModel.setBairro("Nob Neighbor");
        zipcodeModel.setUf("SC");
        zipcodeModel.setIbge("111011");
        zipcodeModel.setGia("GIA-12");
        zipcodeModel.setDdd("22");
        zipcodeModel.setLocalidade("Brazil Sao Paulo Brasilia");
        zipcodeModel.setLogradouro("Log fake");
        zipcodeModel.setSiafi("121212");
        zipcodeModel.setComplemento("Smart house park");
        zipcodeModel.setCep("18090000");
        return zipcodeModel;
    }

    public static String buildZipcodeModelJsonForTests() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(buildZipcodeModelForTests());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
