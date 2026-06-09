package com.freightcomparator.carrier.domain.port;

import com.freightcomparator.carrier.domain.model.Carrier;
import com.freightcomparator.carrier.domain.model.CarrierCode;

import java.util.List;
import java.util.Optional;

public interface CarrierRepository {
    List<Carrier> findAllActive();

    Optional<Carrier> findByCode(CarrierCode code);
}
