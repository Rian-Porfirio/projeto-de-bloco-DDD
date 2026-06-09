package com.freightcomparator.freight.interfaces.web.dto;

import com.freightcomparator.freight.application.dto.FreightOption;

import java.math.BigDecimal;

public record FreightOptionResponse(
        String carrierCode,
        String carrierName,
        String logoUrl,
        String serviceName,
        String modality,
        BigDecimal price,
        String currency,
        int deliveryDays,
        boolean bestPrice,
        boolean fastestDelivery,
        boolean bestCostBenefit
) {
    public static FreightOptionResponse from(FreightOption option) {
        if (option == null) {
            return null;
        }
        return new FreightOptionResponse(
                option.carrierCode(),
                option.carrierName(),
                option.logoUrl(),
                option.serviceName(),
                option.modality(),
                option.price(),
                option.currency(),
                option.deliveryDays(),
                option.bestPrice(),
                option.fastestDelivery(),
                option.bestCostBenefit());
    }
}
