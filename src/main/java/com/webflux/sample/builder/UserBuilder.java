package com.webflux.sample.builder;

import com.webflux.sample.document.AddressDocument;
import com.webflux.sample.document.UserDocument;
import com.webflux.sample.document.PhoneDocument;
import com.webflux.sample.model.*;
import com.webflux.sample.user.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static com.webflux.sample.util.WebFluxUtil.datetimeUtil;
import static java.util.Objects.isNull;

public class UserBuilder {

    public static UserReadResponseBody buildUserResponse(UserDocument document) {
        UserReadResponseBody response = new UserReadResponseBody();
        response.setId(document.getId());
        response.setName(document.getName());
        response.setEmail(document.getEmail());
        response.setActive(document.isActive());
        response.setCreatedAt(datetimeUtil(document.getCreatedAt()));
        response.setUpdatedAt(datetimeUtil(document.getUpdatedAt()));
        response.setDeletedAt(datetimeUtil(document.getDeletedAt()));
        response.setAddress(buildAddressResponse(document.getAddresses()));
        response.setPhones(buildPhoneResponse(document.getPhones()));
        return response;
    }

    public static UserOnlyReadResponseBody buildUesrOnlyResponse(UserDocument document) {
        UserOnlyReadResponseBody response = new UserOnlyReadResponseBody();
        response.setId(document.getId());
        response.setName(document.getName());
        response.setEmail(document.getEmail());
        response.setActive(document.isActive());
        response.setCreatedAt(datetimeUtil(document.getCreatedAt()));
        response.setUpdatedAt(datetimeUtil(document.getUpdatedAt()));
        response.setDeletedAt(datetimeUtil(document.getDeletedAt()));
        return response;
    }

    public static List<AddressResponseBody> buildAddressResponse(Set<AddressDocument> addressDocs) {
        if (isNull(addressDocs) || addressDocs.isEmpty()) return null;
        List<AddressResponseBody> addressResponseBody = new ArrayList<>();

        addressDocs.forEach(address -> {
            AddressResponseBody addresses = new AddressResponseBody();

            addresses.setStreet(address.getStreet());
            addresses.setNumber(address.getNumber());
            addresses.setZipcode(address.getZipcode());
            addresses.setCity(address.getCity());
            addresses.setActive(address.isActive());
            addresses.setCreatedAt(datetimeUtil(address.getCreatedAt()));
            addresses.setUpdatedAt(datetimeUtil(address.getUpdatedAt()));
            addresses.setDeletedAt(datetimeUtil(address.getDeletedAt()));

            addressResponseBody.add(addresses);
        });

        return addressResponseBody;
    }

    public static List<PhoneResponseBody> buildPhoneResponse(Set<PhoneDocument> phonesDocs) {
        if (isNull(phonesDocs) || phonesDocs.isEmpty()) return null;
        List<PhoneResponseBody> phoneResponseBody = new ArrayList<>();

        phonesDocs.forEach(phone -> {
            PhoneResponseBody phones = new PhoneResponseBody();

            phones.setPhoneNumber(phone.getPhoneNumber());
            phones.setPhoneType(phone.getPhoneType());
            phones.setActive(phone.isActive());
            phones.setCreatedAt(datetimeUtil(phone.getCreatedAt()));
            phones.setUpdatedAt(datetimeUtil(phone.getUpdatedAt()));
            phones.setDeletedAt(datetimeUtil(phone.getDeletedAt()));

            phoneResponseBody.add(phones);
        });

        return phoneResponseBody;
    }

    public static UserDocument buildToPatch(UserPatchRequestBody patchRequest, UserDocument userDocument) {
        if (patchRequest.getName() != null) {
            userDocument.setName(patchRequest.getName());
        } else if (patchRequest.getEmail() != null) {
            userDocument.setEmail(patchRequest.getEmail());
        }
        return userDocument;
    }

    public static UserDocument buildAndPatchByPath(UserDocument document, String fieldName, Object fieldValue) {
        Arrays.stream(UserDocument.class.getDeclaredFields()).anyMatch(field -> {
            if (field.getName().equals(fieldName)) {
                try {
                    document.getClass().getDeclaredField(field.getName()).setAccessible(true);
                    document.getClass().getDeclaredField(field.getName()).set(document, fieldValue);
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    e.printStackTrace();
                }
                return true;
            }
            return false;
        });

        return document;
    }

    public static GenericsResponseBody buildGenericsResponse(String message) {
        GenericsResponseBody response = new GenericsResponseBody();
        response.setMessage(message);
        return response;
    }

}
