package com.freightcomparator.freight.infrastructure;

import com.freightcomparator.carrier.domain.model.Carrier;
import com.freightcomparator.carrier.domain.model.CarrierQuote;
import com.freightcomparator.carrier.domain.model.ShipmentSpec;
import com.freightcomparator.carrier.domain.port.CarrierRepository;
import com.freightcomparator.carrier.domain.service.CarrierProviderRegistry;
import com.freightcomparator.freight.application.port.out.RateQuote;
import com.freightcomparator.freight.application.port.out.ShippingRateProviderPort;
import com.freightcomparator.freight.domain.model.Shipment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CarrierRateProviderAdapter implements ShippingRateProviderPort {

    private final CarrierRepository carrierRepository;
    private final CarrierProviderRegistry carrierProviderRegistry;

    public CarrierRateProviderAdapter(
            CarrierRepository carrierRepository,
            CarrierProviderRegistry carrierProviderRegistry) {
        this.carrierRepository = carrierRepository;
        this.carrierProviderRegistry = carrierProviderRegistry;
    }

    @Override
    public List<RateQuote> quotesFor(Shipment shipment) {
        ShipmentSpec spec = new ShipmentSpec(
                shipment.origin().value(),
                shipment.destination().value(),
                shipment.billableWeightKg(),
                shipment.declaredValue().amount());

        List<RateQuote> rateQuotes = new ArrayList<>();
        for (Carrier carrier : carrierRepository.findAllActive()) {
            if (!carrierProviderRegistry.supports(carrier.code())) {
                continue;
            }
            for (CarrierQuote quote : carrierProviderRegistry.quoteFrom(carrier.code(), spec)) {
                rateQuotes.add(new RateQuote(
                        carrier.code().value(),
                        carrier.name(),
                        carrier.logoUrl(),
                        quote.serviceName(),
                        quote.modality().name(),
                        quote.price(),
                        quote.deliveryDays()));
            }
        }
        return rateQuotes;
    }
}
