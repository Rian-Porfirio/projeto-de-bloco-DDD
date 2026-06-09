package com.freightcomparator.freight.application.port.in;

import com.freightcomparator.freight.application.dto.CalculateFreightCommand;
import com.freightcomparator.freight.application.dto.FreightCalculationResult;

public interface CalculateFreightUseCase {
    FreightCalculationResult calculate(CalculateFreightCommand command);
}
