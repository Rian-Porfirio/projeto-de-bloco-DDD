package com.freightcomparator.carrier.domain.model;

public record CarrierCode(String value) {
    public CarrierCode {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Carrier code must not be blank");
        }
        value = value.trim().toUpperCase();
    }

    public static CarrierCode of(String value) {
        return new CarrierCode(value);
    }
}
