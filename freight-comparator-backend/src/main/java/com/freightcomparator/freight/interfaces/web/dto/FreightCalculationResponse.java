package com.freightcomparator.freight.interfaces.web.dto;

import com.freightcomparator.freight.application.dto.FreightCalculationResult;

import java.time.Instant;
import java.util.List;

public record FreightCalculationResponse(
        String id,
        String originZipCode,
        String destinationZipCode,
        FreightOptionResponse bestPrice,
        FreightOptionResponse fastestDelivery,
        FreightOptionResponse bestCostBenefit,
        List<FreightOptionResponse> options,
        Instant calculatedAt
) {
    public static FreightCalculationResponse from(FreightCalculationResult result) {
        return new FreightCalculationResponse(
                result.id(),
                result.originZipCode(),
                result.destinationZipCode(),
                FreightOptionResponse.from(result.bestPrice()),
                FreightOptionResponse.from(result.fastestDelivery()),
                FreightOptionResponse.from(result.bestCostBenefit()),
                result.options().stream().map(FreightOptionResponse::from).toList(),
                result.calculatedAt());
    }
}
