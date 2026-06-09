package com.freightcomparator.location.application.dto;

public record AddressResult(
        String zipCode,
        String street,
        String neighborhood,
        String city,
        String state
) {
}
