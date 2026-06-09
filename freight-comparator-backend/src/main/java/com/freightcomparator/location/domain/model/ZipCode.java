package com.freightcomparator.location.domain.model;

import com.freightcomparator.location.domain.exception.InvalidZipCodeException;

public final class ZipCode {

    private final String value;

    private ZipCode(String value) {
        this.value = value;
    }

    public static ZipCode of(String raw) {
        if (raw == null) {
            throw new InvalidZipCodeException("Zip code must not be null");
        }
        String digits = raw.replaceAll("\\D", "");
        if (digits.length() != 8) {
            throw new InvalidZipCodeException("Zip code must contain exactly 8 digits: " + raw);
        }
        return new ZipCode(digits);
    }

    public String value() {
        return value;
    }

    public String formatted() {
        return value.substring(0, 5) + "-" + value.substring(5);
    }

    public int prefix() {
        return Integer.parseInt(value.substring(0, 5));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ZipCode other)) {
            return false;
        }
        return value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return formatted();
    }
}
