package com.freightcomparator.carrier.infrastructure.provider;

import com.freightcomparator.carrier.domain.model.CarrierCode;
import com.freightcomparator.carrier.domain.model.ShipmentSpec;
import com.freightcomparator.carrier.domain.port.CarrierProvider;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class AbstractMockCarrierProvider implements CarrierProvider {

    private final CarrierCode carrierCode;

    protected AbstractMockCarrierProvider(String code) {
        this.carrierCode = CarrierCode.of(code);
    }

    @Override
    public CarrierCode carrierCode() {
        return carrierCode;
    }

    protected double distanceUnits(ShipmentSpec spec) {
        int diff = Math.abs(spec.originPrefix() - spec.destinationPrefix());
        return diff / 1000.0;
    }

    protected BigDecimal price(double base, double perKg, double perDistance, double insuranceRate, ShipmentSpec spec) {
        double weight = spec.billableWeightKg().doubleValue();
        double distance = distanceUnits(spec);
        double insurance = spec.declaredValue().doubleValue() * insuranceRate;
        double total = base + (perKg * weight) + (perDistance * distance) + insurance;
        return BigDecimal.valueOf(total).setScale(2, RoundingMode.HALF_UP);
    }

    protected int deliveryDays(int minimumDays, double distancePerDay, ShipmentSpec spec) {
        double distance = distanceUnits(spec);
        int transit = (int) Math.ceil(distance / distancePerDay);
        return Math.max(minimumDays, transit + minimumDays - 1);
    }
}
