package com.freightcomparator.freight.domain.model;

public record PostalCode(String value) {
    public PostalCode {
        if (value == null) {
            throw new IllegalArgumentException("Postal code must not be null");
        }
        String digits = value.replaceAll("\\D", "");
        if (digits.length() != 8) {
            throw new IllegalArgumentException("Postal code must contain 8 digits: " + value);
        }
        value = digits;
    }

    public static PostalCode of(String value) {
        return new PostalCode(value);
    }

    public String formatted() {
        return value.substring(0, 5) + "-" + value.substring(5);
    }
}
