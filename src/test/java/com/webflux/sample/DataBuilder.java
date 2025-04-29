package com.webflux.sample;

import com.webflux.sample.model.AddressCreatedResponseBody;
import com.webflux.sample.model.AddressItemsResponseBody;
import com.webflux.sample.model.AddressReadResponseBody;
import com.webflux.sample.model.AddressRequestBody;

import java.util.ArrayList;
import java.util.List;

public class DataBuilder {

    public static final String BASE_URL = "http://localhost:8080";
    public static final String API_PREFIX = "/webflux-sample/v1";
    public static final String PERSON_ID = "68093a2f5f5e40392facb4d3";

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

}
