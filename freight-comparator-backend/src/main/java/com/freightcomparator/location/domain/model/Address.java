package com.freightcomparator.location.domain.model;

public record Address(
        ZipCode zipCode,
        String street,
        String neighborhood,
        String city,
        String state
) {
    public Address {
        if (zipCode == null) {
            throw new IllegalArgumentException("Address zip code must not be null");
        }
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("Address city must not be blank");
        }
        if (state == null || state.isBlank()) {
            throw new IllegalArgumentException("Address state must not be blank");
        }
    }
}
