package com.freightcomparator.freight.domain.model;

public record FreightQuote(
        String carrierCode,
        String carrierName,
        String logoUrl,
        String serviceName,
        String modality,
        Money price,
        int deliveryDays
) {
    public FreightQuote {
        if (carrierCode == null || carrierCode.isBlank()) {
            throw new IllegalArgumentException("Carrier code must not be blank");
        }
        if (serviceName == null || serviceName.isBlank()) {
            throw new IllegalArgumentException("Service name must not be blank");
        }
        if (price == null) {
            throw new IllegalArgumentException("Price must not be null");
        }
        if (deliveryDays <= 0) {
            throw new IllegalArgumentException("Delivery days must be positive");
        }
    }
}
