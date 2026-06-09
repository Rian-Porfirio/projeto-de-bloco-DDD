package com.freightcomparator.freight.domain.exception;

public class NoQuotesAvailableException extends FreightCalculationException {
    public NoQuotesAvailableException(String message) {
        super(message);
    }
}
