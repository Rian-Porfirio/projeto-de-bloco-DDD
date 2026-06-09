package com.freightcomparator.freight.application.dto;

import java.math.BigDecimal;

public record CalculateFreightCommand(
        String originZipCode,
        String destinationZipCode,
        BigDecimal weight,
        BigDecimal height,
        BigDecimal width,
        BigDecimal length,
        BigDecimal declaredValue
) {
}
