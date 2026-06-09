package com.freightcomparator.carrier.domain.model;

import java.math.BigDecimal;

public record ShipmentSpec(
        String originZipCode,
        String destinationZipCode,
        BigDecimal billableWeightKg,
        BigDecimal declaredValue
) {
    public ShipmentSpec {
        if (billableWeightKg == null || billableWeightKg.signum() <= 0) {
            throw new IllegalArgumentException("Billable weight must be positive");
        }
        if (declaredValue == null) {
            declaredValue = BigDecimal.ZERO;
        }
    }

    public int originPrefix() {
        return prefix(originZipCode);
    }

    public int destinationPrefix() {
        return prefix(destinationZipCode);
    }

    private int prefix(String zip) {
        String digits = zip.replaceAll("\\D", "");
        String head = digits.length() >= 5 ? digits.substring(0, 5) : digits;
        return Integer.parseInt(head);
    }
}
