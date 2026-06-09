package com.freightcomparator.freight.application.dto;

import java.time.Instant;
import java.util.List;

public record FreightCalculationResult(
        String id,
        String originZipCode,
        String destinationZipCode,
        FreightOption bestPrice,
        FreightOption fastestDelivery,
        FreightOption bestCostBenefit,
        List<FreightOption> options,
        Instant calculatedAt
) {
}
