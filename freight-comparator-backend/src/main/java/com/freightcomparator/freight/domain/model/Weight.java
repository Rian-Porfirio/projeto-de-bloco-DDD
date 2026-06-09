package com.freightcomparator.freight.domain.model;

import java.math.BigDecimal;

public record Weight(BigDecimal kilograms) {
    public Weight {
        if (kilograms == null || kilograms.signum() <= 0) {
            throw new IllegalArgumentException("Weight must be positive");
        }
    }

    public static Weight ofKilograms(BigDecimal value) {
        return new Weight(value);
    }
}
