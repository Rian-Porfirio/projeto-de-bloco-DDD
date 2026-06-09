package com.freightcomparator.freight.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Dimensions(BigDecimal heightCm, BigDecimal widthCm, BigDecimal lengthCm) {

    private static final BigDecimal VOLUMETRIC_DIVISOR = BigDecimal.valueOf(6000);

    public Dimensions {
        requirePositive(heightCm, "height");
        requirePositive(widthCm, "width");
        requirePositive(lengthCm, "length");
    }

    private static void requirePositive(BigDecimal value, String field) {
        if (value == null || value.signum() <= 0) {
            throw new IllegalArgumentException("Dimension " + field + " must be positive");
        }
    }

    public BigDecimal volumetricWeightKg() {
        return heightCm.multiply(widthCm)
                .multiply(lengthCm)
                .divide(VOLUMETRIC_DIVISOR, 3, RoundingMode.HALF_UP);
    }
}
