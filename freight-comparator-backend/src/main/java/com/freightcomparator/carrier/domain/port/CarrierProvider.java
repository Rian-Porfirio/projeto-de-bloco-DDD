package com.freightcomparator.carrier.domain.port;

import com.freightcomparator.carrier.domain.model.CarrierCode;
import com.freightcomparator.carrier.domain.model.CarrierQuote;
import com.freightcomparator.carrier.domain.model.ShipmentSpec;

import java.util.List;

public interface CarrierProvider {
    CarrierCode carrierCode();

    List<CarrierQuote> quote(ShipmentSpec spec);
}
