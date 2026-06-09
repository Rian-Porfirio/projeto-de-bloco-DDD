package com.freightcomparator.location.interfaces.web.dto;

import com.freightcomparator.location.application.dto.AddressResult;

public record AddressResponse(
        String zipCode,
        String street,
        String neighborhood,
        String city,
        String state
) {
    public static AddressResponse from(AddressResult result) {
        return new AddressResponse(
                result.zipCode(),
                result.street(),
                result.neighborhood(),
                result.city(),
                result.state());
    }
}
