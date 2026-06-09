package com.freightcomparator.freight.domain.exception;

public class UnresolvableLocationException extends FreightCalculationException {
    public UnresolvableLocationException(String message) {
        super(message);
    }
}
