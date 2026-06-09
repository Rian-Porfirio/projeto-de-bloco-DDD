package com.freightcomparator.freight.application.port.out;

import java.math.BigDecimal;

public record RateQuote(
        String carrierCode,
        String carrierName,
        String logoUrl,
        String serviceName,
        String modality,
        BigDecimal price,
        int deliveryDays
) {
}
