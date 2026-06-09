package com.freightcomparator.freight.domain.model;

import java.math.BigDecimal;

public record Shipment(
        PostalCode origin,
        PostalCode destination,
        Weight weight,
        Dimensions dimensions,
        Money declaredValue
) {
    public Shipment {
        if (origin == null || destination == null) {
            throw new IllegalArgumentException("Origin and destination must not be null");
        }
        if (weight == null) {
            throw new IllegalArgumentException("Weight must not be null");
        }
        if (dimensions == null) {
            throw new IllegalArgumentException("Dimensions must not be null");
        }
        if (declaredValue == null) {
            declaredValue = Money.zero();
        }
    }

    public BigDecimal billableWeightKg() {
        return weight.kilograms().max(dimensions.volumetricWeightKg());
    }
}
