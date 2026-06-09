package com.freightcomparator.location.domain.exception;

public class InvalidZipCodeException extends RuntimeException {
    public InvalidZipCodeException(String message) {
        super(message);
    }
}
