package com.freightcomparator.carrier.domain.model;

import java.math.BigDecimal;

public record CarrierQuote(
        CarrierCode carrierCode,
        String serviceName,
        Modality modality,
        BigDecimal price,
        int deliveryDays
) {
    public CarrierQuote {
        if (price == null || price.signum() < 0) {
            throw new IllegalArgumentException("Quote price must not be negative");
        }
        if (deliveryDays <= 0) {
            throw new IllegalArgumentException("Delivery days must be positive");
        }
    }
}
