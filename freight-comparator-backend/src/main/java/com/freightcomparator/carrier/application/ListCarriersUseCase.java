package com.freightcomparator.carrier.application;

import com.freightcomparator.carrier.application.dto.CarrierSummary;

import java.util.List;

public interface ListCarriersUseCase {
    List<CarrierSummary> listActive();
}
