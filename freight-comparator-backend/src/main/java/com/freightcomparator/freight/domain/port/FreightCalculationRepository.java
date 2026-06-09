package com.freightcomparator.freight.domain.port;

import com.freightcomparator.freight.domain.model.FreightCalculation;

import java.util.List;

public interface FreightCalculationRepository {
    void save(FreightCalculation calculation);

    List<FreightCalculation> findRecent(int limit);
}
