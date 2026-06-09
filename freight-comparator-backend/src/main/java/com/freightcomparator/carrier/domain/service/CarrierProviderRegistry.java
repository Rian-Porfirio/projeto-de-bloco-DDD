package com.freightcomparator.carrier.domain.service;

import com.freightcomparator.carrier.domain.model.CarrierCode;
import com.freightcomparator.carrier.domain.model.CarrierQuote;
import com.freightcomparator.carrier.domain.model.ShipmentSpec;
import com.freightcomparator.carrier.domain.port.CarrierProvider;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CarrierProviderRegistry {

    private final Map<CarrierCode, CarrierProvider> providers;

    public CarrierProviderRegistry(List<CarrierProvider> providers) {
        this.providers = providers.stream()
                .collect(Collectors.toUnmodifiableMap(CarrierProvider::carrierCode, Function.identity()));
    }

    public List<CarrierQuote> quoteFrom(CarrierCode code, ShipmentSpec spec) {
        return provider(code)
                .map(provider -> provider.quote(spec))
                .orElse(List.of());
    }

    public Optional<CarrierProvider> provider(CarrierCode code) {
        return Optional.ofNullable(providers.get(code));
    }

    public boolean supports(CarrierCode code) {
        return providers.containsKey(code);
    }
}
