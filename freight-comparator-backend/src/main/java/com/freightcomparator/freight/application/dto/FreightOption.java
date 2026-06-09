package com.freightcomparator.freight.application.dto;

import java.math.BigDecimal;

public record FreightOption(
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
}
