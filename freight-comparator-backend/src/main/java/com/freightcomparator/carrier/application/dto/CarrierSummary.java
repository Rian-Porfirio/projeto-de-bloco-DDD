package com.freightcomparator.carrier.application.dto;

public record CarrierSummary(
        String code,
        String name,
        String logoUrl,
        boolean active
) {
}
