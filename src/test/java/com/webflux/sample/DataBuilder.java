package com.webflux.sample;

import com.webflux.sample.model.*;

import java.util.ArrayList;
import java.util.List;

public class DataBuilder {

    public static final String BASE_URL = "http://localhost:8080";
    public static final String API_PREFIX = "/webflux-sample/v1";
    public static final String PERSON_NAME = "John Smith Viz";
    public static final String PERSON_EMAIL = "john@email.com";
    public static final String PERSON_ID = "68093a2f5f5e40392facb4d3";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_VALUE = "John Smith Hunt";

    public static AddressRequestBody buildAddressRequestBodyForTests() {
        AddressRequestBody addressRequestBody = new AddressRequestBody();
        addressRequestBody.setStreet("Street name test");
        addressRequestBody.setNumber(122);
        addressRequestBody.setZipcode("12900090");
        addressRequestBody.setCity("New York");
        return addressRequestBody;
    }

    public static AddressCreatedResponseBody buildAddressCreatedResponseBodyForTests() {
        AddressCreatedResponseBody addressCreatedResponseBody = new AddressCreatedResponseBody();
        addressCreatedResponseBody.setId(PERSON_ID);
        return addressCreatedResponseBody;
    }

    public static AddressReadResponseBody buildAddressReadResponseBodyForTests() {
        AddressReadResponseBody addressReadResponseBody = new AddressReadResponseBody();
        List<AddressItemsResponseBody> itemsResponseBodyList = new ArrayList<>();
        AddressItemsResponseBody addressItemsResponseBody = new AddressItemsResponseBody();
        addressItemsResponseBody.setStreet("Street name test");
        addressItemsResponseBody.setNumber(123);
        addressItemsResponseBody.setCity("New York");
        addressItemsResponseBody.setZipcode("12090001");
        itemsResponseBodyList.add(addressItemsResponseBody);
        addressReadResponseBody.setAddresses(itemsResponseBodyList);
        return addressReadResponseBody;
    }

    public static PersonRequestBody buildPersonRequestBodyForTests() {
        PersonRequestBody personRequestBody = new PersonRequestBody();
        personRequestBody.setName(PERSON_NAME);
        personRequestBody.setEmail(PERSON_EMAIL);
        return personRequestBody;
    }

    public static PersonCreatedResponseBody buildPersonCreatedResponseBodyForTests() {
        PersonCreatedResponseBody personCreatedResponseBody = new PersonCreatedResponseBody();
        personCreatedResponseBody.setId(PERSON_ID);
        return personCreatedResponseBody;
    }

    public static PersonReadResponseBody buildPersonReadResponseBodyForTests() {
        PersonReadResponseBody personReadResponseBody = new PersonReadResponseBody();
        personReadResponseBody.setId(PERSON_ID);
        personReadResponseBody.setName(PERSON_NAME);
        personReadResponseBody.setEmail(PERSON_EMAIL);
        personReadResponseBody.setActive(true);
        return personReadResponseBody;
    }

    public static PersonsReadResponseBody buildPersonsReadResponseBodyForTests() {
        PersonsReadResponseBody personsReadResponseBody = new PersonsReadResponseBody();
        personsReadResponseBody.setPersons(new ArrayList<>());
        return personsReadResponseBody;
    }

    public static GenericsResponseBody buildGenericsResponseBodyForTests(String message) {
        GenericsResponseBody genericsResponseBody = new GenericsResponseBody();
        genericsResponseBody.setMessage(message);
        return genericsResponseBody;
    }

}
