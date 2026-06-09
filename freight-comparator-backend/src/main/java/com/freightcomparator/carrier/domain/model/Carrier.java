package com.freightcomparator.carrier.domain.model;

import java.util.UUID;

public class Carrier {

    private final UUID id;
    private final CarrierCode code;
    private final String name;
    private final String logoUrl;
    private final boolean active;

    public Carrier(UUID id, CarrierCode code, String name, String logoUrl, boolean active) {
        if (code == null) {
            throw new IllegalArgumentException("Carrier code must not be null");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Carrier name must not be blank");
        }
        this.id = id;
        this.code = code;
        this.name = name;
        this.logoUrl = logoUrl;
        this.active = active;
    }

    public UUID id() {
        return id;
    }

    public CarrierCode code() {
        return code;
    }

    public String name() {
        return name;
    }

    public String logoUrl() {
        return logoUrl;
    }

    public boolean active() {
        return active;
    }
}
