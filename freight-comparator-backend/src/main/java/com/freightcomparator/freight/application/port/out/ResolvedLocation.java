package com.freightcomparator.freight.application.port.out;

public record ResolvedLocation(
        String zipCode,
        String city,
        String state
) {
}
