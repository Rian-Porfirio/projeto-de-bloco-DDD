package com.freightcomparator.freight.application.port.out;

import com.freightcomparator.freight.domain.model.Shipment;

import java.util.List;

public interface ShippingRateProviderPort {
    List<RateQuote> quotesFor(Shipment shipment);
}
