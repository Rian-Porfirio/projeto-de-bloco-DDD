package com.freightcomparator.carrier.interfaces.web.dto;

import com.freightcomparator.carrier.application.dto.CarrierSummary;

public record CarrierResponse(
        String code,
        String name,
        String logoUrl,
        boolean active
) {
    public static CarrierResponse from(CarrierSummary summary) {
        return new CarrierResponse(summary.code(), summary.name(), summary.logoUrl(), summary.active());
    }
}
